package model.paint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ImageOnTreeTest {
	private ImageOnTree root;
	private ImageOnTree left1;
	private ImageOnTree left11;
	private ImageOnTree right2;
	private ImageOnTree right12;
	private ImageOnTree right22;

	private void setupStage1() {
		root = new ImageOnTree(50);
	}

	private void setupStage2() {
		left1 = new ImageOnTree(30);
		left11 = new ImageOnTree(20);
		right12 = new ImageOnTree(40);
		right2 = new ImageOnTree(60);
		right22 = new ImageOnTree(70);

		root.setLeft(left1);
		root.setRight(right2);
		root.getLeft().setLeft(left11);
		root.getLeft().setRight(right12);
		root.getRight().setRight(right22);
	}

	@Test
	void testImageOnTree() {
		setupStage1();
		assertNotNull(root, "The object must not be null because it has just been instantiated");
	}

	@Test
	void testGetLeft() {
		setupStage1();
		setupStage2();

		assertEquals(root.getLeft(), left1, "The object to the left of the root is different from the one assigned");
		assertEquals(root.getLeft().getLeft(), left11,
				"The object to the left of second left is different from the one assigned");
		assertNull(root.getRight().getLeft(),
				"The object to the left of the one on the right of the first must be null, since no value has been assigned to it");
		assertNull(root.getLeft().getLeft().getLeft(),
				"The object to the left of the one on the left of the first must be null, since no value has been assigned to it");
	}

	@Test
	void testSetLeft() {
		setupStage1();
		setupStage2();

		ImageOnTree tmp = new ImageOnTree(35);
		ImageOnTree tmp2 = new ImageOnTree(55);

		root.getLeft().setLeft(null);
		assertNull(root.getLeft().getLeft(), "The object must be null, since it has been assigned null.");

		root.setLeft(tmp);
		assertEquals(tmp, root.getLeft(),
				"The object to the left of the root is different from the one previously assigned");
		assertNotEquals(root.getLeft(), left1,
				"The object to the left of the root was not changed, it remains the same");

		root.getRight().setLeft(tmp2);
		assertNotNull(root.getRight().getLeft(), "The object must not be null, because an object was placed there");
		assertEquals(tmp2, root.getRight().getLeft(), "The object was not added or is different from the assigned");
	}

	@Test
	void testGetRight() {
		setupStage1();
		setupStage2();

		assertEquals(root.getRight(), right2, "The object to the right of the root is different from the one assigned");
		assertEquals(root.getRight().getRight(), right22,
				"The object to the right of the one on the right of the root one different from the one assigned");
		assertNull(root.getRight().getRight().getRight(),
				"The object must be null, since it has not been instantiated");
		assertEquals(root.getLeft().getRight(), right12,
				"The object to the right of the one on the left of the root is different from the one assigned");
	}

	@Test
	void testSetRight() {
		setupStage1();
		setupStage2();

		root.getRight().setRight(null);
		assertNull(root.getRight().getRight(), "The object must be null, since it has been assigned null.");
		ImageOnTree tmp = new ImageOnTree(55);
		ImageOnTree tmp2 = new ImageOnTree(45);

		root.setRight(tmp);
		assertEquals(tmp, root.getRight(),
				"The object to the right of the root is different from the one previously assigned");
		assertNotEquals(root.getRight(), right2,
				"The object to the right of the root was not changed, it remains the same");

		root.getLeft().setRight(tmp2);
		assertNotNull(root.getLeft().getRight(), "The object must not be null, because an object was placed there");
		assertEquals(tmp2, root.getLeft().getRight(), "The object was not added or is different from the assigned");
		assertNotEquals(right12, root.getLeft().getRight(),
				"The object to the right of the one on the left of the root was not changed, it remains the same");
	}
}