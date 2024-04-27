package com.brent.ik.graphs;

import java.util.ArrayList;
import java.util.List;

public class DFSRecursiveComponents {


    static void dfsTraversalHelper(int startNode, List<List<Integer>> graph,
                                   List<Integer> answer, boolean[] isVisited, int[] components) {
        answer.add(startNode);
        isVisited[startNode] = true;
        var adjNodes = graph.get(startNode);
        for (Integer adj : adjNodes) {
            if (!isVisited[adj]) {
                dfsTraversalHelper(adj, graph, answer, isVisited, components);
            }
        }
    }

    public static Integer dfs_components(int n, List<List<Integer>> edges) {
        List<List<Integer>> graph = new ArrayList<>();
        List<Integer> answer = new ArrayList<>();
        boolean[] isVisited = new boolean[n];

        // Initialize graph
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Making a graph from the input edges
        for (List<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            graph.get(u).add(v);
            graph.get(v).add(u); // For undirected graph
        }

        int[] components = new int[1];
        components[0] = 0;
        for (int i = 0; i < n; i++) {
            if (!isVisited[i]) {
                components[0] = components[0] + 1;
                dfsTraversalHelper(i, graph, answer, isVisited, components);
            }
        }

        return components[0];
    }
}
