package model.musicPlayer;

import java.util.Comparator;


public class SizeComparator implements Comparator <Song>{
	
	/**This method compares two song sizes casting them to integers
	 * @param s1 The Song that will compare its size with the Song s2 size<br>s1 != null
	 * @param s2 The Song that will be compared with Song s1 size<br>s2 != null
	 * @return An integer that represents if the Song s1 size is greater, less or equal to Song s2 size.  
	 */
	@Override
	public int compare(Song s1, Song s2) {
		int comp = 0;
		if(s1.getSize() > s2.getSize()) {
			comp = 1;
		} else if(s1.getSize() < s2.getSize()) {
			comp = -1;
		}
		return comp;
	}
	
}
