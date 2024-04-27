package com.brent.ik.trees;

import java.util.ArrayList;
import java.util.List;


public class NaryTreeNode {

    private final Integer key;
    private final String value;

    private final List<NaryTreeNode> children;

    public NaryTreeNode(Integer key, String value) {
        children = new ArrayList<NaryTreeNode>();
        this.value = value;
        this.key = key;
    }

    public static void prettyPrint(NaryTreeNode node, StringBuffer sb, int level) {
        if (node == null) return;
        for (int i = 0; i < level; i++) {
            sb.append(" ");
        }
        sb.append(String.format("%d)%s%n", node.key, node.value));
        for (NaryTreeNode child : node.children) {
            prettyPrint(child, sb, level + 1);
        }
    }

    public static void preOrder(NaryTreeNode node, StringBuffer sb) {
        if (node == null) return;

        sb.append(String.format("%d)%s ", node.key, node.value));
        for (NaryTreeNode child : node.children) {
            preOrder(child, sb);
        }
    }

    public static void postOrder(NaryTreeNode node, StringBuffer sb) {
        if (node == null) return;
        for (NaryTreeNode child : node.children) {
            postOrder(child, sb);
        }
        sb.append(String.format("%d)%s ", node.key, node.value));
    }

    public NaryTreeNode addChild(Integer key, String value) {
        var newNode = new NaryTreeNode(key, value);
        children.add(newNode);
        return newNode;
    }

    public Integer size() {
        var thisSize = 1;
        for (NaryTreeNode child : children) {
            thisSize += child.size();
        }
        return thisSize;

    }

    public String prettyPrint() {
        var output = new StringBuffer();
        prettyPrint(this, output, 0);
        return output.toString().trim();
    }

    public String preOrder() {
        var output = new StringBuffer();
        preOrder(this, output);
        return output.toString().trim();
    }

    public String postOrder() {
        var output = new StringBuffer();
        postOrder(this, output);
        return output.toString().trim();
    }


}




