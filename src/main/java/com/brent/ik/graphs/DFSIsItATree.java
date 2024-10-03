package com.brent.ik.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DFSIsItATree {

    static boolean dfsTraversalHelper(int startNode, List<List<Integer>> graph,
                                      List<Integer> answer, int[] isVisited, int components, List<Integer> parent) {
        answer.add(startNode);
        isVisited[startNode] = components;
        var adjNodes = graph.get(startNode);
        for(Integer neighbor:adjNodes){
            if(isVisited[neighbor]==-1){
                parent.set(neighbor,startNode);
                boolean hasBackTrack = dfsTraversalHelper(neighbor,graph,answer,isVisited,components,parent);
                if(hasBackTrack) return hasBackTrack;
            } else {
                if (parent.get(startNode) != null && !Objects.equals(neighbor,parent.get(startNode))) {
                    // means the visited node wasn't this node's  parent,
                    // so it is a back track
                    return true;
                }
            }
        }
        return false;// meaning hasBackTrack is false;
    }

    static Boolean is_it_a_tree(DFSRecursiveIsTree.GraphData2 graphData2) {
        return is_it_a_tree(graphData2.getNode_count(),graphData2.getEdge_start(),graphData2.getEdge_end());
    }
    static Boolean is_it_a_tree(Integer n, List<Integer> edge_start, List<Integer> edge_end) {
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

        boolean isMultiedge = false;
        boolean isSameEdge = false;

        // Making a graph from the input edges
        for(int i=0;i<edge_start.size();i++){

            int u = edge_start.get(i);
            int v = edge_end.get(i);
            List<Integer> uList = graph.get(u);
            List<Integer> vList = graph.get(v);
            if(uList.contains(v)) {
                isMultiedge = true;
                break;
            }
            if(vList.contains(u)){
                isMultiedge = true;
                break;
            }
            if (u == v) {
                isSameEdge = true;
                break;
            }

            graph.get(u).add(v);
            graph.get(v).add(u); // For undirected graph
        }
        if(isMultiedge) return false;
        if (isSameEdge) return false;


        int components = 0;

        boolean hasBackTrack = false;
        for (int i = 0; i < n; i++) {
            if (isVisited[i]==-1) {
                components++;
                hasBackTrack = dfsTraversalHelper(i, graph, answer, isVisited, components,parent);
                if(hasBackTrack) break;
            }
        }

        return components==1 && !hasBackTrack;
    }
}
