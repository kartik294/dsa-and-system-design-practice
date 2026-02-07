# Minimum Window Substring (LeetCode 76)

## 📌 Problem
Given two strings **s** and **t**, return the minimum window substring of `s` such that every character in `t` (including duplicates) is included in the window.

If no such substring exists, return an empty string `""`.

The answer is guaranteed to be unique.

---

## 🧠 Approach

We use the **Sliding Window + Frequency Array** technique.

### Key Idea
- Maintain a frequency array for characters in `t`
- Expand the right pointer to satisfy the requirement
- Shrink the left pointer to minimize the window
- Track the smallest valid window

---

## 🚀 Algorithm

1. Store frequency of characters in `t`
2. Initialize:
   - `left = 0`
   - `right = 0`
   - `required = t.length()`
3. Expand `right` pointer:
   - Decrease freq count
   - If char needed → decrease `required`
4. When `required == 0`:
   - Update minimum window
   - Shrink from left
5. Return smallest substring

---

## ⏱️ Complexity

### Time
