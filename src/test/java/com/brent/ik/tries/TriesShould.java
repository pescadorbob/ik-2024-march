package com.brent.ik.tries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class TriesShould {


    public static Stream<Arguments> count_two_instances_of_app_prefix_given_two_inserts() {
        return Stream.of(
                Arguments.of(asList("apple","apple"),2,"app"),
                Arguments.of(List.of("apple"),1,"app")
        );

    }

    @ParameterizedTest
    @MethodSource
    void count_two_instances_of_app_prefix_given_two_inserts(List<String> words,int expected,String searchString) {
        var trie = new Trie();
        for (var word:words){
            trie.insert(word);
        }

        var actual = trie.countWordsStartingWith(searchString);

        assertThat(actual).isEqualTo(expected);
    }
    public static Stream<Arguments> count_two_instances_of_apple_given_two_inserts() {
        return Stream.of(
                Arguments.of(asList("apple","apple"),2,"apple"),
                Arguments.of(asList("apple","apples"),1,"apple"),
                Arguments.of(List.of("apple"),1,"apple")
        );

    }

    @ParameterizedTest
    @MethodSource
    void count_two_instances_of_apple_given_two_inserts(List<String> words,int expected,String searchString) {
        var trie = new Trie();
        for (var word:words){
            trie.insert(word);
        }

        var actual = trie.countWordsEqualTo(searchString);

        assertThat(actual).isEqualTo(expected);
    }
}
class TrieNode {
    Map<Character,TrieNode> value;
    int count = 0;
    boolean isLeaf = false;
    public TrieNode(){
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
        for(var letter : letters){
            TrieNode edge;
            if(currentNode.value.containsKey(letter)){
                edge = currentNode.value.get(letter);

            } else {
                edge = new TrieNode();
                currentNode.value.put(letter,edge);
            }
            currentNode = edge;
        }
        currentNode.isLeaf = true;
        currentNode.count ++;
    }

    public int countWordsEqualTo(String word) {
        var letters = word.toCharArray();
        var currentNode = root;
        boolean wordFound=true;
        for(var letter:letters){
            TrieNode edge;
            if(currentNode.value.containsKey(letter)){
                edge = currentNode.value.get(letter);
            } else {
                wordFound = false;
                break;
            }
            currentNode = edge;
        }
        if(wordFound && currentNode.isLeaf) return currentNode.count;
        return 0;
    }

    public int countWordsStartingWith(String prefix) {
        var letters = prefix.toCharArray();
        var currentNode = root;
        boolean prefixFound=true;
        for(var letter:letters){
            TrieNode edge;
            if(currentNode.value.containsKey(letter)){
                edge = currentNode.value.get(letter);
            } else {
                prefixFound = false;
              break;
            }
            currentNode = edge;
        }
        if(prefixFound) return countLeaves(currentNode);
        return 0;
    }

    private int countLeaves(TrieNode currentNode) {
        int count = currentNode.isLeaf?currentNode.count:0;
        for(var key : currentNode.value.keySet()){
            count += countLeaves(currentNode.value.get(key));
        }
        return count;
    }

    public void erase(String word) {

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