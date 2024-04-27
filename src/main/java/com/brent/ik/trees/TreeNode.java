package com.brent.ik.trees;

public class TreeNode {

    int key;
    TreeNode right;
    TreeNode left;

    public TreeNode(int key) {
        this.key = key;
    }

    public TreeNode addRight(int key) {
        var node = new TreeNode(key);
        right = node;

        return node;
    }

    public TreeNode addLeft(int key) {
        var node = new TreeNode(key);
        left = node;
        return node;
    }
}
