package com.brent.ik.sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.util.Arrays.asList;
import static java.util.Collections.nCopies;
import static org.assertj.core.api.Assertions.assertThat;


public class BinaryTreeSerializationShould {

    public static Stream<Arguments> serializationTestData() {
        return Stream.of(
                Arguments.of(aNode(4).withLeft(aNode(5).build()).withRight(aNode(4).withRight(aNode(90).build()).build()).build(),
                        asList(4, 5, 4, null, null, null, 90)),
                Arguments.of(aNode(4).withLeft(aNode(5).build()).withRight(aNode(3).withRight(aNode(90).withLeft(aNode(6).build()).withRight(aNode(1).build()).build()).build()).build(),
                        asList(4, 5, 3, null, null, null, 90, null, null, null, null, null, null, 6, 1)),
                Arguments.of(aNode(9).withLeft(aNode(5).build()).withRight(aNode(4).withRight(aNode(900).build()).build()).build(),
                        asList(9, 5, 4, null, null, null, 900))
        );
    }

    @ParameterizedTest
    @MethodSource("serializationTestData")
    void serializeBT_givenTree(TreeNode tree, List<Integer> expected) {
        var actualSerialized = serialize(tree);

        assertThat(actualSerialized).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("serializationTestData")
    void deserializeBt_givenTree(TreeNode expected, List<Integer> serializedBT) {
        var actualSerialized = deserialize(serializedBT);
        assertThat(actualSerialized).usingRecursiveComparison().isEqualTo(expected);
    }

    private TreeNode deserialize(List<Integer> sd) {
        var root = aNode(sd.get(0)).build();
        deserializeHelper(sd, 1, root);
        return root;
    }

    private void deserializeHelper(List<Integer> sd, int offset, TreeNode root) {
        if (sd.size() < offset + 1) return;
        if (sd.get(offset) != null) {
            root.left = aNode(sd.get(offset)).build();
            deserializeHelper(sd, 2 * (offset + 1), root.left);
        }
        if (sd.get(offset + 1) != null) {
            root.right = aNode(sd.get(offset + 1)).build();
            deserializeHelper(sd, 2 * (offset + 1) + 1, root.right);
        }
    }

    private List<Integer> serialize(TreeNode tree) {
        var level = maxLevel(tree);
        var numNodes = (int) pow(2,level)-1;
        var result = new ArrayList<Integer>(nCopies(numNodes, null));
        serializeHelper(tree, result, 0);
        return result;
    }

    private void serializeHelper(TreeNode node, List<Integer> result, int offset) {
        result.set(offset, node.value);
        if (node.left != null) serializeHelper(node.left, result, 2 * offset + 1);
        if (node.right != null) serializeHelper(node.right, result, 2 * offset + 2);

    }

    private Integer maxLevel(TreeNode tree) {
        if (tree == null) {
            return 0;
        } else {
            var left = maxLevel(tree.left);
            var right = maxLevel(tree.right);
            return 1 + max(left, right);
        }
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

    public static TreeNodeBuilder aNode(Integer value) {
        return new TreeNodeBuilder(value);
    }

    public static class TreeNodeBuilder {
        Integer value;
        TreeNode left;
        TreeNode right;

        private TreeNodeBuilder(Integer value) {
            this.value = value;
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
