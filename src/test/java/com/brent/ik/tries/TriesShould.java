package com.brent.ik.tries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class TriesShould {


    public static Stream<Arguments> perform_functions_as_listed() {
        return Stream.of(
                Arguments.of(asList("insert", "countWordsEqualTo", "countWordsEqualTo", "countWordsStartingWith", "insert", "countWordsEqualTo"),
                        asList("apple", "apple", "app", "app", "app", "app"),
                        asList(null, 1, 0, 1, null, 1)
                ),
                Arguments.of(asList("insert", "search", "search", "startsWith", "insert", "search"),
                        asList("apple", "apple", "app", "app", "app", "app"),
                        asList(null, true, false, true, null, true)
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    void perform_functions_as_listed(List<String> inputMethods, List<Object> inputParameters, List<Object> outputs) throws Exception {
        var trie = new Trie();

        int paramsIndex = 0;
        for (var methodName : inputMethods) {
            Method method = Trie.class.getMethod(methodName, String.class);
            var output = method.invoke(trie, inputParameters.get(paramsIndex));
            var expected = outputs.get(paramsIndex);
            assertThat(output).isEqualTo(expected);
            paramsIndex ++;
        }
    }

    public static Stream<Arguments> count_two_instances_of_app_prefix_given_two_inserts() {
        return Stream.of(
                Arguments.of(asList("apple", "apple"), 2, "app"),
                Arguments.of(asList("apple", "apple"), 2, "apple"),
                Arguments.of(asList("apple", "apple"), 0, "apples"),
                Arguments.of(asList("apple", "apples"), 2, "apple"),
                Arguments.of(asList("apple", "apples"), 1, "apples"),
                Arguments.of(List.of("apple"), 1, "app")
        );

    }

    @ParameterizedTest
    @MethodSource
    void count_two_instances_of_app_prefix_given_two_inserts(List<String> words, int expected, String searchString) {
        var trie = new Trie();
        for (var word : words) {
            trie.insert(word);
        }

        var actual = trie.countWordsStartingWith(searchString);

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> count_two_instances_of_apple_given_two_inserts() {
        return Stream.of(
                Arguments.of(asList("apple", "apple"), 0, ""),
                Arguments.of(asList("", ""), 0, "apple"),
                Arguments.of(asList("", ""), 2, ""),
                Arguments.of(asList("apple", "apple"), 2, "apple"),
                Arguments.of(asList("apple", "apples"), 1, "apple"),
                Arguments.of(asList("apples", "apples"), 0, "apple"),
                Arguments.of(List.of("apple"), 1, "apple")
        );

    }

    @ParameterizedTest
    @MethodSource
    void count_two_instances_of_apple_given_two_inserts(List<String> words, int expected, String searchString) {
        var trie = new Trie();
        for (var word : words) {
            trie.insert(word);
        }

        var actual = trie.countWordsEqualTo(searchString);

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> count_instances_after_remove_of_apple_given_inserts() {
        return Stream.of(
                Arguments.of(asList("apple", "apple"), 1, "apple", "apple"),
                Arguments.of(asList("apple"), 0, "apple", "apple"),
                Arguments.of(emptyList(), 0, "apple", "apple")
        );

    }

    @ParameterizedTest
    @MethodSource
    void count_instances_after_remove_of_apple_given_inserts(List<String> words, int expected, String searchString, String erase) {
        var trie = new Trie();
        for (var word : words) {
            trie.insert(word);
        }
        trie.erase(erase);

        var actual = trie.countWordsEqualTo(searchString);

        assertThat(actual).isEqualTo(expected);
    }
}

class TrieNode {
    Map<Character, TrieNode> value;
    int count = 0;
    boolean isLeaf = false;

    public TrieNode() {
        value = new HashMap<>();
    }

}

class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        var letters = word.toCharArray();
        var currentNode = root;
        for (var letter : letters) {
            TrieNode edge;
            if (currentNode.value.containsKey(letter)) {
                edge = currentNode.value.get(letter);

            } else {
                edge = new TrieNode();
                currentNode.value.put(letter, edge);
            }
            currentNode = edge;
        }
        currentNode.isLeaf = true;
        currentNode.count++;
    }

    public boolean search(String word){
        return countWordsEqualTo(word) != 0;
    }
    public int countWordsEqualTo(String word) {
        var letters = word.toCharArray();
        var currentNode = root;
        boolean wordFound = true;
        for (var letter : letters) {
            TrieNode edge;
            if (currentNode.value.containsKey(letter)) {
                edge = currentNode.value.get(letter);
            } else {
                wordFound = false;
                break;
            }
            currentNode = edge;
        }
        if (wordFound && currentNode.isLeaf) return currentNode.count;
        return 0;
    }

    public boolean startsWith(String prefix){
        return countWordsStartingWith(prefix)!=0;
    }
    public int countWordsStartingWith(String prefix) {
        var letters = prefix.toCharArray();
        var currentNode = root;
        boolean prefixFound = true;
        for (var letter : letters) {
            TrieNode edge;
            if (currentNode.value.containsKey(letter)) {
                edge = currentNode.value.get(letter);
            } else {
                prefixFound = false;
                break;
            }
            currentNode = edge;
        }
        if (prefixFound) return countLeaves(currentNode);
        return 0;
    }

    private int countLeaves(TrieNode currentNode) {
        int count = currentNode.isLeaf ? currentNode.count : 0;
        for (var key : currentNode.value.keySet()) {
            count += countLeaves(currentNode.value.get(key));
        }
        return count;
    }

    public void erase(String word) {
        var letters = word.toCharArray();
        var currentNode = root;
        boolean wordFound = true;
        for (var letter : letters) {
            TrieNode edge;
            if (currentNode.value.containsKey(letter)) {
                edge = currentNode.value.get(letter);
            } else {
                wordFound = false;
                break;
            }
            currentNode = edge;
        }
        if (wordFound && currentNode.isLeaf) currentNode.count--;


    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * int param_2 = obj.countWordsEqualTo(word);
 * int param_3 = obj.countWordsStartingWith(prefix);
 * obj.erase(word);
 */