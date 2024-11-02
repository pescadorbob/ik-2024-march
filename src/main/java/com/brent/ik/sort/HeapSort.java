package com.brent.ik.sort;

import java.util.ArrayList;

public class HeapSort extends Sorter {
    public ArrayList<Integer> sort(ArrayList<Integer> arr) {
        var maxHeap = new MaxHeap();
        maxHeap.create(arr);
        return maxHeap.sort();

    }

    private static final class MaxHeap {
        private final ArrayList<Integer> heapArray = new ArrayList<>();
        int heapIndex = 1;

        void create(ArrayList<Integer> unsortedArray) {
            heapIndex = 1;
            heapArray.add(0, 0);
            for (int ele : unsortedArray) {
                insert(heapIndex, ele);
                heapIndex++;
            }
        }
        private void insert(int index, int ele) {
            heapArray.add(index, ele);
            heapifyLeaf(heapArray, index);
        }
        private void heapifyLeaf(ArrayList<Integer> heapArray, int index) {
            if (index <= 1) return;
            var parentIndex = index / 2;
            var parentValue = heapArray.get(parentIndex);
            if (parentValue < heapArray.get(index)) {
                swap(heapArray, parentIndex, index);
                heapifyLeaf(heapArray, parentIndex);
            }
        }

        private ArrayList<Integer> sort() {
            var sortedArray = new ArrayList<Integer>();
            for (int i = 1; i < heapIndex; i++) {
                sortedArray.add(null);
            }
            for (int i = heapIndex - 1; i > 0; i--) {
                sortedArray.set(i - 1, removeMax());
            }
            return sortedArray;
        }

        private Integer removeMax() {

            var max = heapArray.get(1);
            swap(heapArray, 1, heapIndex - 1);
            heapIndex--;
            heapifyParent(1);
            return max;
        }

        private void heapifyParent(int nodeIndex) {
            if (nodeIndex >= heapIndex) return;
            var leftNodeIndex = nodeIndex * 2;
            var rightNodeIndex = nodeIndex * 2 + 1;
            if (isVertexLessThanLeftChildOnly(nodeIndex,leftNodeIndex,rightNodeIndex)) {
                swap(heapArray, nodeIndex, leftNodeIndex);
                heapifyParent(leftNodeIndex);
            } else if (isVertexLessThanRightChildOnly(nodeIndex)) {
                swap(heapArray, nodeIndex, rightNodeIndex);
                heapifyParent(rightNodeIndex);
            } else if (isVertexLessThanBothChildren(nodeIndex)) {
                if (heapArray.get(leftNodeIndex) > heapArray.get(rightNodeIndex)) {
                    // left one is greater, so go left
                    swap(heapArray, nodeIndex, leftNodeIndex);
                    heapifyParent(leftNodeIndex);
                } else {
                    // they are equal or right is greater, so go right.
                    swap(heapArray, nodeIndex, rightNodeIndex);
                    heapifyParent(rightNodeIndex);
                }
            }
            // otherwise, it's bigger than the children.

        }

        private boolean isVertexLessThanBothChildren(int nodeIndex) {
            var leftNodeIndex = nodeIndex * 2;
            var rightNodeIndex = nodeIndex * 2 + 1;
            boolean hasLeft = exists(leftNodeIndex);
            boolean hasRight = exists(rightNodeIndex);
            if (!hasLeft || !hasRight) return false;
            return heapArray.get(nodeIndex) < heapArray.get(leftNodeIndex) &&
                    heapArray.get(nodeIndex) < heapArray.get(rightNodeIndex);
        }

        private boolean isVertexLessThanRightChildOnly(int nodeIndex) {
            var leftNodeIndex = nodeIndex * 2;
            var rightNodeIndex = nodeIndex * 2 + 1;
            boolean hasLeft = exists(leftNodeIndex);
            boolean hasRight = exists(rightNodeIndex);
            if (!hasRight) return false;
            return (!hasLeft || heapArray.get(nodeIndex) >= heapArray.get(leftNodeIndex)) &&
                    (heapArray.get(nodeIndex) < heapArray.get(rightNodeIndex));
        }

        private boolean isVertexLessThanLeftChildOnly(int nodeIndex, int leftNodeIndex, int rightNodeIndex) {
            boolean hasLeft = exists(leftNodeIndex);
            boolean hasRight = exists(rightNodeIndex);
            if (!hasLeft) return false;
            return (!hasRight || heapArray.get(nodeIndex) >= heapArray.get(rightNodeIndex)) &&
                    (heapArray.get(nodeIndex) < heapArray.get(leftNodeIndex));
        }

        private boolean exists(int nodeIndex) {
            return nodeIndex < heapIndex;
        }

    }


}
