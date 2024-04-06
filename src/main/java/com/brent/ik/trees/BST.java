package com.brent.ik.trees;

import com.sun.source.tree.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.LinkedList;

public class BST {
    private TreeNode root;
	
	public String postOrder(){
		var output = new StringBuffer();
		postOrder(root,output);
		return output.toString().trim();
	}
	
	private static void postOrder(TreeNode node,StringBuffer sb){
		if(node == null) return ;
		postOrder(node.left,sb);
		postOrder(node.right,sb);
		sb.append(node.key).append(" ");
	}
	public String inOrder(){
		var output = new StringBuffer();
		inOrder(root,output);
		return output.toString().trim();
	}
	
	private static void inOrder(TreeNode node,StringBuffer sb){
		if(node == null) return ;
		inOrder(node.left,sb);
		sb.append(node.key).append(" ");
		inOrder(node.right,sb);
	}
	public String preOrder(){
		var output = new StringBuffer();
		preOrder(root,output);
		return output.toString().trim();
	}
	
	private static void preOrder(TreeNode node,StringBuffer sb){
		if(node == null) return ;
		sb.append(node.key).append(" ");
		preOrder(node.left,sb);
		preOrder(node.right,sb);
	}
	public String levelOrderPrint(){
		if(root==null)return "";
		var q = new LinkedList<TreeNode>();
		var output = new StringBuffer();
		q.push(root);
		while(q.size()>0){
			var node = q.poll();
			output.append(node.key).append(" ");
			if(node.left!=null){
				q.offer(node.left);
			}
			if(node.right!=null){
				q.offer(node.right);
			}
		}
		return output.toString().trim();
	}
	
	public TreeNode search(int key) {
		var curr = getRoot();
		while(curr != null){
		   if(key == curr.key){
			   return curr;
		   } else if (key < curr.key){
			   curr = curr.left;
		   } else { 
			   curr = curr.right;
		   }
		} 
		return null;
	}

	public void insert(int key) {
		var newNode = new TreeNode(key);
		
		TreeNode prev = null;
		var curr = getRoot();
		if(curr==null){
			root = newNode;
			return;
		}
		while(curr != null){
		   if(key == curr.key){
			   // key already exists
			   return;
		   } else if (key < curr.key){
			   prev = curr;
			   curr = curr.left;
		   } else { 
			   prev = curr;
			   curr = curr.right;
		   }
		} 
		if(prev!=null){
			if(key < prev.key){
				prev.left = newNode;
				
			} else {
				prev.right = newNode;
				
			}
		}
		return;

	}
	public Integer min(){
		if(getRoot()==null) return null;
		var curr = root;
		while(curr.left !=null){
			curr = curr.left;
		}
		return curr.key;
	}

	public Integer max(){
		if(getRoot()==null) return null;
		var curr = root;
		while(curr.right !=null){
			curr = curr.right;
		}
		return curr.key;
	}
	public Integer successor(Integer key){
		if(root == null) return null;
		var p = search(key);
		if(p==null) return null;
		TreeNode curr = null;
		// if the node has a right node, then the successor is the one furthest right.
		if(p.right!=null){
			curr = p.right;
			while(curr.left != null){
				curr = curr.left;
			}
			return curr.key;
		}
		// if it wasn't found, then we must look back through the tree to the furthest left ancestor
		// that has a right ancestor
		// search for p, starting from root
		TreeNode ancestor = null;
		curr = root;
		while(curr.key != p.key){
			if(p.key < curr.key){
				ancestor = curr;
				curr = curr.left;
			} else {
				curr = curr.right;
			}
		}
		if(ancestor!=null){
			return ancestor.key;
		}
		return null;
	}
	public Integer predecessor(Integer key){
		if(root == null) return null;
		var p = search(key);
		if(p==null) return null;
		TreeNode curr = null;
		// if the node has a left node, then the successor is the one furthest left.
		if(p.left!=null){
			curr = p.left;
			while(curr.right != null){
				curr = curr.right;
			}
			return curr.key;
		}
		// if it wasn't found, then we must look back through the tree to the furthest right ancestor
		// that has a left ancestor
		// search for p, starting from root
		TreeNode ancestor = null;
		curr = root;
		while(curr.key != p.key){
			if(p.key < curr.key){
				curr = curr.left;
			} else {
				ancestor = curr;
				curr = curr.right;
			}
		}
		if(ancestor!=null){
			return ancestor.key;
		}
		return null;
	}
	public TreeNode delete(Integer key){
		// find the node
		var curr = getRoot();
		TreeNode prev = null;
		while(curr != null){
			if(key == curr.key){
				break; // found it
			} else if(key<curr.key){
				prev = curr;
				curr = curr.left;
			} else {
				prev = curr;
				curr = curr.right;
			}
		}
		if(curr == null){
			// root remains the same
			return getRoot();
		}
		// case 1: node is a leaf
		if(curr.left == null && curr.right == null){
			// case 1, node is a leaf 
			// edge case first, is this the root
			if(prev==null){
				root = null;
				return null;
			}
			if(curr == prev.left){
				prev.left = null;
			} else { // curr is prev.right
			    prev.right = null;
			}
			return root;
		}
		// case 2: node has one child
		TreeNode child = null;
		if(curr.left == null && curr.right != null){
			child = curr.right;
		} 
		if(curr.left != null && curr.right == null){
			child = curr.left;
		}
		if(child != null){
			// special corner case of root node
			if(prev == null){
				root = child;
				return root;
			}
			if(curr==prev.left){
				prev.left = child;
			} else {
				prev.right = child;
			}
		}
		// case 3: node has 2 children
		// find the successor, swap the value for the successor, delete the successor.
		if(curr.left!=null && curr.right!=null){
			var succ = curr.right;
			prev = curr;
			
			while(succ.left!=null){
				prev = succ;
				succ=succ.left;
			}
			
			curr.key = succ.key;
			if(succ==prev.left){
				prev.left = succ.right;
			} else { //succ is prev right
				prev.right = succ.right;
			}
				
		}
		return root;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BST bst = (BST) o;
        return Objects.equals(root, bst.root);
    }


    public BST(Builder builder) {
        root = builder.root;
    }

    public BST() {

    }


    public TreeNode getRoot() {
        return root;
    }
	


    public static class TreeNode {

        int key;
        TreeNode right;
        TreeNode left;

        public TreeNode(int key) {
            this.key = key;
        }



        public void setKey(int key) {
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TreeNode treeNode = (TreeNode) o;
            if (key != treeNode.key) return false;
            return Objects.equals(((TreeNode) o).right,this.right) &&
                   Objects.equals(((TreeNode) o).left,this.left) ;
        }


        public TreeNode setRight(TreeNode rightNode) {
            this.right = rightNode;
            return this;
        }

        @Override
        public String toString() {
            return toString(0);
        }
		public String toString(int level){
			return "[" + level + "]{" + 
                    "key=" + key +
                    ", left=" + toString(left,level+1) +
					", right=" + toString(right,level+1) +
                    '}';
		}
		public static String toString(TreeNode node,int level){
			if(node!=null){
				return node.toString(level);
			} else {
				return "-";
			}
		}

        public TreeNode addRight(int key) {
            var node = new TreeNode(key);
            this.setRight(node);
            return node;
        }

        public TreeNode addLeft(int key) {
            var node = new TreeNode(key);
            this.setLeft(node);
            return node;
        }

        private void setLeft(TreeNode node) {
            this.left = node;
        }
    }



    public static class Builder {
        TreeNode root;
        TreeNode curr;
        public Builder(){
        }
        public Builder withRootNode(int key) {
            root = new TreeNode(key);
            root.setKey(key);
            curr = root;
            return this;
        }

        public BST build() {
            return new BST(this);
        }

        public Builder withRightNode(int key) {
            var rightNode = new TreeNode(key);
            curr.setRight(rightNode);
            return this;
        }
    }
    public String toString(){
        return String.format("Root %s", root);
    }
}
