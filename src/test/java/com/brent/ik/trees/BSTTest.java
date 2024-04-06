package com.brent.ik.trees;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BSTTest {
    @Test
    void shouldAddTreeNodeAtRoot_GivenEmpty() {
        var val = 1;
        var expectedBST = new BST.Builder().withRootNode(val).build();
        var bst = new BST();

        bst.add(val);

        assertThat(bst).isEqualTo(expectedBST);
    }

    Map<Integer,BST.TreeNode> nodes = new HashMap<>();
    BST createTestTree(){
        var bst = new BST.Builder().withRootNode(44).build();
        var rootNode = bst.getRoot();
        nodes.put(44,rootNode);
        var _88 = rootNode.addRight(88);
        nodes.put(88,_88);
        var _17 = rootNode.addLeft(17);
        nodes.put(17,_17);
        var _8 = _17.addLeft(8);
        nodes.put(8,_8);
        var _32 = _17.addRight(32);
        nodes.put(32,_32);
        var _28 = _32.addLeft(28);
        nodes.put(28,_28);
        var _29 = _28.addRight(29);
        nodes.put(29,_29);
        var _97 = _88.addRight(97);
        nodes.put(97,_97);
        var _65 = _88.addLeft(65);
        nodes.put(65,_65);
        var _54 = _65.addLeft(54);
        nodes.put(54,_54);
        var _82 = _65.addRight(82);
        nodes.put(82,_82);
        var _76 = _82.addLeft(76);
        nodes.put(76,_76);
        var _68 = _76.addLeft(68);
        nodes.put(68,_68);
        var _80 = _76.addRight(80);
        nodes.put(80,_80);
        return bst;
    }

    @Test
    void shouldNotFindNode_givenNotPresentValue(){
        var bst = createTestTree();

        var node = bst.search(90);

        assertThat(node).isNull();

    }

    static Stream<Integer> searchProvider() {
        return Stream.of(65,44,88,97,93,82,76,80,68,29,54,32,17,8,28);
    }
    @ParameterizedTest
    @MethodSource("searchProvider")

    void shouldFindNode_given13Value(Integer value){

        var bst = createTestTree();

        var expectedNode = bst(value);
        var node = bst.search(value);

        assertThat(node).isEqualTo(expectedNode);

    }

    private BST.TreeNode bst(int key) {
        return nodes.get(key);
    }
//    @Test
//    void shouldAddTreeNodeAtRight_GivenRoot() {
//        var val = 1;
//        var expectedBST = new BST.Builder().withRootNode(val)
//                .withRightNode(1).build();
//        var bst = new BST();
//
//        bst.add(val).add(2);
//
//        assertThat(bst).isEqualTo(expectedBST);
//    }
}
