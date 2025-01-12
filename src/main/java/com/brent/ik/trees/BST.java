package com.brent.ik.trees;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;

public class BST<T extends Comparable<T>> {
    private TreeNode<T> root;

    public BST() {

    }

    public static class Builder<T extends Comparable<T>> {
        BST<T> node;
        public Builder(){
            node = new BST<>();
        }
        public Builder<T> withRootNode(T value){
            node.root = new TreeNode<>(value);
            return this;
        }
        public BST<T> build(){
            return node;
        }
    }

    private static void postOrder(TreeNode node, StringBuffer sb) {
        if (node == null) return;
        postOrder(node.left, sb);
        postOrder(node.right, sb);
        sb.append(node.value).append(" ");
    }

    private static void inOrder(TreeNode node, StringBuffer sb) {
        if (node == null) return;
        inOrder(node.left, sb);
        sb.append(node.value).append(" ");
        inOrder(node.right, sb);
    }

    private static void preOrder(TreeNode node, StringBuffer sb) {
        if (node == null) return;
        sb.append(node.value).append(" ");
        preOrder(node.left, sb);
        preOrder(node.right, sb);
    }

    public String postOrder() {
        var output = new StringBuffer();
        postOrder(root, output);
        return output.toString().trim();
    }

    public String inOrder() {
        var output = new StringBuffer();
        inOrder(root, output);
        return output.toString().trim();
    }

    public String preOrder() {
        var output = new StringBuffer();
        preOrder(root, output);
        return output.toString().trim();
    }

    public String levelOrderPrint() {
        if (root == null) return "";
        var q = new LinkedList<TreeNode>();
        var output = new StringBuffer();
        q.push(root);
        while (q.size() > 0) {
            var node = q.poll();
            output.append(node.value).append(" ");
            if (node.left != null) {
                q.offer(node.left);
            }
            if (node.right != null) {
                q.offer(node.right);
            }
        }
        return output.toString().trim();
    }

    public TreeNode<T> search(T value) {
        var curr = getRoot();
        while (curr != null) {
            if (value == curr.value) {
                return curr;
            } else if (Objects.compare(value , curr.value, Comparator.naturalOrder())<0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return null;
    }

    public void insert(T value) {
        var newNode = new TreeNode(value);

        TreeNode<T> prev = null;
        var curr = getRoot();
        if (curr == null) {
            root = newNode;
            return;
        }
        while (curr != null) {
            if (value == curr.value) {
                // value already exists
                return;
            } else if (Objects.compare(value, curr.value,Comparator.naturalOrder())<0) {
                prev = curr;
                curr = curr.left;
            } else {
                prev = curr;
                curr = curr.right;
            }
        }
        if (prev != null) {
            if (Objects.compare(value, prev.value,Comparator.naturalOrder())<0) {
                prev.left = newNode;

            } else {
                prev.right = newNode;

            }
        }

    }

    public T min() {
        if (getRoot() == null) return null;
        var curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr.value;
    }

    public T max() {
        if (getRoot() == null) return null;
        var curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr.value;
    }

    public T successor(T value) {
        if (root == null) return null;
        var p = search(value);
        if (p == null) return null;
        TreeNode<T> curr = null;
        // if the node has a right node, then the successor is the one furthest right.
        if (p.right != null) {
            curr = p.right;
            while (curr.left != null) {
                curr = curr.left;
            }
            return curr.value;
        }
        // if it wasn't found, then we must look back through the tree to the furthest left ancestor
        // that has a right ancestor
        // search for p, starting from root
        TreeNode<T> ancestor = null;
        curr = root;
        while (curr.value != p.value) {
            if (Objects.compare(p.value, curr.value,Comparator.naturalOrder())<0) {
                ancestor = curr;
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        if (ancestor != null) {
            return ancestor.value;
        }
        return null;
    }

    public T predecessor(T value) {
        if (root == null) return null;
        var p = search(value);
        if (p == null) return null;
        TreeNode<T> curr = null;
        // if the node has a left node, then the successor is the one furthest left.
        if (p.left != null) {
            curr = p.left;
            while (curr.right != null) {
                curr = curr.right;
            }
            return curr.value;
        }
        // if it wasn't found, then we must look back through the tree to the furthest right ancestor
        // that has a left ancestor
        // search for p, starting from root
        TreeNode<T> ancestor = null;
        curr = root;
        while (curr.value != p.value) {
            if (Objects.compare(p.value, curr.value,Comparator.naturalOrder())<0) {
                curr = curr.left;
            } else {
                ancestor = curr;
                curr = curr.right;
            }
        }
        if (ancestor != null) {
            return ancestor.value;
        }
        return null;
    }

    public TreeNode<T> delete(T value) {
        // find the node
        var curr = getRoot();
        TreeNode<T> prev = null;
        while (curr != null) {
            if (value == curr.value) {
                break; // found it
            } else if (Objects.compare(value, curr.value,Comparator.naturalOrder())<0) {
                prev = curr;
                curr = curr.left;
            } else {
                prev = curr;
                curr = curr.right;
            }
        }
        if (curr == null) {
            // root remains the same
            return getRoot();
        }
        // case 1: node is a leaf
        if (curr.left == null && curr.right == null) {
            // case 1, node is a leaf
            // edge case first, is this the root
            if (prev == null) {
                root = null;
                return null;
            }
            if (curr == prev.left) {
                prev.left = null;
            } else { // curr is prev.right
                prev.right = null;
            }
            return root;
        }
        // case 2: node has one child
        TreeNode<T> child = null;
        if (curr.left == null && curr.right != null) {
            child = curr.right;
        }
        if (curr.left != null && curr.right == null) {
            child = curr.left;
        }
        if (child != null) {
            // special corner case of root node
            if (prev == null) {
                root = child;
                return root;
            }
            if (curr == prev.left) {
                prev.left = child;
            } else {
                prev.right = child;
            }
        }
        // case 3: node has 2 children
        // find the successor, swap the value for the successor, delete the successor.
        if (curr.left != null && curr.right != null) {
            var succ = curr.right;
            prev = curr;

            while (succ.left != null) {
                prev = succ;
                succ = succ.left;
            }

            curr.value = succ.value;
            if (succ == prev.left) {
                prev.left = succ.right;
            } else { //succ is prev right
                prev.right = succ.right;
            }

        }
        return root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BST bst = (BST) o;
        return Objects.equals(root, bst.root);
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public String toString() {
        return String.format("Root %s", root);
    }

}
