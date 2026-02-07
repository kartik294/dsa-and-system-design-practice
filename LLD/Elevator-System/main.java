import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.logging.*;

enum Direction { UP, DOWN, IDLE }
enum ElevatorStatus { IDLE, MOVING, DOOR_OPEN, MALFUNCTION }

class Log {
    private static final Logger logger = Logger.getLogger("ElevatorSystem");
    private Log() {}
    static Logger get() { return logger; }
}

class BuildingConfig {
    final int minFloor;
    final int maxFloor;

    BuildingConfig(int min, int max) {
        if (min > max) throw new IllegalArgumentException();
        this.minFloor = min;
        this.maxFloor = max;
    }

    void validateFloor(int f) {
        if (f < minFloor || f > maxFloor)
            throw new IllegalArgumentException("Invalid floor " + f);
    }
}

class TimingConfig {
    final int moveMs;
    final int doorMs;

    TimingConfig(int m, int d) {
        moveMs = m;
        doorMs = d;
    }
}

interface ElevatorState {
    void handle(Elevator e);
}

class IdleState implements ElevatorState {
    public void handle(Elevator e) {
        if (!e.hasRequests()) return;
        e.setDirection(e.hasUp() ? Direction.UP : Direction.DOWN);
        e.setState(new MovingState());
    }
}

class MovingState implements ElevatorState {
    public void handle(Elevator e) {
        e.step();
        if (e.shouldStop()) e.setState(new DoorState());
    }
}

class DoorState implements ElevatorState {
    public void handle(Elevator e) {
        e.openDoor();
        e.clearFloor();
        e.delay(e.timing.doorMs);
        e.closeDoor();
        e.setState(new IdleState());
    }
}

class Elevator implements Runnable {

    private final int id;
    private final ScheduledExecutorService exec =
            Executors.newSingleThreadScheduledExecutor();

    private final ConcurrentSkipListSet<Integer> up =
            new ConcurrentSkipListSet<>();

    private final ConcurrentSkipListSet<Integer> down =
            new ConcurrentSkipListSet<>(Comparator.reverseOrder());

    private final AtomicBoolean running = new AtomicBoolean(true);

    private volatile int floor = 0;
    private volatile Direction direction = Direction.IDLE;
    private volatile ElevatorState state = new IdleState();
    private volatile ElevatorStatus status = ElevatorStatus.IDLE;

    final TimingConfig timing;

    Elevator(int id, TimingConfig t) {
        this.id = id;
        this.timing = t;
    }

    void addRequest(int f) {
        if (status == ElevatorStatus.MALFUNCTION) return;
        if (f > floor) up.add(f);
        else down.add(f);
        Log.get().info("Elevator " + id + " request " + f);
    }

    boolean hasRequests() {
        return !up.isEmpty() || !down.isEmpty();
    }

    boolean hasUp() { return !up.isEmpty(); }

    boolean shouldStop() {
        return up.contains(floor) || down.contains(floor);
    }

    void clearFloor() {
        up.remove(floor);
        down.remove(floor);
    }

    void step() {
        status = ElevatorStatus.MOVING;
        floor += (direction == Direction.UP ? 1 : -1);
        Log.get().info("Elevator " + id + " floor " + floor);
        delay(timing.moveMs);
    }

    void openDoor() {
        status = ElevatorStatus.DOOR_OPEN;
        Log.get().info("Elevator " + id + " door open at " + floor);
    }

    void closeDoor() {
        Log.get().info("Elevator " + id + " door close");
    }

    void delay(int ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }

    void malfunction() {
        status = ElevatorStatus.MALFUNCTION;
        Log.get().warning("Elevator " + id + " malfunction");
    }

    void recover() {
        if (status == ElevatorStatus.MALFUNCTION) {
            status = ElevatorStatus.IDLE;
            state = new IdleState();
            Log.get().info("Elevator " + id + " recovered");
        }
    }

    void shutdown() {
        running.set(false);
        exec.shutdownNow();
    }

    int getFloor() { return floor; }
    ElevatorStatus getStatus() { return status; }
    void setDirection(Direction d) { direction = d; }
    void setState(ElevatorState s) { state = s; }

    public void run() {
        while (running.get()) {
            if (status != ElevatorStatus.MALFUNCTION)
                state.handle(this);
            try { Thread.sleep(50); } catch (Exception ignored) {}
        }
        Log.get().info("Elevator " + id + " stopped");
    }
}

interface SchedulerStrategy {
    Elevator select(int floor, List<Elevator> elevators);
}

class NearestStrategy implements SchedulerStrategy {
    public Elevator select(int f, List<Elevator> list) {
        if (list.isEmpty())
            throw new IllegalStateException("No elevators");

        Elevator best = null;
        int min = Integer.MAX_VALUE;

        for (Elevator e : list) {
            if (e.getStatus() == ElevatorStatus.MALFUNCTION)
                continue;

            int d = Math.abs(e.getFloor() - f);
            if (d < min) {
                min = d;
                best = e;
            }
        }
        if (best == null)
            throw new IllegalStateException("All elevators down");
        return best;
    }
}

class ElevatorController {

    private final List<Elevator> elevators = new ArrayList<>();
    private final SchedulerStrategy strategy;
    private final BuildingConfig config;

    ElevatorController(int count,
                       SchedulerStrategy s,
                       BuildingConfig c,
                       TimingConfig t) {

        if (count <= 0) throw new IllegalArgumentException();
        strategy = s;
        config = c;

        for (int i = 0; i < count; i++) {
            Elevator e = new Elevator(i, t);
            elevators.add(e);
            new Thread(e).start();
        }
    }

    void request(int floor) {
        config.validateFloor(floor);
        Elevator e = strategy.select(floor, elevators);
        e.addRequest(floor);
    }

    void malfunction(int id) { elevators.get(id).malfunction(); }
    void recover(int id) { elevators.get(id).recover(); }

    void shutdown() {
        for (Elevator e : elevators) e.shutdown();
    }
}

public class Main {
    public static void main(String[] args) throws Exception {

        BuildingConfig building =
                new BuildingConfig(0, 20);

        TimingConfig timing =
                new TimingConfig(300, 700);

        ElevatorController controller =
                new ElevatorController(
                        3,
                        new NearestStrategy(),
                        building,
                        timing
                );

        controller.request(5);
        controller.request(10);
        controller.request(3);

        Thread.sleep(5000);

        controller.malfunction(1);

        Thread.sleep(3000);

        controller.recover(1);

        Thread.sleep(5000);

        controller.shutdown();
    }
}
