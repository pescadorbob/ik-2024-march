package com.brent.ik.trees;

public class TreeNodeBuilder<T> {
    private TreeNode<T> node;

    private TreeNodeBuilder(T val) {
        node = new TreeNode<>(val);
    }

    public static <T> TreeNodeBuilder<T> aNode(T val) {
        return new TreeNodeBuilder<>(val);
    }

    public TreeNodeBuilder<T> withLeft(T val) {
        node.left = new TreeNode<>(val);
        return this;
    }

    public TreeNodeBuilder<T> withRight(T val) {
        node.right = new TreeNode<>(val);
        return this;
    }

    public TreeNodeBuilder<T> withLeft(TreeNodeBuilder<T> leftBuilder) {
        node.left = leftBuilder.build();
        return this;
    }

    public TreeNodeBuilder<T> withRight(TreeNodeBuilder<T> rightBuilder) {
        node.right = rightBuilder.build();
        return this;
    }

    public TreeNode<T> build() {
        return node;
    }
}
