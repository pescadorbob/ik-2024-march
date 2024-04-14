package com.brent.ik.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.LinkedList;
import java.util.*;

public class DFSRecursiveIsTree {


    static void dfsTraversalHelper(int startNode, List<List<Integer>> graph, 
	    List<Integer> answer, int[] isVisited,int[]components) {
			answer.add(startNode);
        isVisited[startNode] = components[0];
		var adjNodes = graph.get(startNode);
		for(Integer adj:adjNodes){
			if(isVisited[adj]==-1){
				dfsTraversalHelper(adj,graph,answer,isVisited,components);
			}
		}
    }

    public static boolean isTree(int n, List<List<Integer>> edges) {
        List<List<Integer>> graph = new ArrayList<>();
        List<Integer> answer = new ArrayList<>();
        int[] isVisited = new int[n];

        // Initialize graph
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
			isVisited[i] = -1;
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
        for (int i = 0; i < n; i++) {
            if (isVisited[i]==-1) {
				components[0] = components[0]+1;
                dfsTraversalHelper(i, graph, answer, isVisited, components);
            }
        }

        return components[0]==1;
    }
}
