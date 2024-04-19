package com.brent.ik.graphs;

import java.util.*;

public class AlienAlphabet {

    public static String getAlienAlphabet(String[] words) {
        // figure out the order word to word
        // build an adjacency list
        // then do a topological search
        var adjList = getAdjList(words);
        var topoSort = toposort(adjList);
        return topoSort;
    }

    private static String toposort(Map<Character, List<Character>> adjList) {

        var visited = new HashSet<Character>();
        Map<Character, Integer> departed = new HashMap<>();
        var timestamp = 0;
        var keys = adjList.keySet();
        var topoMap = new ArrayList<Character>();
        for (char node : keys) {
            if (!visited.contains(node)) {
                boolean hasCycle = dfsCycleFound(node, adjList, visited, timestamp, topoMap, departed);
                if (hasCycle) {
                    return null;
                }
            }
        }
        Collections.reverse(topoMap);
        return charListToString(topoMap);
    }

    private static String charListToString(List<Character> reversed) {
        var output = new StringBuffer();
        for(Character c: reversed){
            output.append(c);
        }
        return output.toString();
    }

    private static boolean dfsCycleFound(char node,
                                         Map<Character, List<Character>> adjList,
                                         HashSet<Character> visited, int timestamp,
                                         ArrayList<Character> topoMap, Map<Character, Integer> departed) {
        visited.add(node);
        timestamp++;
        var neighbors = adjList.get(node);
        for (Character neighbor : neighbors) {
            if(!visited.contains(neighbor)){
                if(dfsCycleFound(neighbor,adjList,visited,timestamp,topoMap,departed)) return true;

            } else if (!departed.containsKey(neighbor)) return true;
        }
        departed.put(node,++timestamp);
        topoMap.add(node);
        return false;
    }

    private static Map<Character, List<Character>> getAdjList(String[] words) {
        Map<Character, List<Character>> adjList = new HashMap<>();
        for (int wI = 0; wI < words.length - 1; wI++) {
            String word1 = words[wI];
            String word2 = words[wI + 1];
            int minLength = Math.min(word1.length(), word2.length());

            for (int i = 0; i < minLength; i++) {
                char a = word1.charAt(i);
                char b = word2.charAt(i);
                if (a != b) {
                    List<Character> neighbors;
                    if (!adjList.containsKey(a)) {
                        neighbors = new ArrayList<>();
                        adjList.put(a, neighbors);
                    } else {
                        neighbors = adjList.get(a);
                    }
                    neighbors.add(b);
                    if(!adjList.containsKey(b)){
                        adjList.put(b,new ArrayList<>());
                    }
                    break;
                }

            }

        }
        if(adjList.size()==0) {
            adjList.put(words[0].charAt(0),new ArrayList<>());
        }

        return adjList;
    }
}
