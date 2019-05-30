package model.paint;

public class Image {

	private int value;
	
	/**
	 * Constructor for Node class.
	 * post: a new instance of this class with it's correspondent value assigned can be created.
	 * @param value the value for the actual node created.
	 */
	public Image(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value + "";
	}
	
}
