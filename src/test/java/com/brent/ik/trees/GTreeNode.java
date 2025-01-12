package com.brent.ik.trees;

public class GTreeNode<T> {
    public T value;
    public GTreeNode<T> left;
    public GTreeNode<T> right;

    public GTreeNode(T x) {
        value = x;
        left = null;
        right = null;
    }


}
