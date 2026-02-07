# 96. Unique Binary Search Trees

## 🧩 Problem

Given an integer `n`, return the number of structurally unique BSTs (binary search trees) which store values 1 to n.

---

## 📌 Example

Input:
n = 3

Output:
5

---

## 💡 Approach (Dynamic Programming)

For each number `i` from 1 to n:

- Consider `i` as root
- Left subtree has `i-1` nodes
- Right subtree has `n-i` nodes
- Total trees = leftWays × rightWays

This follows the **Catalan Number pattern**.

---

## 🔁 DP Relation

dp[n] = Σ (dp[left] × dp[right])

Where:
- left = root-1
- right = n-root

---

## ✅ Base Cases

dp[0] = 1  
dp[1] = 1  

---

## ⏱ Complexity

Time: O(n²)  
Space: O(n)

---

## 🚀 Java Implementation

See `Solution.java`
