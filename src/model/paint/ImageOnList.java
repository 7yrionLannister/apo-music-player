package model.paint;

public class ImageOnList extends Image {

	private ImageOnList next;
	private ImageOnList previous;
	private boolean selected;
	
	/**
	 * Constructor for ImageOnList class.
	 * post: a new instance of this class with it's correspondent value assigned 
	 * and the next and previous references == null can be created.
	 * @param value the value for the actual node created.
	 */
	public ImageOnList(int value) {
		super(value);
	}
	
	//Getters and setters
	
	public ImageOnList getNext() {
		return next;
	}
	
	public void setNext(ImageOnList next) {
		this.next = next;
	}
	
	public ImageOnList getPrevious() {
		return previous;
	}
	
	public void setPrevious(ImageOnList prev) {
		this.previous = prev;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}