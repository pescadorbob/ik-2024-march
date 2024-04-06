package com.brent.ik.trees;

import com.sun.source.tree.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import java.util.List;
import java.util.ArrayList;


public  class NaryTreeNode {

	private String key;
	private List<NaryTreeNode> children;
	
	public NaryTreeNode(String key){
		children = new ArrayList<NaryTreeNode>();
		this.key = key;
	}
	
	public NaryTreeNode addChild(String key){
		var newNode = new NaryTreeNode(key);
		children.add(newNode);
		return newNode;
	}
	public Integer size(){
		var thisSize = 1;
		for(NaryTreeNode child : children){
			thisSize += child.size();
		}
		return thisSize;
		
	}
	
}




