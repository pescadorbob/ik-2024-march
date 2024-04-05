package com.brent.ik.trees;

import java.util.Objects;

public class BST {
    private final TreeNode root;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BST bst = (BST) o;
        return Objects.equals(root, bst.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }

    public BST(Builder builder) {
        root = builder.root;
    }

    public BST() {
        root = new TreeNode();
    }

    public void add(int val) {
        root.setValue(val);
    }

    public static class TreeNode {

        private int value;

        public void setValue(int val) {
            this.value = val;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TreeNode treeNode = (TreeNode) o;
            return value == treeNode.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
    public static class Builder {
        TreeNode root;
        public Builder(){
            root = new TreeNode();
        }
        public Builder withRootNode(int val) {
            root.setValue(val);
            return this;
        }

        public BST build() {
            return new BST(this);
        }
    }
    public String toString(){
        return String.format("Root %s", root.value);
    }
}
