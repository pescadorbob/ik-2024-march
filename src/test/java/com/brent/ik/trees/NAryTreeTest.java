package com.brent.ik.trees;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NAryTreeTest {
    public static void testMethod(Integer ref) {
        ref = Integer.valueOf(6);
    }

    public static NaryTreeNode defaultTestTree() {
        var root = new NaryTreeNode(1, "CEO");
        root.addChild(2, "Manager 1");
        var man2 = root.addChild(3, "Manager 2");
        var man4 = man2.addChild(4, "Manager 4");
        man2.addChild(5, "Manager 5");
        man4.addChild(6, "dev1");
        man4.addChild(7, "dev2");
        man4.addChild(8, "dev3");
        root.addChild(9, "Manager 3");
        return root;
    }

    @Test
    void shouldBeAssigned() {
        var myInt = Integer.valueOf(5);
        assertThat(myInt.intValue()).isEqualTo(5);
    }

    @Test
    void shouldCompareTwoIntegerValues() {
        var int1 = Integer.valueOf(1);
        var int2 = 2;
        assertThat(int1 < int2).isTrue();
    }

    @Test
    void shouldCompareIntegerValues() {
        var int1 = Integer.valueOf(1);
        var int2 = Integer.valueOf(1);
        assertThat(int1).isEqualTo(int2);
    }

    @Test
    void shouldPrintWithIndent_giveDefaultTree() {
        var expectedOutput = String.format("1)CEO%n 2)Manager 1%n 3)Manager 2%n  4)Manager 4%n   6)dev1%n   7)dev2%n   8)dev3%n  5)Manager 5%n 9)Manager 3");
        var tree = defaultTestTree();

        var output = tree.prettyPrint();
        assertThat(output).isEqualTo(expectedOutput);
    }

    @Test
    void shouldProvidePostOrderTraversal_giveDefaultTree() {
        var expectedOutput = "2)Manager 1 6)dev1 7)dev2 8)dev3 4)Manager 4 5)Manager 5 3)Manager 2 9)Manager 3 1)CEO";
        var tree = defaultTestTree();

        var output = tree.postOrder();
        assertThat(output).isEqualTo(expectedOutput);
    }

    @Test
    void shouldProvidePreOrderTraversal_giveDefaultTree() {
        var expectedOutput = "1)CEO 2)Manager 1 3)Manager 2 4)Manager 4 6)dev1 7)dev2 8)dev3 5)Manager 5 9)Manager 3";
        var tree = defaultTestTree();

        var output = tree.preOrder();
        assertThat(output).isEqualTo(expectedOutput);
    }

    @Test
    void shouldPrintSize_giveDefaultTree() {
        var expectedTree = defaultTestTree();

        assertThat(9).isEqualTo(expectedTree.size());
    }


}
