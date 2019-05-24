package model;

import java.util.Comparator;

public class GenreComparator implements Comparator<Song>{

	@Override
	public int compare(Song o1, Song o2) {
		return o1.getGenre().compareTo(o2.getGenre());
	}
	
}
