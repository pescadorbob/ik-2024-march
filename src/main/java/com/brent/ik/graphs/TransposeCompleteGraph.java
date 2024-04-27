package com.brent.ik.graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TransposeCompleteGraph {


    static GraphNode create_transpose(GraphNode node) {
        // create a map of all the nodes as I go
        // create a copy of this to return
        // visit each node, find or make a new node for all the neighbors
        // point them back to this node.
        // return any of the new nodes when done.
        Map<Integer, GraphNode> graphNodeMap = new HashMap<>();
        var visited = new HashSet<Integer>();
        dfs(node, graphNodeMap, visited);
        //
        return graphNodeMap.get(node.value);
    }

    private static void dfs(GraphNode node, Map<Integer, GraphNode> graphNodeMap, HashSet<Integer> visited) {
        visited.add(node.value);
        for (GraphNode neighbor : node.neighbors) {
            GraphNode graphNode;
            if (graphNodeMap.containsKey(neighbor.value)) {
                graphNode = graphNodeMap.get(neighbor.value);
            } else {
                graphNode = new GraphNode(neighbor.value);
                graphNodeMap.put(graphNode.value, graphNode);
            }
            GraphNode graphNodeBack;
            if (graphNodeMap.containsKey(node.value)) {
                graphNodeBack = graphNodeMap.get(node.value);
            } else {
                graphNodeBack = new GraphNode(node.value);
                graphNodeMap.put(graphNodeBack.value, graphNodeBack);
            }
            graphNode.neighbors.add(graphNodeBack);
            if (!visited.contains(graphNode.value)) {
                dfs(neighbor, graphNodeMap, visited);
            }
        }
    }

}
