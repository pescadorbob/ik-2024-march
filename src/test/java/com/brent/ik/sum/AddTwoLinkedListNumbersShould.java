package com.brent.ik.sum;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class AddTwoLinkedListNumbersShould {

    @Test
    void parse_out_the_linked_list_into_numbers_then_add_and_provide_the_solution_as_a_linked_list(){
        var l1 = new ListNode(2,new ListNode(4,new ListNode(3)));
        var l2 = new ListNode(5,new ListNode(6,new ListNode(4)));

        var expected = new ListNode(7,new ListNode(0,new ListNode(8)));

        var actual = addTwoNumbers(l1,l2);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int l1Value=0;
        int l2Value=0;
        var place = 1;
        while(l1!=null){
            l1Value += l1.val*place;
            l1 = l1.next;
            place *= 10;
        }
        place = 1;
        while(l2!=null){
            l2Value += l2.val*place;
            l2 = l2.next;
            place *= 10;
        }

        int sum = l1Value + l2Value;
        System.out.println(String.format("%d+%d=%d",l1Value,l2Value,sum));

        return sumToListNode(sum);
    }

    private ListNode sumToListNode(int sum) {
        int place = (int) Math.pow(10d, (double) new String("" + sum).length() - 1);
        int digit = sum /place;
        var responseNode = new ListNode(digit);
        var nextValue = sum - place*digit;
        place /= 10;
        while(place>=1){
            digit = nextValue/place;
            responseNode = new ListNode(digit,responseNode);

            nextValue = nextValue - digit * place;
            place /= 10;
        }
        return responseNode;
    }

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

}
