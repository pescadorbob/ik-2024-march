package com.brent.ik.graphs;

import java.util.ArrayList;
import java.util.List;

public class DFSRecursiveIsTree {


    static boolean dfsTraversalHelper(int startNode, List<List<Integer>> graph,
                                       int[] isVisited, int components, List<Integer> parent) {
        isVisited[startNode] = components;
        var adjNodes = graph.get(startNode);
        for (Integer neighbor : adjNodes) {
            if (isVisited[neighbor] == -1) {
                parent.set(neighbor, startNode);
                boolean hasBackTrack = dfsTraversalHelper(neighbor, graph, isVisited, components, parent);
                if (hasBackTrack) return hasBackTrack;
            } else {
                if (parent.get(startNode) != null && neighbor.intValue() != parent.get(startNode).intValue()) {
                    // means the visited node wasn't this node's  parent,
                    // so it is a back track
                    return true;
                }
            }
        }
        return false;// meaning hasBackTrack is false;
    }
    public static class GraphData2 {
        private int node_count;
        private List<Integer> edge_start;
        private List<Integer> edge_end;

        public int getNode_count() {
            return node_count;
        }

        public void setNode_count(int node_count) {
            this.node_count = node_count;
        }

        public List<Integer> getEdge_start() {
            return edge_start;
        }

        public void setEdge_start(List<Integer> edge_start) {
            this.edge_start = edge_start;
        }

        public List<Integer> getEdge_end() {
            return edge_end;
        }

        public void setEdge_end(List<Integer> edge_end) {
            this.edge_end = edge_end;
        }
    }
    public static boolean isTree(GraphData2 graphData2) {
        List<List<Integer>> edges = new ArrayList<>();
        for(int i=0;i<graphData2.edge_start.size();i++){
            var edge = new ArrayList<Integer>();
            edge.add(graphData2.edge_start.get(i));
            edge.add(graphData2.edge_end.get(i));
            edges.add(edge);
        }
        return isTree(graphData2.node_count,edges);
    }
    public static boolean isTree(Integer n, List<List<Integer>> edges) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] isVisited = new int[n];
        List<Integer> parent = new ArrayList<>();

        initializeEmptyGraphVisitedArrayAndParentGraph(n, graph, isVisited, parent);

        var graphConditions = makeGraphFromEdges(edges, graph);
        if(graphConditions.isMultiEdge || graphConditions.isEdgeToSelf){
            return false;
        }

        return verifyNoBackTrackOrSeparateComponents(n, isVisited, graph, parent);
    }

    private static boolean verifyNoBackTrackOrSeparateComponents(Integer n, int[] isVisited, List<List<Integer>> graph,  List<Integer> parent) {
        int components = 0;

        boolean hasBackTrack = false;
        for (int i = 0; i < n; i++) {
            if (isVisited[i] == -1) {
                components++;
                if(components>1) return false;
                hasBackTrack = dfsTraversalHelper(i, graph,  isVisited, components, parent);
                if (hasBackTrack) break;
            }
        }

        return components == 1 && !hasBackTrack;
    }

    private static GraphResult makeGraphFromEdges(List<List<Integer>> edges, List<List<Integer>> graph) {

        GraphResult graphResult = new GraphResult();
        graphResult.isMultiEdge = false;
        graphResult.isEdgeToSelf = false;

        // Making a graph from the input edges
        for (List<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            List<Integer> uList = graph.get(u);
            List<Integer> vList = graph.get(v);
            if (uList.contains(v)) {
                graphResult.isMultiEdge = true;
                break;
            }
            if (vList.contains(u)) {
                graphResult.isMultiEdge = true;
                break;
            }
            if (u == v) {
                graphResult.isEdgeToSelf = true;
                break;
            }
            graph.get(u).add(v);
            graph.get(v).add(u); // For undirected graph
        }
        return graphResult;
    }

    private static void initializeEmptyGraphVisitedArrayAndParentGraph(Integer n, List<List<Integer>> graph, int[] isVisited, List<Integer> parent) {
        // Initialize graph
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            isVisited[i] = -1;
            parent.add(null);
        }
    }

    private static class GraphResult {
        public boolean isMultiEdge;
        public boolean isEdgeToSelf;
    }
}
