package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AddTwoLinkedListNumbersShould {

    public static Stream<Arguments> inputs() {
        return Stream.of(
                Arguments.of(
                        toNodes(2, 4, 3),
                        toNodes(5, 6, 4),
                        toNodes(7, 0, 8)
                ),
                Arguments.of(
                        toNodes(9, 9, 9, 9, 9, 9, 9),
                        toNodes(9, 9, 9, 9),
                        toNodes(8, 9, 9, 9, 0, 0, 0, 1)
                ),
                Arguments.of(
                        toNodes(9),
                        toNodes(1, 9, 9, 9, 9, 9, 9, 9, 9, 9),
                        toNodes(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)
                )
        );
    }

    private static ListNode toNodes(int... ints) {
        ListNode prev = null;
        for (int i = ints.length - 1; i >= 0; i--) {
            prev = new ListNode(ints[i], prev);
        }
        return prev;
    }

    @ParameterizedTest
    @MethodSource("inputs")
    void parse_out_the_linked_list_into_numbers_then_add_and_provide_the_solution_as_a_linked_list(ListNode l1, ListNode l2, ListNode expected) {

        var actual = addTwoNumbers(l1, l2);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        var carry = 0;
        ListNode dummyHead = new ListNode(0);
        ListNode currentNode = dummyHead;

        while (l1 != null || l2 != null) {
            var x = l1!=null?l1.val:0;
            var y = l2!=null?l2.val:0;
            var sum = x + y + carry;
            var digit = sum % 10;
            carry = sum / 10;
            currentNode.next = new ListNode(digit);
            currentNode = currentNode.next;
            l1 = l1!=null?l1.next:null;
            l2 = l2!=null?l2.next:null;
        }
        if (carry > 0) {

            var node = new ListNode(carry);
            currentNode.next = node;

        }
        return dummyHead.next;
    }


    public static final class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        public String toString() {
            var rep = "" + val;
            if (next != null) {
                rep += "->" + next.toString();
            }
            return rep;
        }
    }

}
