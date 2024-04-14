package com.brent.ik.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.LinkedList;
import java.util.*;

public class DFSRecursiveIsTree {


    static boolean dfsTraversalHelper(int startNode, List<List<Integer>> graph, 
	    List<Integer> answer, int[] isVisited,int[]components, List<Integer> parent) {
			answer.add(startNode);
        isVisited[startNode] = components[0];
		var adjNodes = graph.get(startNode);
		for(Integer neighbor:adjNodes){
			if(isVisited[neighbor]==-1){
				parent.set(neighbor,startNode);
				boolean hasBackTrack = dfsTraversalHelper(neighbor,graph,answer,isVisited,components,parent);
				if(hasBackTrack) return hasBackTrack;
			} else {
				if(parent.get(startNode) !=null && neighbor != parent.get(startNode)){
					// means the visited node wasn't this node's  parent, 
					// so it is a back track					
					return true;
				}
			}
		}
		return false;// meaning hasBackTrack is false;
    }

    public static boolean isTree(int n, List<List<Integer>> edges) {
        List<List<Integer>> graph = new ArrayList<>();
        List<Integer> answer = new ArrayList<>();
        int[] isVisited = new int[n];
		List<Integer> parent = new ArrayList<>();

        // Initialize graph
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
			isVisited[i] = -1;
			parent.add(null);
        }

        // Making a graph from the input edges
        for (List<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            graph.get(u).add(v);
            graph.get(v).add(u); // For undirected graph
        }

		int [] components = new int[1];
		components[0]=0;
		boolean hasBackTrack = false;
        for (int i = 0; i < n; i++) {
            if (isVisited[i]==-1) {
				components[0] = components[0]+1;
                hasBackTrack = dfsTraversalHelper(i, graph, answer, isVisited, components,parent);
				if(hasBackTrack) break;
            }
        }

        return components[0]==1 && !hasBackTrack;
    }
}
