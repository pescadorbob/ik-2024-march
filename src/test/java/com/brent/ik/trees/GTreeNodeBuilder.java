package com.brent.ik.trees;

public class GTreeNodeBuilder<T> {
    private GTreeNode<T> node;

    private GTreeNodeBuilder(T val) {
        node = new GTreeNode<>(val);
    }

    public static <T> GTreeNodeBuilder<T> aNode(T val) {
        return new GTreeNodeBuilder<>(val);
    }

    public GTreeNodeBuilder<T> withLeft(T val) {
        node.left = new GTreeNode<>(val);
        return this;
    }

    public GTreeNodeBuilder<T> withRight(T val) {
        node.right = new GTreeNode<>(val);
        return this;
    }

    public GTreeNodeBuilder<T> withLeft(GTreeNodeBuilder<T> leftBuilder) {
        node.left = leftBuilder.build();
        return this;
    }

    public GTreeNodeBuilder<T> withRight(GTreeNodeBuilder<T> rightBuilder) {
        node.right = rightBuilder.build();
        return this;
    }

    public GTreeNode<T> build() {
        return node;
    }
}
