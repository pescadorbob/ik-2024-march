package com.brent.ik.graphs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static com.brent.ik.graphs.TransposeCompleteGraph.create_transpose;
import static org.assertj.core.api.Assertions.assertThat;

public class TransposeCompleteGraphTest {

    @Test
    void shouldTransposeGraph_GivenCompleteGraph() throws IOException {
        String inputJson = """
                {
                "edges": [
                [1, 2],
                [2, 3],
                [3, 1]
                ]
                }""";
        var json = new ObjectMapper();
        EdgeGraphData edgeGraphData = json.readValue(inputJson, EdgeGraphData.class);

        String expectedJson = """
                [
                [2, 1],
                [3, 2],
                [1, 3]
                ]""";
        List<List<Integer>> expected = json.readValue(expectedJson, new TypeReference<List<List<Integer>>>() {
        });

        var convertedInput = convertEdgeToGraphNode(edgeGraphData);
        var actual = create_transpose(convertedInput);
        var edgeActual = convertGraphNodeToEdge(actual);
        assertThat(edgeActual).hasSameElementsAs(expected);

    }

    private List<List<Integer>> convertGraphNodeToEdge(GraphNode node) {
        var visited = new HashSet<Integer>();
        List<List<Integer>> edges = new ArrayList<>();
        dfs(node, edges, visited);
        return edges;
    }

    private void dfs(GraphNode node, List<List<Integer>> edges, HashSet<Integer> visited) {
        visited.add(node.value);
        for (GraphNode graphNode : node.neighbors) {
            edges.add(Arrays.asList(node.value, graphNode.value));
            if (!visited.contains(graphNode.value)) {
                dfs(graphNode, edges, visited);
            }
        }
    }


    private GraphNode convertEdgeToGraphNode(EdgeGraphData edgeGraphData) {
        return convertEdgeToGraphNode(edgeGraphData.edges);
    }

    private GraphNode convertEdgeToGraphNode(List<List<Integer>> edgeGraphData) {
        Map<Integer, GraphNode> graphNodeMap = new HashMap<>();
        for (List<Integer> edge : edgeGraphData) {
            GraphNode graphNode;
            if (!graphNodeMap.containsKey(edge.get(0))) {
                graphNode = new GraphNode(edge.get(0));
                graphNodeMap.put(edge.get(0), graphNode);
            } else {
                graphNode = graphNodeMap.get(edge.get(0));
            }
            GraphNode neighbor;
            if (!graphNodeMap.containsKey(edge.get(1))) {
                neighbor = new GraphNode(edge.get(1));
                graphNodeMap.put(edge.get(1), neighbor);
            } else {
                neighbor = graphNodeMap.get(edge.get(1));
            }

            graphNode.neighbors.add(neighbor);


        }
        return graphNodeMap.get(edgeGraphData.get(0).get(0));
    }

    public static class EdgeGraphData {
        List<List<Integer>> edges;

        public List<List<Integer>> getEdges() {
            return edges;
        }

        public void setEdges(List<List<Integer>> edges) {
            this.edges = edges;
        }
    }
}
