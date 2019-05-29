package model.paint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ListOfImagesTest {
	private ListOfImages list;
	
	private void setupStage() {
	
	}
	
	private void setupStage1() {
		list = new ListOfImages();
	}

	private void setupStage2() {
		setupStage1();
		list.addNode(0);
		list.addNode(3);
		list.addNode(6);
		list.addNode(2);
		list.addNode(5);
		list.addNode(4);
	}

	@Test
	public void selectNextTest() {
		setupStage2();
		list.selectNext();
		list.selectNext();
		assertTrue(list.getFirst().getNext().isSelected(), "The wrong object or none was selected");
	}

	@Test
	void testListOfImages() {
		setupStage();
		ListOfImages list = new ListOfImages();
		assertNotNull(list, "The object can not be null, since it has just been instantiated");
		assertTrue(list.getFirst() == null, "The list is not empty");
	}

	@Test
	void testAddNode() {
		setupStage2();
		assertNotNull(list.getFirst(),
				"The first element must not be null since the first element has just been added to the list");
		assertNotNull(list.getFirst().getNext(),
				"The second element must not be null since the second element has just been added to the list");
		assertNotNull(list.getFirst().getNext().getNext(),
				"The third element must not be null since the third element has just been added to the list");
		assertNotNull(list.getLastNode().getPrevious().getPrevious(),
				"The fourth element must not be null since the fourth element has just been added to the list");
		assertNotNull(list.getLastNode().getPrevious(),
				"The fifth element must not be null since the fifth element has just been added to the list");
		assertNotNull(list.getLastNode(),
				"The last element must not be null since the last element has just been added to the list");

		assertEquals(list.getFirst().getValue(), 0, "The first element added must be 0");
		assertEquals(list.getFirst().getNext().getValue(), 3, "The second element added must be 3");
		assertEquals(list.getFirst().getNext().getNext().getValue(), 6, "The third element added must be 6");
		assertEquals(list.getFirst().getNext().getNext().getNext().getValue(), 2, "The fourth element added must be 2");
		assertEquals(list.getFirst().getNext().getNext().getNext().getNext().getValue(), 5,
				"The fifth element added must be 5");
		assertEquals(list.getLastNode().getValue(), 4, "The last element added must be 4");
	}

	@Test
	void testGetFirst() {
		setupStage2();
		assertTrue(list.getFirst().getValue() == 0, "The attribute first hasn't been initialized correctly");
	}

	@Test
	void testSetFirst() {
		setupStage2();
		ImageOnList temp = new ImageOnList(21);
		list.setFirst(temp);
		assertEquals(temp, list.getFirst(),
				"The first object should have a value of " + temp.getValue() + ", not " + list.getFirst());
		assertNotEquals(list.getFirst().getValue(), 0, "The first object was not replaced");
		assertNotNull(list.getFirst(), "The first object must not be null since it has just been replaced by another");
	}

	@Test
	void testSize() {
		setupStage2();
		assertEquals(6, list.size());
	}

	@Test
	void testSelectPrevious() {
		setupStage2();
		list.selectPrevious();
		list.selectPrevious();
		assertTrue(list.getLastNode().isSelected(), "The wrong object or none was selected");
	}

	@Test
	void testGetLastNode() {
		setupStage2();
		assertTrue(list.getLastNode().getValue() == 4, "The last object of the list doesn't coincide with the correct one");
	}

	@Test
	void testLastSelected() {
		setupStage2();
		list.selectNext();
		list.selectNext();
		list.selectNext();
		assertTrue(list.lastSelected().getValue() == 6, "The last selected object doesn't coincide with the correct one");
	}
}