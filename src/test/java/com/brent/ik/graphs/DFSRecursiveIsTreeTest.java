package com.brent.ik.graphs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.brent.ik.graphs.DFSIsItATree.is_it_a_tree;
import static com.brent.ik.graphs.DFSRecursiveIsTree.isTree;
import static org.assertj.core.api.Assertions.assertThat;


class DFSRecursiveIsTreeTest {


    @Test
    void shouldFindIsItATree_givenLargeTreeGraph() throws Exception {
        var inputJsonStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("problem_141.json");

        var json = new ObjectMapper();
        DFSRecursiveIsTree.GraphData2 graphData = json.readValue(inputJsonStream, DFSRecursiveIsTree.GraphData2.class);

        var actual = is_it_a_tree(graphData);
        assertThat(actual).isEqualTo(true);
    }


    @Test
    void shouldFindIsTree_givenLargeTreeGraph() throws Exception {
        var inputJsonStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("problem_141.json");

        var json = new ObjectMapper();
        DFSRecursiveIsTree.GraphData2 graphData = json.readValue(inputJsonStream, DFSRecursiveIsTree.GraphData2.class);

        var actual = isTree(graphData);
        assertThat(actual).isEqualTo(true);
    }
    @Test
    void shouldFindIsTreeFalse_givenGraphSelfLoop() throws Exception {
        var inputJson = """
                				{
                					"n": 4,
                					"edges": [
                					[0, 1],
                					[0, 2],
                					[0, 3],
                					[0, 0]
                					]
                				}
                """;
        var json = new ObjectMapper();
        GraphData graphData = json.readValue(inputJson, GraphData.class);

        var actual = isTree(graphData.n, graphData.edges);
        assertThat(actual).isEqualTo(false);
    }
    @Test
    void shouldFindIsTreeFalse_givenGraphWith2Components() throws Exception {
        var inputJson = """
                				{
                					"n": 6,
                					"edges": [
                					[0, 1],
                					[0, 2],
                					[0, 4],
                					[2, 3]
                					]
                				}
                """;
        var json = new ObjectMapper();
        GraphData graphData = json.readValue(inputJson, GraphData.class);

        var actual = isTree(graphData.n, graphData.edges);
        assertThat(actual).isEqualTo(false);
    }

    @Test
    void shouldFindIsTreeTrue_givenTrueTree() throws Exception {
        var inputJson = """
                				{
                					"n": 6,
                					"edges": [
                					[4, 3],
                					[4, 5],
                					[4, 0],
                					[2, 0],
                					[1, 0]
                					]
                				}
                """;
        var json = new ObjectMapper();
        GraphData graphData = json.readValue(inputJson, GraphData.class);

        var actual = isTree(graphData.n, graphData.edges);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    void shouldFindIsTreeTrue_givenStaticCircleTree() throws Exception {
        var inputJson = """
                				{
                					"n": 5,
                					"edges": [
                					[0, 1],
                					[1, 2],
                					[2, 3],
                					[3, 4]
                					]
                				}
                """;
        var json = new ObjectMapper();
        GraphData graphData = json.readValue(inputJson, GraphData.class);

        var actual = isTree(graphData.n, graphData.edges);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    void shouldFindIsTreeTrue_givenCircleTree() throws Exception {
        GraphData graphData = new GraphData();
        var n = 999;
        graphData.setN(n);
        List<List<Integer>> edges = new ArrayList<>();
        graphData.setEdges(edges);
        //n==3
		/*
		0->1
		1->2
		*/
        for (int i = 0; i < n - 1; i++) {
            var edge = new ArrayList<Integer>();
            edge.add(i);
            edge.add(i + 1);
            edges.add(edge);
        }
        var actual = isTree(graphData.n, graphData.edges);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    void shouldFindIsTreeFalse_givenBackTrack() throws Exception {
        var inputJson = """
                				{
                					"n": 5,
                					"edges": [
                					[0, 1],
                					[0, 2],
                					[0, 4],
                					[2, 3],
                					[3, 1]
                					]
                				}
                """;
        var json = new ObjectMapper();
        GraphData graphData = json.readValue(inputJson, GraphData.class);

        var actual = isTree(graphData.n, graphData.edges);
        assertThat(actual).isEqualTo(false);
    }

    @Test
    void shouldFindIsTreeFalse_givenBackTrackWithDuplicateEdge() throws Exception {
        var inputJson = """
                				{
                					"n": 5,
                					"edges": [
                					[0, 1],
                					[0, 2],
                					[0, 4],
                					[2, 3],
                					[1, 0]
                					]
                				}
                """;
        var json = new ObjectMapper();
        GraphData graphData = json.readValue(inputJson, GraphData.class);

        var actual = isTree(graphData.n, graphData.edges);
        assertThat(actual).isEqualTo(false);
    }



    public static class GraphData {
        private int n;
        private List<List<Integer>> edges;

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }

        public List<List<Integer>> getEdges() {
            return edges;
        }

        public void setEdges(List<List<Integer>> edges) {
            this.edges = edges;
        }
    }
}
/**
 * <T> T readValue(String content,
 * com.fasterxml.jackson.core.type.TypeReference<T> valueTypeRef)
 */
