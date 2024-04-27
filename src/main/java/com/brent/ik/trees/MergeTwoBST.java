/*
For your reference:
class BinaryTreeNode {
    Integer value;
    BinaryTreeNode left;
    BinaryTreeNode right;

    BinaryTreeNode(Integer value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
do in order traversal of treeA into arrayA, and treeB into arrayA
walk through the two arrays to merge the two into an array called mergedArray
then create the balanced tree, starting in the middle, cutting the array in half recursively until you have a height-balanced BST

*/
package com.brent.ik.trees;

public class MergeTwoBST {
	
	
	/*
    static BinaryTreeNode merge_two_binary_search_trees(BinaryTreeNode root1, BinaryTreeNode root2) {
        if(root1==null && root2==null) return null;
        if(root1==null && root2!=null) return root2;
        if(root1!=null && root2==null) return root1;
        var arrayA = treeToArrayInOrder(root1);
        var arrayB = treeToArrayInOrder(root2);
        
        var mergedArray = mergeSortedArrays(arrayA,arrayB);
        var balancedTree = arrayToHeightBalancedBST(mergedArray);
        
        return balancedTree;
    }
    
    static ArrayList<Integer> treeToArrayInOrder(BinaryTreeNode root){
        if(root==null) return new ArrayList<Integer>();
        ArrayList<Integer> answer = new ArrayList<>();
        helper1(root,answer);
        return answer;
    }
    static void helper1(BinaryTreeNode root,ArrayList<Integer> answer){
        if(root.left==null && root.right==null){
            answer.add(root.value);// leaf node
            return;
        }
        
        if(root.left!=null) helper1(root.left,answer);
        // in order traversal
        answer.add(root.value);
        if(root.right!=null) helper1(root.right,answer);
    }
    static ArrayList<Integer> mergeSortedArrays(ArrayList<Integer> array1,ArrayList<Integer> array2){
        int index1 = 0;
        int index2 = 0;
        ArrayList<Integer> solution = new ArrayList<>();
        while(index1<array1.size() && index2<array2.size()){
            if(array1.get(index1)<array2.get(index2)){
                solution.add(array1.get(index1));
                index1++;
            } else {
                solution.add(array2.get(index2));
                index2++;
            }
        }
        while(index1<array1.size()){
            solution.add(array1.get(index1));
            index1++;
        }
        while(index2<array2.size()){
            solution.add(array2.get(index2));
            index2++;
        }
        return solution;
    }

    static BinaryTreeNode arrayToHeightBalancedBST(ArrayList<Integer> array, int left,int right){
        int mid = (left+right)/2;
        if(left>right){
            return;
        }
        var node = new BinaryTreeNode(array.get(mid));
        if(isLeft){
            root.left = node;
        } else {
            root.right = node;
        }
        node.left = helper2(array,node,left,mid-1);
        node.right = helper2(array,node,mid+1,right);
    }
	*/
}