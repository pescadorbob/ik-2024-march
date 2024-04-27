package com.brent.ik.trees;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BinaryTreeMaximumWidth {

    static int find_maximum_width(TreeNode root) {
        int maximum_width = 1;
        Queue<Map.Entry<TreeNode, Integer>> q = new LinkedList<>();
        q.add(new AbstractMap.SimpleEntry<>(root, 0));

        while (!q.isEmpty()) {
            int nodesInCurrentLevel = q.size();
            int startId = q.peek().getValue();
            int leftmostIndex = 0, rightmostIndex = 0;

            for (int i = 0; i < nodesInCurrentLevel; i++) {
                Map.Entry<TreeNode, Integer> currentNode = q.poll();
                int newId = currentNode.getValue() - startId;
                System.out.printf("Node:%d pos:%d startId:%d newId:%d%n",
                        currentNode.getKey().key, currentNode.getValue(), startId, newId);
                leftmostIndex = Math.min(leftmostIndex, newId);
                rightmostIndex = Math.max(rightmostIndex, newId);

                if (currentNode.getKey().left != null) {
                    q.add(new AbstractMap.SimpleEntry<>(currentNode.getKey().left, 2 * newId + 1));
                }
                if (currentNode.getKey().right != null) {
                    q.add(new AbstractMap.SimpleEntry<>(currentNode.getKey().right, 2 * newId + 2));
                }
            }
            maximum_width = Math.max(maximum_width, rightmostIndex - leftmostIndex + 1);
        }
        return maximum_width;
    }

    static int find_maximum_width_zero_based(TreeNode root) {
        int maximum_width = 1;
        Queue<Map.Entry<TreeNode, Integer>> q = new LinkedList<>();
        q.add(new AbstractMap.SimpleEntry<>(root, 0));

        while (!q.isEmpty()) {
            int nodesInCurrentLevel = q.size();
            int startPosition = q.peek().getValue();
            int leftmostIndex = 0, rightmostIndex = 0;

            for (int i = 0; i < nodesInCurrentLevel; i++) {
                Map.Entry<TreeNode, Integer> currentNode = q.poll();
                int nodePosition = currentNode.getValue() - startPosition;
                System.out.printf("Node:%d pos:%d startPosition:%d nodePosition:%d%n",
                        currentNode.getKey().key, currentNode.getValue(), startPosition, nodePosition);
                leftmostIndex = Math.min(leftmostIndex, nodePosition);
                rightmostIndex = Math.max(rightmostIndex, nodePosition);

                if (currentNode.getKey().left != null) {
                    q.add(new AbstractMap.SimpleEntry<>(currentNode.getKey().left, 2 * nodePosition));
                }
                if (currentNode.getKey().right != null) {
                    q.add(new AbstractMap.SimpleEntry<>(currentNode.getKey().right, 2 * nodePosition + 1));
                }
            }
            maximum_width = Math.max(maximum_width, rightmostIndex - leftmostIndex + 1);
        }
        return maximum_width;
    }


}
