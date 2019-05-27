package model;

import java.util.Comparator;


public class GenreComparator implements Comparator<Song>{
	
	/**
	 * This method compares two song genres lexicographically.
	 * @param s1 The Song that will compare its genre with the Song s2 genre lexicographically.
	 * @param s2 The Song that will be compared with Song s1 genre lexicographically.
	 * @return An integer that represents if the Song s1 genre is greater, less or equal to Song s2 genre lexicographically.  
	 */
	@Override
	public int compare(Song o1, Song o2) {
		return o1.getGenre().compareTo(o2.getGenre());
	}
	
}
