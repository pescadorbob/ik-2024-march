package com.brent.ik.sort;

import com.brent.ik.trees.GTreeNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.brent.ik.trees.GTreeNodeBuilder.aNode;
import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.util.Arrays.asList;
import static java.util.Collections.nCopies;
import static org.assertj.core.api.Assertions.assertThat;


public class BinaryTreeLevelOrderSerializationShould {

    public static Stream<Arguments> serializationTestData() {
        return Stream.of(
                Arguments.of(aNode(4).withLeft(5).withRight(aNode(4).withRight(90)).build(),
                        asList(4, 5, 4, null, null, null, 90, null, null)),
                Arguments.of(aNode(4)
                                .withLeft(5)
                                .withRight(aNode(3)
                                        .withRight(aNode(90)
                                                .withLeft(6).withRight(1)))
                                .build(),
                        asList(4, 5, 3, null, null, null, 90, 6, 1, null, null, null, null)),
                Arguments.of(aNode(9).withLeft(5).withRight(aNode(4).withRight(900)).build(),
                        asList(9, 5, 4, null, null, null, 900, null, null))
        );
    }

    @ParameterizedTest
    @MethodSource("serializationTestData")
    void serializeBT_givenTree(GTreeNode tree, List<Integer> expected) {
        var actualSerialized = serialize(tree);

        assertThat(actualSerialized).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("serializationTestData")
    void deserializeBt_givenTree(GTreeNode<Integer> expected, List<Integer> serializedBT) {
        var actualSerialized = deserialize(serializedBT);
        assertThat(actualSerialized).usingRecursiveComparison().isEqualTo(expected);
    }

    private GTreeNode deserialize(List<Integer> data) {
        if (data == null || data.size() == 0) {
            return null;
        }
        var q = new LinkedList<GTreeNode<Integer>>();
        var index = 0;
        var root = new GTreeNode<>(data.get(index));
        q.add(root);
        while (!q.isEmpty()) {
            var node = q.poll();
            index++;
            var left = data.get(index);
            if (left != null) {
                var lNode = new GTreeNode<>(left);
                q.add(lNode);
                node.left = lNode;
            }
            index++;
            var rightValue = data.get(index);
            if (rightValue != null) {
                var right = new GTreeNode<>(rightValue);
                q.add(right);
                node.right = right;
            }

        }
        return root;
    }

    private List<Integer> serialize(GTreeNode<Integer> tree) {
        var result = new ArrayList<Integer>();
        var q = new LinkedList<GTreeNode<Integer>>();
        q.add(tree);
        while (!q.isEmpty()) {
            var node = q.poll();
            if (node != null) {
                result.add(node.value);
            } else {
                result.add(null);
            }
            if (node == null) continue;
            q.add(node.left);
            q.add(node.right);
        }
        return result;
    }
}
