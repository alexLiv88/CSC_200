/*
 * The HBT class is the Huffman Binary Tree class. This class implements the Tree class found within the text. 
 * 
 * An HBT can be created in one of two ways: 
 * 		as a leaf that contains only a root node, or
 * 		as a combination of two HBT's that will become the left and right branches of the new tree's root.
 * 
 * This HBT class only contains one method for traversing the tree which traverses the tree in-order and 
 * creates the table output of each of the characters values and weights as a series of console output
 * strings during this traversal
 */
import java.util.Iterator;

public class HBT<E extends Comparable<E>> implements Tree<E>{
	protected TreeNode root;
	protected int size = 0;
	
	/* Creates a huffman binary tree from a single element */
	public HBT (char c, int w) {
		this.root = new TreeNode(c, w);
		size++;
	}
	
	/* Creates a huffman binary tree by combining two HBT's */
	public HBT (HBT one, HBT two) {
		// weight of the new tree is the combined weight of the two trees
		int w = one.getRootWeight() + two.getRootWeight();
		
		// new root node is empty of char
		this.root = new TreeNode(' ', w);
		
		// set left and right of new root to old trees,
		// if a root has an empty char value it goes to the left
		if(one.getRoot().getChar() == ' ') {
			HBT hold = one;
			one = two;
			two = hold;
		}
		// if both roots are not spaces the smaller weight is one
		if ((one.getRoot().getChar() != ' ' && two.getRoot().getChar() != ' ') && one.getRootWeight() < two.getRootWeight()) {
			HBT hold = one;
			one = two;
			two = hold;
		}
		this.root.setRight(one.getRoot());
		this.root.setLeft(two.getRoot());
	}
	@Override
	public void clear() {
		root = new TreeNode(' ', 0);
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override /** Get the number of nodes in the tree */
	public int getSize() {
	  return size;
	}

	/** Returns the root of the tree */
	public TreeNode getRoot() {
	  return root;
	}
	
	public int getRootWeight() {
		return this.root.getWeight();
	}

	@Override
	public boolean search(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(E e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String toString() {
		return getRoot().toString();
	}


	@Override /** Inorder traversal from the root */
	public void inorder() {
		System.out.format("%10s%16s%16s%16s", "ASCII Code", "Character", "Frequency", "Code");
		inorder(root, "");
	}

	/** Inorder traversal from a subtree */
	protected void inorder(TreeNode root, String val) {
	    if (root == null) return;
	    inorder(root.left, val+"0");
	    if(root.element != ' ') {System.out.println(""); System.out.format("%10d%16c%16s%16s", (int)root.element, root.element, root.getWeight(), val );}
	    inorder(root.right, val+"1");
	}


}
