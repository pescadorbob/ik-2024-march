package com.brent.ik.graphs;

import java.util.ArrayList;
import java.util.Objects;

public class GraphNode {
    Integer value;
    ArrayList<GraphNode> neighbors;

    GraphNode(Integer value) {
        this.value = value;
        this.neighbors = new ArrayList(3);
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "value=" + value + " -> " + neighbors.get(0).value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode graphNode = (GraphNode) o;
        return Objects.equals(value, graphNode.value) && Objects.equals(neighbors, graphNode.neighbors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, neighbors);
    }
}
