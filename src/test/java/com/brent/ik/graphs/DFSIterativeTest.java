package com.brent.ik.graphs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.brent.ik.graphs.DFSIterative.dfs_traversal;
import static org.assertj.core.api.Assertions.assertThat;


class DFSTest {

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
				[0, 4, 2, 3, 1, 5]
""";		
		var json = new ObjectMapper();
		GraphData graphData = json.readValue(inputJson,GraphData.class);
		
		List<Integer> expected = json.readValue(expectedString, new TypeReference<List<Integer>>(){});
		var actual = dfs_traversal(graphData.n,graphData.edges);
		assertThat(actual).isEqualTo(expected);
		
	}
}
/**
<T> T readValue(String content,
                       com.fasterxml.jackson.core.type.TypeReference<T> valueTypeRef)
*/
