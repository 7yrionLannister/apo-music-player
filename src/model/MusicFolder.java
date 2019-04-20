package model;
import java.net.URI;
import java.util.ArrayList;

public class MusicFolder {
	private URI folder;
	private MusicFolder nextMusicFolder;
	private ArrayList<Song> songs;
	
	public MusicFolder(URI folder) {
		this.folder = folder;
		//TODO read the audio files in the folder ---> arraylist of songs
	}
}
