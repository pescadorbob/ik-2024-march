package com.brent.ik.trees;

import com.sun.source.tree.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.LinkedList;
import java.util.Queue;
import java.util.AbstractMap;

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
				     currentNode.getKey().key,currentNode.getValue(), startId,newId);
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


    



   
    
}
