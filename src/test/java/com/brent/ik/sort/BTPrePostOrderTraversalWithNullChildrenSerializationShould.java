package com.brent.ik.sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;


public class BTPrePostOrderTraversalWithNullChildrenSerializationShould {

    public static Stream<Arguments> serializationTestData() {
        return Stream.of(
                Arguments.of(aNode(4).withLeft(5).withRight(aNode(4).withRight(90)).build(),
                        asList(4, 5, null, null, 4, null, 90, null, null)),
                Arguments.of(aNode(4).withLeft(5).withRight(aNode(3).withRight(aNode(90).withLeft(6).withRight(1))).build(),
                        asList(4, 5, null, null, 3, null, 90, 6, null, null, 1, null, null)),
                Arguments.of(aNode(9).withLeft(5).withRight(aNode(4).withRight(900)).build(),
                        asList(9,5,null,null,4,null,900,null,null))
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
        Queue<Integer> nodes = new LinkedList<>(sd);
        return deserializeHelper(nodes);
    }

    private TreeNode deserializeHelper(Queue<Integer> sd) {
        var value = sd.poll();
        if(value == null){
            return null;
        }
        var node = new TreeNode(value);

        node.left = deserializeHelper(sd);

        node.right = deserializeHelper(sd);
        return node;
    }

    private List<Integer> serialize(TreeNode node) {
        if (node == null) {
            return new ArrayList<>(singletonList(null));
        }
        var res = new ArrayList<>(singletonList(node.value));
        res.addAll(serialize(node.left));
        res.addAll(serialize(node.right));

        return res;
    }

    static TreeNodeBuilder aNode(Integer value) {
        return new TreeNodeBuilder(value);
    }

    public static class TreeNodeBuilder {
        private TreeNode node;


        private TreeNodeBuilder(Integer val) {
            node = new TreeNode(val);
        }

        public TreeNodeBuilder withLeft(Integer val) {
            node.left = new TreeNode(val);
            return this;
        }

        public TreeNodeBuilder withRight(Integer val) {
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
        Integer value;
        TreeNode left;
        TreeNode right;

        public TreeNode(Integer value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public TreeNode(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
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
