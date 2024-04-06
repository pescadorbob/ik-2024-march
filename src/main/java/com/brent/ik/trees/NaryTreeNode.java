package com.brent.ik.trees;

import com.sun.source.tree.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import java.util.List;
import java.util.ArrayList;


public  class NaryTreeNode {

	private Integer key;
	private String value;
	
	private List<NaryTreeNode> children;
	
	public NaryTreeNode(Integer key, String value){
		children = new ArrayList<NaryTreeNode>();
		this.key = key;
	}
	
	public NaryTreeNode addChild(Integer key, String value){
		var newNode = new NaryTreeNode(key,value);
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




