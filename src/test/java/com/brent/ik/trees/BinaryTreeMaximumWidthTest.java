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
import static com.brent.ik.trees.BinaryTreeMaximumWidth.*;

public class BinaryTreeMaximumWidthTest {
	
	@Test
	void shouldCalculateMaxWidth(){
		
		var expectedWidth = 32;
		var root = createTestTree();
		
		var actual = find_maximum_width(root);
		assertThat(actual).isEqualTo(expectedWidth);
	}		

    TreeNode createTestTree(){
        var root = new TreeNode(11);

		var _7 = root.addLeft(7);
		var _2 = root.addRight(2);
		var _3 = _7.addLeft(3);
		var _5 = _2.addRight(5);
		var _9 = _3.addLeft(9);
		var _6 = _5.addRight(6);
		var _8 = _9.addLeft(8);
		var _1 = _6.addRight(1);
		var _10 = _8.addLeft(10);
		var _4 = _1.addRight(4);        
        return root;
    }
}
