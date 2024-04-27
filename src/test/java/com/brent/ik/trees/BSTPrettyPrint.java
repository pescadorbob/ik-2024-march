package com.brent.ik.trees;

public class BSTPrettyPrint {

    private static int getHeight(BST.TreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private static void printRow(BST.TreeNode node, int totalHeight, int currentHeight) {
        if (node == null) {
            return;
        }

        int width = (int) Math.pow(2, totalHeight - currentHeight) - 1;
        String space = " ".repeat(width);

        if (node.key != Integer.MIN_VALUE) {
            System.out.printf("%" + width + "s", node.key);
        } else {
            System.out.print(" ".repeat(width));
        }

        System.out.print(space);
    }

    public static void prettyPrintBST(BST.TreeNode root) {
        int height = getHeight(root) * 2;
        for (int i = 0; i < height; i++) {
            printRow(root, height, i);
            System.out.println(); // Move to the next row
        }
    }

    public static void main(String[] args) {
        // Example usage:
        BST.TreeNode root = new BST.TreeNode(50);
        root.left = new BST.TreeNode(30);
        root.right = new BST.TreeNode(70);
        root.left.left = new BST.TreeNode(20);
        root.left.right = new BST.TreeNode(40);
        root.right.left = new BST.TreeNode(60);
        root.right.right = new BST.TreeNode(80);

        System.out.println("Pretty Print of BST:");
        prettyPrintBST(root);
    }
}