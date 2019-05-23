package model;

import java.util.Comparator;

public class SizeComparator implements Comparator <Song>{

	@Override
	public int compare(Song o1, Song o2) {
		return (int) (o1.getSize() - o2.getSize());
	}
	
}
