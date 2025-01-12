package com.brent.ik.trees;

public class TreeNode<T> {
    public T value;
    public TreeNode<T> left;
    public TreeNode<T> right;

    public TreeNode(T x) {
        value = x;
        left = null;
        right = null;
    }


}
