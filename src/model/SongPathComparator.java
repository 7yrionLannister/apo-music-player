package model;

import java.io.File;
import java.util.Comparator;

public class SongPathComparator implements Comparator<Song>{

	@Override
	public int compare(Song o1, Song o2) {
		String [] paths = o1.getSongPath().split(File.separator);
		String [] paths2 = o2.getSongPath().split(File.separator);
		return paths[paths.length-1].compareTo(paths2[paths2.length-1]);
	}
	
}
