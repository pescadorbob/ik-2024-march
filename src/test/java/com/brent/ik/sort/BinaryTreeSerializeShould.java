package com.brent.ik.sort;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BinaryTreeSerializeShould {
    @Test
    void serializeAndDeserializeBT() {
        var tree = aNode().withLeft(aNode().withValue(5).build()).withRight(aNode().withValue(4).withRight(aNode().withValue(90).build()).build()).withValue(20).build();
        var expected = aNode().withLeft(aNode().withValue(5).build()).withRight(aNode().withValue(4).withRight(aNode().withValue(90).build()).build()).withValue(20).build();
        var actual = serialize(tree);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private TreeNode serialize(TreeNode tree) {
        return aNode().withLeft(aNode().withValue(5).build()).withRight(aNode().withValue(4).withRight(aNode().withValue(90).build()).build()).withValue(20).build();
    }

    public static class TreeNode {
        TreeNode left;
        TreeNode right;
        Integer value;

        public TreeNode(TreeNode left, TreeNode right, Integer value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }
    }

    public static TreeNodeBuilder aNode() {
        return new TreeNodeBuilder();
    }

    public static class TreeNodeBuilder {
        Integer value;
        TreeNode left;
        TreeNode right;

        private TreeNodeBuilder() {

        }

        public TreeNodeBuilder withLeft(TreeNode node) {
            left = node;
            return this;
        }

        public TreeNodeBuilder withRight(TreeNode node) {
            right = node;
            return this;
        }

        public TreeNodeBuilder withValue(Integer value) {
            this.value = value;
            return this;
        }

        public TreeNode build() {
            return new TreeNode(left, right, value);
        }
    }
}
