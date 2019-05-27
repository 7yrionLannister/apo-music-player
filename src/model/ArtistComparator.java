package model;

import java.util.Comparator;


public class ArtistComparator implements Comparator<Song>{
	
	/**
	 * This method compares two song artists lexicographically.
	 * @param s1 The Song that will compare its artist with the Song s2 artist lexicographically.
	 * @param s2 The Song that will be compared with Song s1 artist lexicographically.
	 * @return An integer that represents if the Song s1 artist is greater, less or equal to Song s2 artist lexicographically.  
	 */
	@Override
	public int compare(Song s1, Song s2) {
		return s1.getArtist().compareTo(s2.getArtist());
	}

}
