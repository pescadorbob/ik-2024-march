package com.brent.ik.trees;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.assertj.core.api.Assertions.assertThat;

public class LevelOrderTreeTraversalShould {
    @Test
    void produceTheTreeByLevel_givenATree() {
        var tree = aNode('A')
                .left(aNode('B').build())
                .right(aNode('C')
                        .right(aNode('D').build()).build()).build();
        var expected = "ABCD";
        var actual = levelOrderTraversal(tree);
        assertThat(actual).isEqualTo(expected);
    }

    private String levelOrderTraversal(TreeNode tree) {
        var q = new ArrayDeque<TreeNode>();
        q.add(tree);
        var result = new StringBuffer();
        while(!q.isEmpty()){
            var node = q.poll();
            result.append(node.value);
            if(node.left != null){
                q.add(node.left);
            }
            if(node.right != null){
                q.add(node.right);
            }
        }
        return result.toString();
    }

    public static TreeNodeBuilder aNode(Character value) {
        return new TreeNodeBuilder(value);
    }

    public static class TreeNodeBuilder {
        Character value;
        TreeNode left;
        TreeNode right;

        private TreeNodeBuilder(Character value) {
            this.value = value;
        }

        public TreeNodeBuilder left(TreeNode node) {
            left = node;
            return this;
        }

        public TreeNodeBuilder right(TreeNode node) {
            right = node;
            return this;
        }


        public TreeNode build() {
            return new TreeNode(value, left, right);
        }

    }

    public static class TreeNode {
        Character value;
        TreeNode left;
        TreeNode right;

        public TreeNode(Character value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Character getValue() {
            return value;
        }

        public void setValue(Character value) {
            this.value = value;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }
    }
}
