# 95. Unique Binary Search Trees II

## 🧩 Problem

Given an integer `n`, return all the structurally unique BSTs (Binary Search Trees) which store values from 1 to n.

Return the answer in any order.

---

## 📌 Example

Input:
n = 3

Output:
[
 [1,null,2,null,3],
 [1,null,3,2],
 [2,1,3],
 [3,1,null,null,2],
 [3,2,null,1]
]

---

## 💡 Approach

This is a classic **Divide & Conquer + Recursion** problem.

For each number `i` from 1 to n:

- Consider `i` as root
- Recursively build:
  - Left subtrees from `[start…i-1]`
  - Right subtrees from `[i+1…end]`
- Combine each left and right subtree

---

## 🔁 Algorithm

For every root choice:

1. Generate all left subtree possibilities  
2. Generate all right subtree possibilities  
3. Attach each left × right combination to root  

---

## ✅ Base Case

If start > end:
Return a list containing `null`

This helps form valid combinations.

---

## ⏱ Complexity

Time Complexity:
O(Cn) (Catalan number growth)

Space Complexity:
O(Cn)

Where Cn ≈ 4ⁿ / √n

Since n ≤ 8, recursion is safe.

---

## 🚀 Key Learning

- Tree construction
- Divide and conquer
- Catalan number pattern
- Recursion mastery

---

## 💻 Java Implementation

See `Solution.java`
