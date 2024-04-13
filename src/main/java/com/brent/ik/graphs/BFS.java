package com.brent.ik.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.LinkedList;
import java.util.*;

public class BFS{

	static void bfsTraversalHelper(int startNode, List<List<Integer>> graph, List<Integer> answer, boolean[] isVisited) {
        isVisited[startNode] = true;
        answer.add(startNode);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : graph.get(u)) {
                if (!isVisited[v]) {
                    answer.add(v);
                    queue.add(v);
                    isVisited[v] = true;
                }
            }
        }
    }

    public static List<Integer> bfs_traversal(int n, List<List<Integer>> edges) {
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

        for (int i = 0; i < n; i++) {
            if (!isVisited[i]) {
                bfsTraversalHelper(i, graph, answer, isVisited);
            }
        }

        return answer;
    }
}
