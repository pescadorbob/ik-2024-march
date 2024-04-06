package com.brent.ik.trees;

import com.sun.source.tree.Tree;

import java.util.Objects;

public class BST {
    private TreeNode root;


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

    }

    public BST add(int val) {

        if(root == null){
            root = new TreeNode(val);

        } else {

        }
        return this;
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode search(int val) {
        var curr = root;
        while(curr != null){
           if(val == curr.value){
               return curr;
           } else if (val < curr.value){
               curr = curr.left;
           } else {
               curr = curr.right;
           }
        }
        return null;
    }

    public static class TreeNode {

        private int value;
        private TreeNode right;
        private TreeNode left;

        public TreeNode(int value) {
            this.value = value;
        }

        public void setValue(int val) {
            this.value = val;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TreeNode treeNode = (TreeNode) o;
            if (value != treeNode.value) return false;
            return Objects.equals(((TreeNode) o).right,this.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        public TreeNode setRight(TreeNode rightNode) {
            this.right = rightNode;
            return this;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "value=" + value +
                    ", right=" + right +
                    '}';
        }

        public TreeNode addRight(int val) {
            var node = new TreeNode(val);
            this.setRight(node);
            return node;
        }

        public TreeNode addLeft(int val) {
            var node = new TreeNode(val);
            this.setLeft(node);
            return node;
        }

        private void setLeft(TreeNode node) {
            this.left = node;
        }
    }
    public static class Builder {
        TreeNode root;
        TreeNode curr;
        public Builder(){
        }
        public Builder withRootNode(int val) {
            root = new TreeNode(val);
            root.setValue(val);
            curr = root;
            return this;
        }

        public BST build() {
            return new BST(this);
        }

        public Builder withRightNode(int val) {
            var rightNode = new TreeNode(val);
            curr.setRight(rightNode);
            return this;
        }
    }
    public String toString(){
        return String.format("Root %s", root);
    }
}
