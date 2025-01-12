package com.brent.ik.trees;

import org.junit.jupiter.api.Test;

import static com.brent.ik.trees.BinaryTreeMaximumWidth.find_maximum_width;
import static com.brent.ik.trees.BinaryTreeMaximumWidth.find_maximum_width_zero_based;
import static com.brent.ik.trees.GTreeNodeBuilder.aNode;
import static org.assertj.core.api.Assertions.assertThat;

public class BinaryTreeMaximumWidthTest {

    @Test
    void shouldCalculateMaxWidth() {

        var expectedWidth = 32;
        var root = createTestTree();

        var actual = find_maximum_width(root);
        assertThat(actual).isEqualTo(expectedWidth);
    }

    @Test
    void shouldCalculateMaxWidth_zero_based() {

        var expectedWidth = 32;
        var root = createTestTree();

        var actual = find_maximum_width_zero_based(root);
        assertThat(actual).isEqualTo(expectedWidth);
    }

    GTreeNode createTestTree() {
        return aNode(11)
                .withRight(aNode(2).withRight(aNode(5).withRight(aNode(6)
                        .withRight(aNode(1).withRight(14)))))
                .withLeft(aNode(7).withLeft(aNode(3).withLeft(aNode(9)
                        .withLeft(aNode(8).withLeft(10))))).build();
    }
}
