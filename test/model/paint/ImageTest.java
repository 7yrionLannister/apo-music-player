package model.paint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ImageTest {
	private Image image;

	private void setupStage1() {
		image = new Image(3);
	}

	@Test
	public void testGetValue() {
		setupStage1();
		assertEquals(image.getValue(), 3, "The value should be 3, not: " + image.getValue());
	}

	@Test
	public void testSetValue() {
		setupStage1();
		image.setValue(50);
		assertEquals(image.getValue(), 50,
				"The first element should not be " + image.getValue() + ", since the element was just changed by 50");
	}

	@Test
	public void testToString() {
		setupStage1();
		String value = "3";
		assertEquals(image.toString(), value, "The value should be 3, not: " + image.toString());
	}
}