package com.brent.ik.tries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.util.Arrays.asList;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

class TriesShould {


    @Test
    void count_1_word_starting_with_prefix_given_insert_of_word_with_that_prefix_and_erase_of_prefix() {
        var trie = new Trie();
        trie.insert("app");
        trie.erase("a");
        var expected = 1;

        var actual = trie.countWordsStartingWith("a");

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> perform_functions_as_listed() {
        return Stream.of(
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
            paramsIndex++;
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

    public static Stream<Arguments> count_two_words_equal_to_search_string_given_two_inserts_of_search_string() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("apple", "apple")
        );
    }

    @ParameterizedTest
    @MethodSource
    void count_two_words_equal_to_search_string_given_two_inserts_of_search_string(String word, String searchString) {
        var trie = new Trie();
        range(0, 2).forEach(it -> trie.insert(word));
        var expected = 2;

        var actual = trie.countWordsEqualTo(searchString);

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> count_zero_instances_of_search_term_given_inserts_excluding_search_term() {
        return Stream.of(
                Arguments.of(asList("apple", "apple"), 0, ""),
                Arguments.of(asList("", ""), 0, "apple")
        );

    }

    @ParameterizedTest
    @MethodSource
    void count_zero_instances_of_search_term_given_inserts_excluding_search_term(List<String> words, int expected, String searchString) {
        var trie = new Trie();
        for (var word : words) {
            trie.insert(word);
        }

        var actual = trie.countWordsEqualTo(searchString);

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> count_zero_words_equal_to_empty_search_string_given_two_inserts_of_non_empty_word() {
        return Stream.of(
                Arguments.of("apple"),
                Arguments.of("banana")
        );
    }

    @ParameterizedTest
    @MethodSource
    void count_zero_words_equal_to_empty_search_string_given_two_inserts_of_non_empty_word(String word) {
        var trie = new Trie();
        range(0, 2).forEach(it -> trie.insert(word));

        var actual = trie.countWordsEqualTo("");

        assertThat(actual).isZero();
    }


    @Test
    void count_one_word_equal_to_search_string_given_inserts_of_search_string_and_longer_word_from_search_string() {
        var trie = new Trie();
        trie.insert("apple");
        trie.insert("apples");
        var expected = 1;

        var actual = trie.countWordsEqualTo("apple");

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> count_zero_word_equal_to_search_string_given_two_inserts_with_shared_prefix_of_search_string() {
        return Stream.of(
                Arguments.of("apples", "apple"),
                Arguments.of("bananas", "banana")
        );

    }

    @ParameterizedTest
    @MethodSource
    void count_zero_word_equal_to_search_string_given_two_inserts_with_shared_prefix_of_search_string(String word, String searchString) {
        var trie = new Trie();
        range(0, 2).forEach(it -> trie.insert(word));

        var expected = 0;

        var actual = trie.countWordsEqualTo(searchString);

        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> count_one_instance_of_word_given_two_inserts_and_one_erase_of_the_word() {
        return Stream.of(
                Arguments.of("apple"),
                Arguments.of("banana")
        );

    }

    @ParameterizedTest
    @MethodSource
    void count_one_instance_of_word_given_two_inserts_and_one_erase_of_the_word(String word) {
        var trie = new Trie();
        range(0, 2).forEach(it -> trie.insert(word));
        trie.erase(word);

        var actual = trie.countWordsEqualTo(word);

        assertThat(actual).isOne();
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
            if (currentNode.hasEdge(letter)) {
                edge = currentNode.getEdge(letter);
            } else {
                edge = currentNode.addEdge(letter);
            }
            edge.incrementStartCount();
            currentNode = edge;
        }
        currentNode.setEnd();
        currentNode.incrementEndCount();
    }

    public boolean search(String word) {
        return countWordsEqualTo(word) != 0;
    }

    public TrieNode searchPrefix(String prefix) {
        var letters = prefix.toCharArray();
        var currentNode = root;
        for (var letter : letters) {
            TrieNode edge;
            if (currentNode.hasEdge(letter)) {
                edge = currentNode.getEdge(letter);
            } else {
                return null;
            }
            currentNode = edge;
        }
        return currentNode;
    }

    public TrieNode searchWord(String word) {
        var node = searchPrefix(word);
        if (node == null) return null;
        if (node.isLeaf()) return node;
        return null;
    }

    public int countWordsEqualTo(String word) {
        var node = searchWord(word);
        if (node != null) return node.getEndCount();
        return 0;
    }

    public boolean startsWith(String prefix) {
        return countWordsStartingWith(prefix) != 0;
    }

    public int countWordsStartingWith(String prefix) {
        var node = searchPrefix(prefix);
        if (node == null) return 0;
        return node.getStartCount();
    }

    public void erase(String word) {
        var letters = word.toCharArray();
        var currentNode = root;

        for (var letter : letters) {
            if (!currentNode.hasEdge(letter)) {
                return;
            }
            currentNode = currentNode.getEdge(letter);
        }
        if (currentNode.getEndCount() == 0) {
            return;
        }
        currentNode = root;
        for (var letter : letters) {
            if (!currentNode.hasEdge(letter)) {
                return;
            }
            currentNode = currentNode.getEdge(letter);
            currentNode.decrementStartCount();
        }
        currentNode.decrementEndCount();

    }

    private static class TrieNode {
        private Map<Character, TrieNode> value;
        private int startCount = 0;
        private int endCount = 0;
        private boolean isLeaf = false;

        public TrieNode() {
            value = new HashMap<>();
        }

        public boolean isLeaf() {
            return isLeaf;
        }

        public void setEnd() {
            isLeaf = true;
        }

        public void incrementEndCount() {
            endCount++;
        }

        public void decrementEndCount() {
            endCount = max(0, endCount - 1);
        }

        public boolean hasEdge(Character letter) {
            return value.containsKey(letter);
        }

        public TrieNode getEdge(Character letter) {
            return value.get(letter);
        }

        public TrieNode addEdge(Character letter) {
            var edge = new TrieNode();
            value.put(letter, edge);
            return edge;
        }

        public int getEndCount() {
            return endCount;
        }

        public Set<Character> edges() {
            return value.keySet();
        }

        public void incrementStartCount() {
            startCount++;
        }

        public int getStartCount() {
            return startCount;
        }

        public void decrementStartCount() {
            startCount = max(0, startCount - 1);
        }
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