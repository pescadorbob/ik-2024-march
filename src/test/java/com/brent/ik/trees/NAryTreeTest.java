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

public class NAryTreeTest {
	
	@Test
	void shouldProvidePostOrderTraversal_giveDefaultTree(){
		var expectedOutput = "2)Manager 1 6)dev1 7)dev2 8)dev3 4)Manager 4 5)Manager 5 3)Manager 2 9)Manager 3 1)CEO";
		var tree = defaultTestTree();
		
		var output = tree.postOrder();
		assertThat(output).isEqualTo(expectedOutput);
	}

	@Test
	void shouldProvidePreOrderTraversal_giveDefaultTree(){
		var expectedOutput = "1)CEO 2)Manager 1 3)Manager 2 4)Manager 4 6)dev1 7)dev2 8)dev3 5)Manager 5 9)Manager 3";
		var tree = defaultTestTree();
		
		var output = tree.preOrder();
		assertThat(output).isEqualTo(expectedOutput);
	}

	@Test
	void shouldPrintSize_giveDefaultTree(){
		var expectedTree = defaultTestTree();
		
		assertThat(9).isEqualTo(expectedTree.size());
	}
	
	public static NaryTreeNode defaultTestTree(){
		var root = new NaryTreeNode(1,"CEO");
		root.addChild(2,"Manager 1");
		var man2 = root.addChild(3,"Manager 2");
		var man4 = man2.addChild(4,"Manager 4");
		man2.addChild(5,"Manager 5");
		man4.addChild(6,"dev1");
		man4.addChild(7,"dev2");
		man4.addChild(8,"dev3");
		root.addChild(9,"Manager 3");
		return root;
	}


}
