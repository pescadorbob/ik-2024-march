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
		this.value = value;
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
	public String preOrder(){
		var output = new StringBuffer();
		preOrder(this,output);
		return output.toString().trim();
	}
	public static void preOrder(NaryTreeNode node,StringBuffer sb){
		if(node==null) return ;
		sb.append(String.format("%d)%s ",node.key,node.value));
		for(NaryTreeNode child:node.children){
			preOrder(child,sb);
		}
	}
		
	
}




