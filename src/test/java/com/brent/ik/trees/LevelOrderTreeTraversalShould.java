package com.brent.ik.trees;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LevelOrderTreeTraversalShould {

    static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of(aNode('A')
                        .withLeft('B')
                        .withRight(aNode('C').withRight('D')).build(), "ABCD"),
                Arguments.of(aNode('A')
                                .withLeft(aNode('B').withLeft('C').withRight('D')).build()
                        , "ABCD")
        );
    }

    @ParameterizedTest
    @MethodSource("testArguments")
    void produceTheTreeByLevel_givenATree(TreeNode tree, String expected) {
        var actual = levelOrderTraversal(tree);
        assertThat(actual).isEqualTo(expected);
    }

    private String levelOrderTraversal(TreeNode tree) {
        var q = new ArrayDeque<TreeNode>();
        q.add(tree);
        var result = new StringBuffer();
        while (!q.isEmpty()) {
            var node = q.poll();
            result.append(node.value);
            if (node.left != null) {
                q.add(node.left);
            }
            if (node.right != null) {
                q.add(node.right);
            }
        }
        return result.toString();
    }

    public static TreeNodeBuilder aNode(Character value) {
        return new TreeNodeBuilder(value);
    }

    public static class TreeNodeBuilder {
        private TreeNode node;

        private TreeNodeBuilder(Character val) {
            node = new TreeNode(val);
        }

        public TreeNodeBuilder withLeft(Character val) {
            node.left = new TreeNode(val);
            return this;
        }

        public TreeNodeBuilder withRight(Character val) {
            node.right = new TreeNode(val);
            return this;
        }

        public TreeNodeBuilder withLeft(TreeNodeBuilder leftBuilder) {
            node.left = leftBuilder.build();
            return this;
        }

        public TreeNodeBuilder withRight(TreeNodeBuilder rightBuilder) {
            node.right = rightBuilder.build();
            return this;
        }

        public TreeNode build() {
            return node;
        }
    }


    public static class TreeNode {
        Character value;
        TreeNode left;
        TreeNode right;

        public TreeNode(Character value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public TreeNode(Character value) {
            this.value = value;
        }

        public Character getValue() {
            return value;
        }

        public void setValue(Character value) {
            this.value = value;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }
    }
}
