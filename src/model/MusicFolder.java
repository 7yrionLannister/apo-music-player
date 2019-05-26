package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import customExceptions.FolderWithoutMP3ContentException;
import customExceptions.NotMP3FileException;

public class MusicFolder implements Serializable {
	private File folder;
	private String folderName;
	private int numberOfSongs;

	private Song root;
	private ArrayList<Song> songs;

	private MusicFolder nextMusicFolder;
	private MusicFolder prevMusicFolder;

	public MusicFolder(File folder) throws IOException, FolderWithoutMP3ContentException {
		this.folder = folder;
		if(!folder.exists()) {
			throw new FileNotFoundException();
		}
		File[] content = folder.listFiles();
		songs = new ArrayList<Song>();
		if(content.length != 0) {
			int index = 0;
			boolean added = false;
			while(!added) {
				try {
					root = new Song(content[index]);
					added = true;
				} catch(NotMP3FileException nmp3fe) {
					index++;
					if(index == content.length) {
						throw new FolderWithoutMP3ContentException(folder.getPath());
					}
				}
			}
		}
		for(int i = 1; i < content.length; i++) {
			if(content[i].isFile() && content[i].getPath().endsWith(".mp3")) {
				addSongToBST(root, new Song(content[i]));
			}
		}
		songs = inorder();
		folderName = folder.getName();
		numberOfSongs = songs.size();
	}

	private void addSongToBST(Song current, Song addme) {
		if(current.compareTo(addme) > 0) {
			if(current.getLeft() != null) {
				addSongToBST(current.getLeft(), addme);
			} else {
				current.setLeft(addme);
			}
		} else {
			if(current.getRight() != null) {
				addSongToBST(current.getRight(), addme);
			} else {
				current.setRight(addme);
			}
		}
	}

	public ArrayList<Song> inorder() {
		ArrayList<Song> inorderSongs = new ArrayList<Song>();
		if(root != null) {
			inorder(root, inorderSongs);
		}
		return inorderSongs;
	}

	private void inorder(Song current, ArrayList<Song> tofill) {
		if(current.getLeft() != null) {
			inorder(current.getLeft(), tofill);
		}
		tofill.add(current);
		if(current.getRight() != null) {
			inorder(current.getRight(), tofill);
		}
	}

	public MusicFolder getNextMusicFolder() {
		return nextMusicFolder;
	}

	public void setNextMusicFolder(MusicFolder next) {
		nextMusicFolder = next;
	}

	public MusicFolder getPrevMusicFolder() {
		return prevMusicFolder;
	}

	public void setPrevMusicFolder(MusicFolder prevMusicFolder) {
		this.prevMusicFolder = prevMusicFolder;
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
		Collections.sort(songs);
	}

	//it uses bubble sort
	public void sortSongsByArtist() {
		ArtistComparator ac = new ArtistComparator();
		for(int i = 0; i < songs.size(); i++) {
			for(int j = 0; j < songs.size()-1-i; j++) {
				if(ac.compare(songs.get(j), songs.get(j+1)) > 0) {
					Song temp = songs.get(j);
					songs.set(j, songs.get(j+1));
					songs.set(j+1, temp);
				}
			}
		}
	}

	//it uses insertion sort
	public void sortSongsByAlbum() {
		AlbumComparator ac = new AlbumComparator();
		for(int i = 1; i < songs.size(); i++) {
			Song current = songs.get(i);
			int j = i-1;
			while(j >= 0 && ac.compare(songs.get(j), current) > 0) {
				songs.set(j+1, songs.get(j));
				j--;
			}
			songs.set(j+1, current);
		}
	}

	//it uses selection sort
	public void sortSongsBySize() {
		SizeComparator sc = new SizeComparator();
		for(int i = 0; i < songs.size()-1; i++) {
			int low = i;
			for(int j = i+1; j < songs.size(); j++) {
				if(sc.compare(songs.get(low), songs.get(j)) > 0) {
					low = j;
				}
			}
			Song temp = songs.get(low);
			songs.set(low, songs.get(i));
			songs.set(i, temp);
		}
	}

	public void sortSongsByGenre() {
		Collections.sort(songs, new GenreComparator());
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}
}
