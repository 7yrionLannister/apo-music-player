package model.paint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ImageOnListTest {
	private ImageOnList imageOnList;

	public void setupStage1() {
		imageOnList = new ImageOnList(10);
	}

	public void setupStage2() {
		imageOnList.setNext(new ImageOnList(30));
		imageOnList.getNext().setPrevious(imageOnList);
		imageOnList.getNext().setNext(new ImageOnList(4));
		imageOnList.getNext().getNext().setPrevious(imageOnList.getNext());
		imageOnList.getNext().getNext().setNext(new ImageOnList(14));
		imageOnList.getNext().getNext().getNext().setPrevious(imageOnList.getNext().getNext());
	}

	@Test
	public void testGetNext() {
		setupStage1();
		setupStage2();
		ImageOnList second = new ImageOnList(30);
		ImageOnList third = new ImageOnList(4);
		ImageOnList fourth = new ImageOnList(14);
		assertEquals(imageOnList.getNext().getValue(), second.getValue(),
				"The following object of the first is different from the one assigned as second");
		assertEquals(imageOnList.getNext().getNext().getValue(), third.getValue(),
				"The following object of the second is different from the one assigned as third");
		assertEquals(imageOnList.getNext().getNext().getNext().getValue(), fourth.getValue(),
				"The following object of the third is different from the one assigned as fourth");
		assertNull(imageOnList.getNext().getNext().getNext().getNext(),
				"The following object of the fourth element must be null, since it is a non-circular list");
	}

	@Test
	public void testSetNext() {
		setupStage1();
		ImageOnList object = new ImageOnList(3);
		imageOnList.setNext(object);
		assertEquals(imageOnList.getNext(), object,
				"The next object of the first is different from the one that you just added as the second");
	}

	@Test
	public void testGetPrevious() {
		setupStage1();
		setupStage2();
		ImageOnList second = new ImageOnList(30);
		ImageOnList third = new ImageOnList(4);
		assertNull(imageOnList.getPrevious(),
				"The previous one of the first item in the list must be null since it is a non-circular list");
		assertEquals(imageOnList.getNext().getPrevious().getValue(), imageOnList.getValue(),
				"The previous object of the second is different from the one assigned as first element");
		assertEquals(imageOnList.getNext().getNext().getPrevious().getValue(), second.getValue(),
				"The following object of the second is different from the one assigned as third");
		assertEquals(imageOnList.getNext().getNext().getNext().getPrevious().getValue(), third.getValue(),
				"The following object of the third is different from the one assigned as fourth");
	}

	@Test
	public void testSetPrevious() {
		setupStage1();
		setupStage2();
		ImageOnList object = new ImageOnList(40);
		imageOnList.getNext().getNext().setPrevious(object);
		assertNotNull(imageOnList.getNext().getNext().getPrevious(), "The object can not be null because it has just been assigned");
		assertEquals(object, imageOnList.getNext().getNext().getPrevious(), "The previous one of the object is different from the one that was just assigned as previous");	
	}

	@Test
	void testIsSelected() {
		setupStage1();
		setupStage2();
		imageOnList.getNext().isSelected();
		assertEquals(imageOnList.isSelected(), false, "The image can not be selected, because its status has not changed at any time");
		assertEquals(imageOnList.getNext().isSelected(), false, "The image can not be selected, because its status has not changed at any time");
		assertEquals(imageOnList.getNext().getNext().isSelected(), false, "The image can not be selected, because its status has not changed at any time");
		assertEquals(imageOnList.getNext().getNext().getNext().isSelected(), false, "The image can not be selected, because its status has not changed at any time");
	}

	@Test
	public void testSetSelected() {
		setupStage1();
		setupStage2();
		imageOnList.setSelected(true);
		imageOnList.getNext().getNext().setSelected(true);
		assertEquals(imageOnList.isSelected(), true, "The image has to be selected, because its status has just been changed to true");
		assertEquals(imageOnList.getNext().getNext().isSelected(), true, "The image has to be selected, because its status has just been changed to true");
		assertEquals(imageOnList.getNext().isSelected(), false, "The image can not be selected, because its status has not changed at any time");
		assertEquals(imageOnList.getNext().getNext().getNext().isSelected(), false, "The image can not be selected, because its status has not changed at any time");
	}
}