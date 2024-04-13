package com.brent.ik.graphs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.*;
import java.util.stream.Stream;

import static com.brent.ik.graphs.BFS.*;

import static org.assertj.core.api.Assertions.assertThat;


class BFSTest {

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

	@Test
	void shouldTraverseGraph_givenGraph() throws Exception {
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
		var expectedString = """
[0, 1, 2, 4, 3, 5]
""";		
		var json = new ObjectMapper();
		GraphData graphData = json.readValue(inputJson,GraphData.class);
		
		List<Integer> expected = json.readValue(expectedString, new TypeReference<List<Integer>>(){});
		var actual = bfs_traversal(graphData.n,graphData.edges);
		assertThat(actual).isEqualTo(expected);
		
	}
}
/**
<T> T readValue(String content,
                       com.fasterxml.jackson.core.type.TypeReference<T> valueTypeRef)
*/
