package model.paint;

public class ListOfImages implements Measurable {

	private ImageOnList first;

	/**
	 * Basic constructor for ListOfImages class. post: a new instance of List with a
	 * ListNode reference as attribute first equals to null can be created.
	 */
		
	public ListOfImages() {

	}

	/**
	 * This method adds a new node, which value is passed as parameter, to the
	 * doubly linked list. If the first reference == null, the new node is going to
	 * be the first, otherwise, the node is going to be added to the end of the list
	 * by calling another method. post: the new node is going to be added to the
	 * list.
	 * 
	 * @param value is the integer which the new node will be created with.
	 * @see #addNode(ListNode, ListNode)
	 */
	public void addNode(int value) {
		ImageOnList n = new ImageOnList(value);
		if (first == null) {
			first = n;
		} else {
			addNode(n, first);
		}
	}

	/**
	 * This method adds a new node to the doubly linked list in a recursive way.
	 * pre: n != null && current != null. post: the node is going to be added at the
	 * end of the list.
	 * 
	 * @param n       the node to be added.
	 * @param current the needed parameter to make this method recursive.
	 */
	private void addNode(ImageOnList n, ImageOnList current) {
		if(current.getNext() != null) {
			addNode(n, current.getNext());
		} else {
			current.setNext(n);
			n.setPrevious(current);
		}
	}

	/**
	 * @return first the reference for the first item from the doubly linked list of Images.
	 */
	public ImageOnList getFirst() {
		return first;
	}

	/**
	 * Setter method for <first> attribute.
	 * @param first the object of the type ImageOnList to replace the list's first object reference.
	 */
	public void setFirst(ImageOnList first) {
		this.first = first;
	}

	
	/**
	 * This method counts every object in the list and returns that value.
	 * post: the returned value is going to be the total number of nodes in the list.
	 * @return size the size of the list.
	 */
	@Override
	public int size() {
		ImageOnList current = first;
		int size = 0;
		while (current != null) {
			size++;
			current = current.getNext();
		}
		return size;
	}

	/**
	 * This method sets the <selected> attribute from the last selected object in the list to false, 
	 * and sets its next's to true. When none of the nodes in the list has been selected in the list 
	 * ever before, the selected item will be the first node of the list. 
	 * pre: there must be just 0 or 1 items selected in the list, otherwise the selection function is 
	 * going to go wrong.
	 * post: one and only one item in the list will be selected.
	 * @see #lastSelected()
	 */
	public void selectNext() {
		ImageOnList last = lastSelected();
		if (last != null) {
			last.setSelected(false);
			if (last.getNext() != null) {
				last.getNext().setSelected(true);
			} else {
				first.setSelected(true);
			}
		} else {
			first.setSelected(true);
		}
	}

	/**
	 * This method sets the <selected> attribute from the last selected object in the list to false, 
	 * and sets its previous's to true. When none of the nodes in the list has been selected in the list 
	 * ever before, the selected item will be the first node of the list. 
	 * pre: there must be just 0 or 1 items selected in the list, otherwise the selection function is 
	 * going to go wrong.
	 * post: one and only one item in the list will be selected.
	 * @see #lastSelected()
	 */
	public void selectPrevious() {
		ImageOnList last = lastSelected();
		if (last != null) {
			last.setSelected(false);
			if (last.getPrevious() != null) {
				last.getPrevious().setSelected(true);
			} else {
				getLastNode().setSelected(true);
			}
		} else {
			first.setSelected(true);
		}
	}
	
	/**
	 * If the first reference != null, this method returns the last object from the list, otherwise it returns null.
	 * @return last object from a not-empty list or null.
	 */
	public ImageOnList getLastNode() {
		if(first != null) {
			ImageOnList ln = first;
			while(ln.getNext() != null) {
				ln = ln.getNext();
			}
			return ln;
		} 
		return null;
	}		
	
	/**
	 * If the first reference != null, this method returns the last object from the list, otherwise it returns null.
	 * @return selected which can be null only when the list of images is empty, otherwise it's going to be the last 
	 * selected image.
	 */
	public ImageOnList lastSelected() {
		ImageOnList selected = null; //the last node selected
		ImageOnList current = first; //the iterator
		while(current != null && selected == null) { //it iterates over the list till the last selected node is found
			if(current.isSelected()) { // verifies if the actual node was the last selected
				selected = current; // updates the selected node reference
			}
			current = current.getNext(); // advances over the list
		}
		return selected;// returns null if none of the nodes was selected, otherwise it returns the last
						// selected
	}

}
