package com.brent.ik.trees;

import com.sun.source.tree.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.LinkedList;
import java.util.Queue;
import java.util.AbstractMap;

public class TreeNode {

	int key;
	TreeNode right;
	TreeNode left;

	public TreeNode(int key) {
		this.key = key;
	}
    public TreeNode addRight(int key) {
		var node = new TreeNode(key);
		right = node;
		
		return node;
	}

	public TreeNode addLeft(int key) {
		var node = new TreeNode(key);
		left = node;
		return node;
	}
}
