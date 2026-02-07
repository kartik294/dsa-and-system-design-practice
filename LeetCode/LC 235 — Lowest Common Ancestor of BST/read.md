# 📌 Lowest Common Ancestor of a Binary Search Tree (LeetCode 235)

## 🧩 Problem Statement

Given a **Binary Search Tree (BST)** and two nodes `p` and `q`, return their **Lowest Common Ancestor (LCA)**.

> The Lowest Common Ancestor is defined as the lowest node in the tree that has both nodes as descendants (a node can be a descendant of itself).

---

## 🌳 Key BST Property

For any BST node:

```
Left subtree < Root < Right subtree
```

This property allows efficient searching.

---

## 🚀 Approach (Iterative)

We use the BST property to move in the correct direction:

- If both `p` and `q` are smaller than root → go LEFT  
- If both are greater than root → go RIGHT  
- Otherwise → current node is the LCA  

This works because the split point where one node lies on each side is the LCA.

---

## ✅ Algorithm

1. Start at the root  
2. Compare `p.val` and `q.val` with root value  
3. Move left or right accordingly  
4. When values split, return root  

---

## 💻 Java Implementation

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        
        while (root != null) {
            
            if (p.val < root.val && q.val < root.val) {
                root = root.left;
            }
            else if (p.val > root.val && q.val > root.val) {
                root = root.right;
            }
            else {
                return root;
            }
        }
        
        return null;
    }
}
```

---

## ⏱️ Complexity Analysis

### Time Complexity
```
O(H)
```
- H = height of tree  
- Balanced BST → O(log N)  
- Skewed BST → O(N)  

### Space Complexity
```
O(1)
```
(Iterative solution, no recursion stack)

---

## 📌 Example

### Input
```
root = [6,2,8,0,4,7,9,null,null,3,5]
p = 2
q = 8
```

### Output
```
6
```

---

## ⚠️ Edge Cases

- One node is ancestor of the other  
- Skewed BST  
- p or q equals root  
- Nodes on opposite sides of root  

---

## 🎯 Interview Insight

Using BST properties avoids full traversal and improves efficiency compared to a normal binary tree LCA solution.

---

## 🏷️ Tags

`Binary Search Tree` `LCA` `Tree` `LeetCode` `Interview Prep`

---

⭐ If this helped, consider starring your repo!
