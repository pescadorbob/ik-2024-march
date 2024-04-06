package com.brent.ik.trees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BSTTest {

	@Test
	void shouldDeleteNode_givenNodeHasTwoChildren(){
		var bst = createTestTree();
		var expectedBST = createTestTree();
		bst(expectedBST,97).left = null;
		bst(expectedBST,88).key = 93;
		
		
		var valueToDelete = 88;
		bst.delete(valueToDelete);
        assertThat(bst).isEqualTo(expectedBST);		
	}		
	
	@Test
	void shouldDeleteNode_givenNodeHasOneChild(){
		var bst = createTestTree();
		var expectedBST = createTestTree();
		bst(expectedBST,97).left = null;
		bst(expectedBST,97).key = 93;
		
		var valueToDelete = 97;
		bst.delete(valueToDelete);
        assertThat(bst).isEqualTo(expectedBST);		
	}		

    @Test
    void shouldReturnDifferentHashes_givenDifferentTrees(){
        var bst1 = createTestTree();
        var bst2 = createTestTree();

        assertThat(bst1.hashCode()).isNotEqualTo(bst2.hashCode());
    }
    @Test
    void shouldFailEquals_givenUnequalTrees(){
        var tree1= createTestTree();
        var tree2= createTestTree();
        bst(tree2,76).left = null;

        assertThat(tree1).isNotEqualTo(tree2);
    }
	@Test
	void shouldDeleteNode_givenNodeIsALeaf(){
		var bst = createTestTree();
		var expectedBST = createTestTree();
		bst(expectedBST,76).left = null;
		
		var valueToDelete = 68;
		bst.delete(valueToDelete);
        assertThat(bst).isEqualTo(expectedBST);		
	}		
	
	private static Stream<Arguments> predecessorProvider() {
		return Stream.of(
		  Arguments.of(44, 54),
		  Arguments.of(65, 68),
		  Arguments.of(null, 8),
		  Arguments.of(88, 93)
		  
		);
	}

    @ParameterizedTest
    @MethodSource("predecessorProvider")
	void shouldFindPredecessor_givenFullTree(Integer predecessor,Integer givenKey){
		var bst = createTestTree();
		
		var actual = bst.predecessor(givenKey);
		assertThat(actual).isEqualTo(predecessor);
	}
	private static Stream<Arguments> successorProvider() {
		return Stream.of(
		  Arguments.of(44, 54),
		  Arguments.of(65, 68),
		  Arguments.of(97, null),
		  Arguments.of(88, 93)
		  
		);
	}

    @ParameterizedTest
    @MethodSource("successorProvider")
	void shouldFindSuccessor_givenFullTree(Integer givenKey,Integer expectedSuccessor){
		var bst = createTestTree();
		
		var actual = bst.successor(givenKey);
		assertThat(actual).isEqualTo(expectedSuccessor);
	}
		
	@Test
	void shouldReturnMax_givenFullTree(){
		var bst = createTestTree();
        
		var expectedMax = 97;


        var actual = bst.max();
        assertThat(actual).isEqualTo(expectedMax);
	}
	@Test
	void shouldReturnMin_givenFullTree(){
		var expectedBST = createTestTree();
        var _8 = bst(expectedBST,8);
        
		var expectedMin = 8;

        var bst = createTestTree();

        var actual = bst.min();
        assertThat(actual).isEqualTo(expectedMin);
	}
    @Test
    void shouldAddTreeNodeAtRoot_GivenEmpty() {
        var val = 1;
        var expectedBST = new BST.Builder().withRootNode(val).build();
        var bst = new BST();

        bst.insert(val);

        assertThat(bst).isEqualTo(expectedBST);
    }

    Map<BST,Map<Integer, BST.TreeNode>> testBackTracker;

    @BeforeEach
    void setup(){
        testBackTracker = new HashMap<>();
    }
    BST createTestTree(){
        Map<Integer,BST.TreeNode> nodes = new HashMap<>();
        var bst = new BST.Builder().withRootNode(44).build();
        testBackTracker.put(bst,nodes);
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
		var _93 = _97.addLeft(93);
		nodes.put(93,_93);
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

    static Stream<Integer> searchMissProvider() {
        return Stream.of(62,42,82,92,92,81,71,81,61,21,51,31,11,2,22);
    }

    @ParameterizedTest
    @MethodSource("searchMissProvider")
    void shouldNotFindNode_givenNotPresentKey(){
        var bst = createTestTree();

        var node = bst.search(90);

        assertThat(node).isNull();

    }

    static Stream<Integer> searchProvider() {
        return Stream.of(65,44,88,97,93,82,76,80,68,29,54,32,17,8,28);
    }
    @ParameterizedTest
    @MethodSource("searchProvider")
    void shouldFindNode_given13Key(Integer key){

        var bst = createTestTree();

        var expectedNode = bst(bst,key);
        var node = bst.search(key);

        assertThat(node).isEqualTo(expectedNode);

    }
    @Test void shouldInsertNode_given_12(){
        var expectedBST = createTestTree();
        var _8 = bst(expectedBST,8);
        _8.addRight(12);

        var bst = createTestTree();

        bst.insert(12);
        assertThat(bst).isEqualTo(expectedBST);

    }

    private BST.TreeNode bst(BST bst,int key) {
        return testBackTracker.get(bst).get(key);
    }


}
