package com.brent.ik.sort;

import com.brent.ik.trees.TreeNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.brent.ik.trees.TreeNodeBuilder.aNode;
import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.util.Arrays.asList;
import static java.util.Collections.nCopies;
import static org.assertj.core.api.Assertions.assertThat;


public class BinaryTreeSerializationShould {

    public static Stream<Arguments> serializationTestData() {
        return Stream.of(
                Arguments.of(aNode(4).withLeft(5).withRight(aNode(4).withRight(90)).build(),
                        asList(4, 5, 4, null, null, null, 90)),
                Arguments.of(aNode(4).withLeft(5).withRight(aNode(3).withRight(aNode(90).withLeft(6).withRight(1))).build(),
                        asList(4, 5, 3, null, null, null, 90, null, null, null, null, null, null, 6, 1)),
                Arguments.of(aNode(9).withLeft(5).withRight(aNode(4).withRight(900)).build(),
                        asList(9, 5, 4, null, null, null, 900))
        );
    }

    @ParameterizedTest
    @MethodSource("serializationTestData")
    void serializeBT_givenTree(TreeNode<Integer> tree, List<Integer> expected) {
        var actualSerialized = serialize(tree);

        assertThat(actualSerialized).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("serializationTestData")
    void deserializeBt_givenTree(TreeNode<Integer> expected, List<Integer> serializedBT) {
        var actualSerialized = deserialize(serializedBT);
        assertThat(actualSerialized).usingRecursiveComparison().isEqualTo(expected);
    }

    private TreeNode<Integer> deserialize(List<Integer> sd) {
        var root = aNode(sd.get(0)).build();
        deserializeHelper(sd, 1, root);
        return root;
    }

    private void deserializeHelper(List<Integer> sd, int offset, TreeNode<Integer> root) {
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

    private List<Integer> serialize(TreeNode<Integer> tree) {
        var level = maxLevel(tree);
        var numNodes = (int) pow(2,level)-1;
        var result = new ArrayList<Integer>(nCopies(numNodes, null));
        serializeHelper(tree, result, 0);
        return result;
    }

    private void serializeHelper(TreeNode<Integer> node, List<Integer> result, int offset) {
        result.set(offset, node.value);
        if (node.left != null) serializeHelper(node.left, result, 2 * offset + 1);
        if (node.right != null) serializeHelper(node.right, result, 2 * offset + 2);

    }

    private Integer maxLevel(TreeNode<Integer> tree) {
        if (tree == null) {
            return 0;
        } else {
            var left = maxLevel(tree.left);
            var right = maxLevel(tree.right);
            return 1 + max(left, right);
        }
    }

}
