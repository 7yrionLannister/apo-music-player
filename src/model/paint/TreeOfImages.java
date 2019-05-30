package model.paint;

import java.util.Random;

public class TreeOfImages implements Measurable {

	private ImageOnTree root;
	
	/**
	 * Basic constructor for Tree class.
	 * post: it allows to create a Tree object with it's root reference == null.
	 */
	public TreeOfImages() {
		
	}
	
	/**
	 * This method changes the root reference to the new node created with the value passed as 
	 * parameter in case that root == null, otherwise it just calls to the recursive method to 
	 * add the new node.
	 * post: the new node created with required value is going to be added to the tree.
	 * @param value the value needed to create the new node.
	 * @see #addNode(ImageOnTree, ImageOnTree)
	 */
	public void addNode(int value) {
		ImageOnTree n = new ImageOnTree(value);
		if(root == null) {
			root = n;
		} else {
			addNode(n, root);
		}
	}
	
	/**
	 * This method adds recursively a node passed as parameter into the tree.
	 * pre: current cannot be null.
	 * post: the new node is going to be added to the tree.
	 * @param n the new node.
	 * @param current the necessary node reference to apply recursion.
	 */
	public void addNode(ImageOnTree n, ImageOnTree current) {
		if(n.getValue() <= current.getValue()) {
			if(current.getLeft() == null) {
				current.setLeft(n);
			} else {
				addNode(n, current.getLeft());
			}
		} else {
			if(current.getRight() == null) {
				current.setRight(n);
			} else {
				addNode(n, current.getRight());
			}
		}
	}
	
	
	/**
	 * This method just calls the recursive one only if root != null and returns 
	 * a random node from the tree, otherwise it returns null.
	 * @return a partly-random chosen node or null.
	 */
	public ImageOnTree selectRandomNode() {
		int size = size();
		if(root == null) {
			return null;
		}
		return selectRandomNode(root, size);
	}
	
	/**
	 * This method returns a random node from the tree if and only if root != null.
	 * pre: root != null.
	 * post: a partly random node from the tree will be returned.
	 * @param current necessary parameter in order to apply recursion.
	 * @param size the size or weight of the tree which is passed as parameter to this 
	 * function mainly to avoid calling the size() method every time this method calls itself
	 * and making it more efficient.
	 * @return current the node which has been chosen by a random condition inside the method.
	 */
	private ImageOnTree selectRandomNode(ImageOnTree current, int size) {
		if(current != null) {
			if(current.getRight() != null || current.getLeft() != null) {
				if(1.0 / (Math.random()*(size+1)) <= 1.0/size) {
					return current;
				} else {
					Random r = new Random();
					if(r.nextBoolean()) {
						if(current.getLeft() != null) {
							return selectRandomNode(current.getLeft(), size);
						}
						return selectRandomNode(current.getRight(), size);
					} else {
						if(current.getRight() != null) {
							return selectRandomNode(current.getRight(), size);
						}
						return selectRandomNode(current.getLeft(), size);
					}
				}
			} else {
				return current;
			}
		}
		return null;
	}
	
	/**
	 * This method returns the size or also called weight of the tree. In case that root == null
	 * it returns 0, otherwise it calls the recursive method for size to calculate this value.
	 * post: the returned value is the number of nodes in the tree.
	 * @see #size(ImageOnTree)
	 */
	@Override
	public int size() {
		if(root == null) {
			return 0;
		}
		return size(root);
	}
	
	/**
	 * This recursive method calculates the weight of the tree.
	 * pre: current != null
	 * post: the returned value is the number of nodes in the tree.
	 * @param current the necessary parameter to make this function recursive.
	 * @return size the correspondent weight of the tree.
	 */
	private int size(ImageOnTree current) {
		int size = 1;
		if(current.getLeft() != null) {
			size += size(current.getLeft());
		}
		if(current.getRight() != null) {
			size += size(current.getRight());
		}
		return size;
	}
	
	/**
	 * Getter method for root attribute.
	 * @return root the reference for the root node of the tree.
	 */
	public ImageOnTree getRoot() {
		return root;
	}
	
}
