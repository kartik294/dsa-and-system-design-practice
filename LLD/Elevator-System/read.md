# 🚀 Elevator System (Low-Level Design)

## 📌 Overview

This project implements a multi-elevator control system using object-oriented design principles and common design patterns. The system simulates real-world elevator behavior, supports concurrent requests, and is designed to be scalable and modular.

The goal is to demonstrate a production-style LLD solution suitable for backend or SDE interviews.

---

# 🧩 Features

## ✅ Multi-Elevator Support
- Supports multiple elevators operating concurrently
- Each elevator runs in its own thread

## ✅ Request Handling
- Handles floor requests efficiently
- Deduplicates repeated requests automatically
- Uses sorted concurrent sets for optimized movement

## ✅ Scheduling Strategy
- Strategy pattern used for pluggable scheduling
- Default: Nearest Elevator Strategy

## ✅ State Management
State pattern used for elevator behavior:
- IDLE
- MOVING
- DOOR_OPEN
- MALFUNCTION

## ✅ Fault Tolerance
- Elevator malfunction simulation
- Recovery mechanism available
- Malfunctioned elevators excluded from scheduling

## ✅ Logging & Monitoring
- Centralized Singleton Logger
- Logs requests, movement, door operations, and errors

## ✅ Thread Safety
- Concurrent data structures
- Atomic flags for lifecycle control

## ✅ Graceful Shutdown
- Cleanly stops elevator threads
- Prevents resource leaks

---

# 🏗️ Design Patterns Used

## 1. Strategy Pattern
Used for elevator selection logic.  
Allows switching scheduling algorithms easily.

Examples:
- Nearest elevator
- Load-based scheduling
- Priority scheduling

---

## 2. State Pattern
Encapsulates elevator behavior based on state.

Each state controls:
- Movement
- Door operations
- Transition logic

---

## 3. Singleton Pattern
Used for centralized logging.

---

# 📦 Project Structure

```
ElevatorSystem
│
├── Elevator
├── ElevatorController
├── SchedulerStrategy
├── NearestStrategy
├── ElevatorState
├── IdleState
├── MovingState
├── DoorState
├── BuildingConfig
├── TimingConfig
└── Main
```

---

# ⚙️ Configuration

## BuildingConfig
Defines:
- Minimum floor
- Maximum floor

## TimingConfig
Defines:
- Time per floor movement
- Door open/close time

---

# ▶️ How to Run

## 1. Compile
```bash
javac Main.java
```

## 2. Run
```bash
java Main
```

---

# 🧪 Example Simulation

```java
controller.request(5);
controller.request(10);
controller.request(3);

controller.malfunction(1);
controller.recover(1);

controller.shutdown();
```

---

# 🚦 Edge Cases Handled

- Invalid floor requests
- Duplicate requests
- All elevators in malfunction
- Empty elevator pool
- Graceful shutdown
- Concurrent requests

---

# 📈 Performance Considerations

- Uses ConcurrentSkipListSet for sorted requests
- Minimizes unnecessary travel
- Efficient nearest-car dispatch

---

# 🔮 Possible Improvements

- Load-based scheduling
- Destination grouping
- AI-based prediction
- Metrics dashboard
- Distributed elevator system
- Microservice architecture
- Persistence layer
- Real-time monitoring UI

---

# 🎯 Interview Value

This project demonstrates:
- Strong OOP fundamentals
- SOLID principles
- Concurrency handling
- Design pattern usage
- Production-style thinking

Perfect for:
- SDE interviews
- LLD rounds
- System design discussions

---

# 👨‍💻 Author

Designed as a learning and interview-prep project for scalable system design.
