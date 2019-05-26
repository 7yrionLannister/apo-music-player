package model;

import java.util.Comparator;

public class TitleComparator implements Comparator<Song>{
	
	/** This method compares two song titles lexicographically.
	 * @param s1 The Song that will compare its title with the Song s2 title lexicographically.
	 * @param s2 The Song that will be compared with Song s1 title lexicographically.
	 * @return An integer that represents if the Song s1 title is greater, less or equal to Song s2 title lexicographically.  
	 */
	@Override
	public int compare(Song o1, Song o2) {
		return o1.getTitle().compareTo(o2.getTitle());
	}
	
}
