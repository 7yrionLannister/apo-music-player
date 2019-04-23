package model;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MusicFolder implements Serializable {
	private File folder;
	private MusicFolder nextMusicFolder;
	private String folderName;
	private int numberOfSongs;
	private ArrayList<Song> songs;
	
	public MusicFolder(File folder) throws IOException {
		this.folder = folder;
		File[] content = folder.listFiles();
		songs = new ArrayList<Song>();
		for(int i = 0; i < content.length; i++) {
			if(content[i].isFile() && content[i].getPath().endsWith(".mp3")) {
				songs.add(new Song(content[i]));
			}
		}
		folderName = folder.getName();
		numberOfSongs = songs.size();
	}
	
	public MusicFolder getNextMusicFolder() {
		return nextMusicFolder;
	}
	
	public void setNextMusicFolder(MusicFolder next) {
		nextMusicFolder = next;
	}
	
	public File getFolder() {
		return folder;
	}
	
	public String getFolderName() {
		return folderName;
	}
	
	public int getNumberOfSongs() {
		return numberOfSongs;
	}
	
	public boolean equals(MusicFolder another) {
		boolean equal = false;
		if(folder.getPath().equals(another.getFolder().getPath())) {
			equal = true;
		}
		return equal;
	}
	
	public void sortSongsByTitle() {
		//TODO implement this (Collections.sort(natural order))
	}
	
	public void sortSongsByArtist() {
		//TODO implement this (selection sort)
	}
	
	public void sortSongsByAlbum() {
		//TODO implement this (bubble sort)
	}
	
	public void sortSongsBySize() {
		//TODO implement this (insertion sort)
	}
	
	public void sortSongsByGenre() {
		//TODO implement this (Collections.sort(non-natural order))
	}
	
	public ArrayList<Song> getSongs(){
		return songs;
	}
}
