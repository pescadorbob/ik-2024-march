package com.brent.ik.graphs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

public class FindGoldenTileShould {
    @Test
    void find_the_shortest_path_to_a_golden_tile_given_a_grid() {
        var grid = """
                o o o
                s x o
                o o g""";
        var expected = "s e e";
        var actual = findGoldenTile(grid);
        assertThat(actual).isEqualTo(expected);

    }

    private String findGoldenTile(String grid) {
        var startNode = makeGraph(grid);
        return helper(startNode);

    }
    private static class BFSNode {
        List<GridNode> visited;
        String path;
        GridNode value;

        public BFSNode(List<GridNode> visited, String path, GridNode value) {
            this.visited = visited;
            this.path = path;
            this.value = value;
        }
    }

    private String helper(GridNode startTile) {
        // BFS
        Queue<BFSNode> queue = new LinkedList<>();
        queue.add(new BFSNode(new ArrayList<>(),"",startTile));


        while(!queue.isEmpty()){
            var nextNode = queue.poll();
            if(nextNode.value.value.equals("g")){
                return nextNode.path.trim();
            }

            var partialPath = nextNode.path;
            var node = nextNode.value;
            var visited = nextNode.visited;
            enqueue(nextNode, visited, queue, partialPath, node.north,"n ");
            enqueue(nextNode, visited, queue, partialPath, node.south,"s ");
            enqueue(nextNode, visited, queue, partialPath, node.east,"e ");
            enqueue(nextNode, visited, queue, partialPath, node.west,"w ");


        }
        throw new IllegalStateException("No path found to the gold");

    }

    private static void enqueue( BFSNode nextNode, List<GridNode> visited, Queue<BFSNode> queue,
                                String partialPath, GridNode edge,String breadCrumb) {
        if(edge!=null && !nextNode.visited.contains(edge) && !edge.value.equals("x") ){
            var nVisited = new ArrayList<>(visited);
            nVisited.add(edge);
            queue.add(new BFSNode(nVisited, partialPath + breadCrumb, edge));
        }
    }

    private GridNode makeGraph(String gridString) {
        GridNode startNode = null;
        var lines = gridString.trim().split("\n");
        GridNode[][] grid = new GridNode[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            var line = lines[i];
            var nodeChars = line.split(" ");
            grid[i] = new GridNode[nodeChars.length];
            for (int j = 0; j < nodeChars.length; j++) {
                var nodeChar = nodeChars[j];
                grid[i][j] = new GridNode(nodeChar);
                if ("s".equals(nodeChar)) {
                    startNode = grid[i][j];
                }
            }
        }
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {

                if (grid[row][col].value.equals("x")) continue;
                if (col > 0) {
                    grid[row][col].west = grid[row][col - 1];
                }
                if (row > 0) {
                    grid[row][col].north = grid[row - 1][col];
                }
                if (col < grid[row].length - 1) {
                    grid[row][col].east = grid[row][col + 1];
                }
                if (row < grid.length - 1) {
                    grid[row][col].south = grid[row + 1][col];
                }
            }
        }
        return startNode;
    }

    private static class GridNode {
        String value;
        GridNode north;
        GridNode south;
        GridNode east;
        GridNode west;

        public GridNode(String value) {
            this.value = value;
        }
    }
}
