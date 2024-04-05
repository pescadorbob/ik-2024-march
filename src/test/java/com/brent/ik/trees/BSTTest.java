package com.brent.ik.trees;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BSTTest {
    @Test
    void shouldAddTreeNodeAtRoot_GivenEmpty(){
        var val = 1;
        var expectedBST = new BST.Builder().withRootNode(val).build();
        var bst = new BST();

        bst.add(val);

        assertThat(bst).isEqualTo(expectedBST);
    }
}
