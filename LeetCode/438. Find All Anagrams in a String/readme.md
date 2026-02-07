# Find All Anagrams in a String (LeetCode 438)

## 📌 Problem

Given two strings **s** and **p**, return all the start indices of `p`'s anagrams in `s`.

You may return the answer in any order.

---

## 🧠 Approach

We use the **Sliding Window + Frequency Array** technique.

### Key Insight

- Any anagram of `p` must have:
  - Same length as `p`
  - Same character frequencies
- So we maintain a fixed-size window of length `p.length()` and compare frequencies efficiently.

---

## 🚀 Algorithm

1. Create a frequency array for `p`
2. Use a sliding window of size `p.length()`
3. As window expands:
   - Decrease frequency for incoming char
   - If char was needed → reduce `required`
4. If window size exceeds `p.length()`:
   - Remove left char
   - Restore frequency
5. If `required == 0` → record index

---

## ⏱️ Complexity

### Time Complexity
