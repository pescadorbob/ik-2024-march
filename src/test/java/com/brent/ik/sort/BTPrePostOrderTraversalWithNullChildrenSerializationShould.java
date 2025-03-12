package com.brent.ik.sort;

import com.brent.ik.trees.TreeNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

import static com.brent.ik.trees.TreeNodeBuilder.aNode;
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
        Queue<Integer> nodes = new LinkedList<>(sd);
        return deserializeHelper(nodes);
    }

    private TreeNode<Integer> deserializeHelper(Queue<Integer> sd) {
        Integer value = sd.poll();
        if(value == null){
            return null;
        }
        var node = new TreeNode<Integer>(value);

        node.left = deserializeHelper(sd);

        node.right = deserializeHelper(sd);
        return node;
    }

    private List<Integer> serialize(TreeNode<Integer> node) {
        if (node == null) {
            return new ArrayList<>(singletonList(null));
        }
        var res = new ArrayList<>(singletonList(node.value));
        res.addAll(serialize(node.left));
        res.addAll(serialize(node.right));

        return res;
    }


}
