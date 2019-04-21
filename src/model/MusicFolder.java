package model;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class MusicFolder implements Serializable {
	private File folder;
	private MusicFolder nextMusicFolder;
	private ArrayList<Song> songs;
	
	public MusicFolder(File folder) {
		this.folder = folder;
		//TODO read the audio files in the folder ---> arraylist of songs
	}
	
	public MusicFolder getNextMusicFolder() {
		return nextMusicFolder;
	}
	
	public void setNextMusicFolder(MusicFolder next) {
		nextMusicFolder = next;
	}
	
	public boolean equals(MusicFolder another) {
		//TODO implement this
		return false;
	}
}
