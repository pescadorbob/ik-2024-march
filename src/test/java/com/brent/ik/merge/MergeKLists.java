package com.brent.ik.merge;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.presentation.StandardRepresentation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class MergeKLists {

    public static class LinkedListNodeRepresentation extends StandardRepresentation {

        @Override
        public String toStringOf(Object o) {
            if (o instanceof LinkedListNode) {
                LinkedListNode myObject = (LinkedListNode) o;
                return "[" + myObject.value + rest(myObject.next) + "]";
            }
            return super.toStringOf(o);
        }

        private String rest(LinkedListNode next) {

            if(next!=null){
                return ", " + next.value + rest(next.next);
            }
            return "";
        }
    }
    static class LinkedListNode {
        Integer value;
        LinkedListNode next;

        LinkedListNode(Integer value) {
            this.value = value;
            this.next = null;
        }
    }

    @Test
    void shouldMergeListsIntoOne(){
        Assertions.useRepresentation(new LinkedListNodeRepresentation());
        var given = makeLists(Arrays.asList(Arrays.asList(1,3,5),Arrays.asList(3,4),Arrays.asList(7)));
        var expected = makeLinkedList(Arrays.asList(1,3,3,4,5,7));
        var actual = merge_k_lists(given);
        LinkedListAssert.assertThat(actual).isEqualTo(expected);
    }

    public static class LinkedListAssert extends AbstractAssert<LinkedListAssert, LinkedListNode> {

        public LinkedListAssert(LinkedListNode actual) {
            super(actual, LinkedListAssert.class);
        }

        public static LinkedListAssert assertThat(LinkedListNode actual) {
            return new LinkedListAssert(actual);
        }
        public LinkedListAssert isEqualTo(LinkedListNode expected){
            isNotNull();
            var actualList = makeAList(actual);
            var expectedList = makeAList(expected);
            Assertions.assertThat(actualList).isEqualTo(expectedList);
            return this;
        }

        private List<Integer> makeAList(LinkedListNode node) {
            var result = new ArrayList<Integer>();
            result.add(node.value);
            while(node.next != null){
                node = node.next;
                result.add(node.value);
            }
            return result;
        }
        // assertion methods described later
    }



    private ArrayList<LinkedListNode> makeLists(List<List<Integer>> list) {
        var result = new ArrayList<LinkedListNode>();
        for(var sortedList:list){
            result.add(makeLinkedList(sortedList));
        }
        return result;
    }

    private static LinkedListNode makeLinkedList(List<Integer> sortedList) {
        LinkedListNode last = null;
        LinkedListNode first = null;
        for(int val: sortedList){
            var node = new LinkedListNode(val);
            if(last !=null){
                last.next = node;
            } else {
                first = node;
            }
            last = node;
        }
        return first;
    }

    /*
    For your reference:
    class LinkedListNode {
        Integer value;
        LinkedListNode next;

        LinkedListNode(Integer value) {
            this.value = value;
            this.next = null;
        }
    }
    lists
    0: 1,3,5
    1: 3,4
    2: 7
    response:null
    listsHaveMoreElements:true
        minIndex:0
        0: 3,5
        1: 3,4
        2: 7
        response:[1]
    listsHaveMoreElements:true
        minIndex:0
        0: 3,5
        1: 3,4
        2: 7
        response:[1]
    */
    static LinkedListNode merge_k_lists(ArrayList<LinkedListNode> lists) {
        if(lists.size()==0) return null;
        LinkedListNode response= null;
        while(listsHaveMoreElements(lists)){
            var minIndex = getMinIndex(lists);

            response = addNodeAndAdvancePointer(lists,response,minIndex);
        }
        return response;
    }
    /*
    0: 1,3,5
    1: 3,4
    2: 7
    minIndex:0
    minValue:1
      i:0
      node[i:0]:1
      minIndex:0
      i:1
      node[i:1]:3
      minIndex:0
      i:2
      node[i:2]:7
      minIndex:0
    << 0
    */
    static int getMinIndex(ArrayList<LinkedListNode> lists){
        var minIndex = getFirstElementIndex(lists);
        if(minIndex<0) return -1;

        for(int i=0;i<lists.size();i++){
            var node = lists.get(i);
            minIndex = min(lists,minIndex,i);
        }
        return minIndex;
    }
    static int min(ArrayList<LinkedListNode> lists,int index1,int index2){
        LinkedListNode node1 = lists.get(index1);
        LinkedListNode node2 =lists.get(index2);

        if(node1==null && node2==null) return -1;
        if(node1==null) return index2;
        if(node2==null) return index1;
        if(node1.value < node2.value) return index1;
        else return index2;
    }

    static LinkedListNode addNodeAndAdvancePointer(ArrayList<LinkedListNode> lists, LinkedListNode response,int nodeIndex){
        var node = lists.get(nodeIndex);
        if(response!=null){
            addNodeToEndOfResponse(response,node);
        } else {
            response = node;
        }
        lists.set(nodeIndex,node.next);
        node.next = null;
        return response;
    }

    private static void addNodeToEndOfResponse(LinkedListNode response, LinkedListNode node) {
        LinkedListNode end = response;
        while(end.next !=null){
            end = end.next;
        }
        end.next = node;

    }

    static int getFirstElementIndex(ArrayList<LinkedListNode> lists){
        for(int i=0;i<lists.size();i++){
            var node = lists.get(i);
            if(node != null) return i;
        }
        return -1;
    }
    static boolean listsHaveMoreElements(ArrayList<LinkedListNode> lists){

        for(var node : lists){
            if(node != null) return true;
        }
        return false;
    }
/*
    brute force, it seems I need k pointers to the location in each linked list. Then iterate through all the pointers and take the smallest one
    each time, and add it to the end of the merged list.
    e.g.
    0: 1,2,
    1: -1,4

    pointers[0,0]
    ---
    merged[-1]
    0: 1,2,
    1: 4

    pointers[0,1]
    ---
    merged[-1,1]
    0: 2,
    1: 4

    pointers[1,1]
    ---
    merged[-1,1,2]
    0:
    1: 4

    pointers[2,1]
    ---
    merged[-1,1,2,4]
    0:
    1:

    pointers[2,2]
    ---
*/
}
