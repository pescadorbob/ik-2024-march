package com.brent.ik.trees;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.brent.ik.trees.TreeNodeBuilder.aNode;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class LevelOrderTreeTraversalShould {

    static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of(aNode('A')
                        .withLeft('B')
                        .withRight(aNode('C').withRight('D')).build(),
                        asList(asList('A'),asList('B','C'),asList('D')))
        );
    }

    @ParameterizedTest
    @MethodSource("testArguments")
    void produceTheTreeByLevel_givenATree(TreeNode<Character> tree, List<String> expected) {
        var actual = levelOrderTraversal(tree);
        assertThat(actual).isEqualTo(expected);
    }

    private List<List<Character>> levelOrderTraversal(TreeNode<Character> tree) {
        var q = new ArrayDeque<TreeNode<Character>>();
        q.add(tree);
        var solution = new ArrayList<List<Character>>();
        while (!q.isEmpty()) {
            var size = q.size();
            var row = new ArrayList<Character>();
            for(int i=0;i<size;i++){
                var node = q.poll();
                row.add(node.value);
                if(node.left!=null) q.add(node.left);
                if(node.right!=null) q.add(node.right);
            }
            solution.add(row);
        }
        return solution;
    }



}
