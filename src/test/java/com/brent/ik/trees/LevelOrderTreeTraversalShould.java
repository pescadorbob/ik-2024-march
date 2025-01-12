package com.brent.ik.trees;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.stream.Stream;

import static com.brent.ik.trees.GTreeNodeBuilder.aNode;
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
    void produceTheTreeByLevel_givenATree(GTreeNode<Character> tree, String expected) {
        var actual = levelOrderTraversal(tree);
        assertThat(actual).isEqualTo(expected);
    }

    private String levelOrderTraversal(GTreeNode<Character> tree) {
        var q = new ArrayDeque<GTreeNode<Character>>();
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



}
