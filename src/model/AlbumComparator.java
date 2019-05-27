package model;

import java.util.Comparator;

public class AlbumComparator implements Comparator<Song>{
	
	/**This method compares two song albums lexicographically.
	 * @param s1 The Song that will compare its album with the Song s2 album lexicographically.
	 * @param s2 The Song that will be compared with Song s1 album lexicographically.
	 * @return An integer that represents if the Song s1 album is greater, less or equal to Song s2 album lexicographically.  
	 */
	@Override
	public int compare(Song s1, Song s2) {
		return s1.getAlbum().compareTo(s2.getAlbum());
	}
	
}
