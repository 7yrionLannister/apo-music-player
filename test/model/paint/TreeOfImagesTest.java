package model.paint;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TreeOfImagesTest {

	private TreeOfImages tree;
	
	private void setupScenary1() {
		
	}
	
	private void setupScenary2() {
		tree = new TreeOfImages();
	}
	
	private void setupScenary3() {
		this.tree = new TreeOfImages();
		tree.addNode(5);
		tree.addNode(2);
		tree.addNode(4);
		tree.addNode(1);
		tree.addNode(7);
		tree.addNode(6);
		tree.addNode(8);
	}
	
	@Test
	public void testTreeOfImages() {
		setupScenary1();
		this.tree = new TreeOfImages();
		assertTrue(tree.getRoot() == null, "The first attribute should be null.");
	}

	@Test
	public void testAddNodeInt() {
		setupScenary3();
		int value = 10;
		tree.addNode(value);
		assertTrue(search(tree, 10), "The ImageOnTree instance with value == 10 was not added.");
	}

	@Test
	public void testAddNodeImageOnTreeImageOnTree() {
		setupScenary3();
		ImageOnTree n = new ImageOnTree(9);
		tree.addNode(n, tree.getRoot());
		assertTrue(search(tree, 9), "The ImageOnTree instance with value == 9 was not added.");
	}

	@Test
	public void testSelectRandomNode() {
		setupScenary3();
		ImageOnTree iot = tree.selectRandomNode();
		assertTrue(search(tree, iot.getValue()), "The random node selected doesn't belong to the tree.");
	}
	@Test
	public void testSelectRandomNode2() {
		setupScenary3();
		int[] values = new int[2000];
		for (int i = 0; i < 2000; i++) {
			values[i] = tree.selectRandomNode().getValue();
		}
		boolean approximatelyRandom = approximatelyRandom(values);
		assertTrue(approximatelyRandom, "The method isn't approximately random");
	}

	@Test
	public void testSize() {
		setupScenary3();
		assertTrue(tree.size() == 7, "The size method doesn't return the actual weight of the tree.");
	}
	
	@Test
	public void testSize2() {
		setupScenary2();
		assertTrue(tree.size() == 0, "The size method does not return 0 when the tree is empty");
	}

	@Test
	public void testGetRoot2() {
		setupScenary3();
		assertTrue(tree.getRoot().getValue() == 5, "The root reference hasn't been correctly assigned.");
	}
	
	@Test
	public void testGetRoot1() {
		setupScenary2();
		assertNull(tree.getRoot(), "The root reference should be null");
	}
	
	public boolean search(TreeOfImages toi, int key) {
		return search(toi.getRoot(), key);
	}
	
	public boolean search(ImageOnTree current, int key) {
		if(current != null) {
			if(current.getValue() == key) {
				return true;
			} else {
				if(key <= current.getValue()) {
					return search(current.getLeft(), key);
				} else {
					return search(current.getRight(), key);
				}
			}
		}
		return false;
	}
	//this method verifies that extreme cases don't happen when the random selection from the tree is used 
	//a bunch of times. The method for "random selection" could return non-uniform pseudo-random results, 
	//but anyways the extreme cases evaluated with this method should not happen.
	public boolean approximatelyRandom(int[] values) {
		ArrayList<Integer> differentValues = new ArrayList<Integer>();
		for (int i = 0; i < values.length; i++) {
			if(!differentValues.contains(values[i])) {
				differentValues.add(values[i]);
			}
		}
		int[] frequencies = new int[differentValues.size()];
		for (int i = 0; i < differentValues.size(); i++) {
			int sum = 0;
			for (int j = 0; j < values.length; j++) {
				if(differentValues.get(i).equals(values[j])) {
					sum++;
				}
			}
			frequencies[i] = sum;
		}
		for (int i = 0; i < frequencies.length; i++) {
			System.out.print(frequencies[i] + " ");
		}
		for (int i = 0; i < frequencies.length; i++) {
			if(frequencies[i] == 0 || frequencies[i] == values.length) 
				return false;
		}
		return true;
	}
	
}
