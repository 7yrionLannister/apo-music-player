package model.paint;

public class ImageOnTree extends Image {

	private ImageOnTree left;
	private ImageOnTree right;
	
	/**
	 * Constructor for Node class.
	 * @param val this integer is set to the value attribute.
	 */
	public ImageOnTree(int val) {
		super(val);
	}
	
	//Getters and setters for left, right and value attributes:
	
	public ImageOnTree getLeft() {
		return left;
	}

	public void setLeft(ImageOnTree left) {
		this.left = left;
	}

	public ImageOnTree getRight() {
		return right;
	}

	public void setRight(ImageOnTree right) {
		this.right = right;
	}
	
}
