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
	
	/** This is the folder that will be represented by this class
	 */
	private File folder;
	/** This is the name of the folder being represented
	 */
	private String folderName;
	/**This is the number of MP3 files that are contained in this folder
	 */
	private int numberOfSongs;
	/**This is the song that charges all the other songs in the binary search tree
	 */
	private Song root;
	/** This is where all the songs in the binary search tree will be contained to perform the sorting algorithms
	 */
	private ArrayList<Song> songs;
	/** This is the next folder in the folders linked list
	 */
	private MusicFolder nextMusicFolder;
	/**This is the previous folder in the folders linked list
	 */
	private MusicFolder prevMusicFolder;
	
	/**The method allows to get an instance of MusicFolder that will represent the folder received as parameter.
	 * @param folder The folder to be represented by this MusicFolder.
	 * @throws IOException if the folder doesn't exist or there was another problem reading it.
	 * @throws FolderWithoutMP3ContentException if the folder doesn't contain any MP3 files in it.
	 */
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
	
	/** The method allows to add a new Song to the binary search tree according to the natural order.
	 * @param current The current node in the binary search tree that is responsible to add the requested 
	 * node before or after itself depending on the natural order. 
	 * @param addme The node that is going to be added to the binary search tree.
	 */
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
	
	/** The method allows to obtain a list with the songs in the binary search tree in order. 
	 * @return A Song ArrayList with the songs in order.
	 */
	public ArrayList<Song> inorder() {
		ArrayList<Song> inorderSongs = new ArrayList<Song>();
		if(root != null) {
			inorder(root, inorderSongs);
		}
		return inorderSongs;
	}
	
	/** The method allows to fill an ArrayList with the songs in the binary search tree in order.
	 * @param current The current node in the binary search tree that is going to be added to the ArrayList after its left 
	 * subtree and before its right subtree.
	 * @param tofill the ArrayList where the songs are going to accumulate.
	 */
	private void inorder(Song current, ArrayList<Song> tofill) {
		if(current.getLeft() != null) {
			inorder(current.getLeft(), tofill);
		}
		tofill.add(current);
		if(current.getRight() != null) {
			inorder(current.getRight(), tofill);
		}
	}
	
	/** The method allows to obtain the next music folder in the linked list.
	 * @return A MusicFolder that represents the next music folder in the linked list.
	 */
	public MusicFolder getNextMusicFolder() {
		return nextMusicFolder;
	}
	
	/**The method allows to change the next music folder in the linked list
	 * @param next A Music folder that represents the next music folder in the linked list.
	 */
	public void setNextMusicFolder(MusicFolder next) {
		nextMusicFolder = next;
	}
	
	/** The method allows to obtain the previous music folder in the linked list.
	 * @return A MusicFolder that represents the previous music folder in the linked list.
	 */
	public MusicFolder getPrevMusicFolder() {
		return prevMusicFolder;
	}
	
	/**The method allows to change the previous music folder in the linked list.
	 * @param prevMusicFolder A MusicFolder that represents the previous music folder in the linked list.
	 */
	public void setPrevMusicFolder(MusicFolder prevMusicFolder) {
		this.prevMusicFolder = prevMusicFolder;
	}
	
	/**The method allows to obtain the folder that this MusicFolder represents.
	 * @return A File that shows the folder that this MusicFolder represents.
	 */
	public File getFolder() {
		return folder;
	}
	
	/**The method allows to obtain the name of the folder that this MusicFolder represents.
	 * @return A String that shows the name of the folder that this MusicFolder represents.
	 */
	public String getFolderName() {
		return folderName;
	}
	
	/**The method allows to obtain the number of songs inside the music folder.
	 * @return An integer that represents the number of songs inside the music folder.
	 * */
	public int getNumberOfSongs() {
		return numberOfSongs;
	}
	
	/**The method allows to know whether this MusicFolder is equal to another one.
	 * @param another The music folder to make the comparison.
	 */
	public boolean equals(MusicFolder another) {
		boolean equal = false;
		if(folder.getPath().equals(another.getFolder().getPath())) {
			equal = true;
		}
		return equal;
	}
	
	/** The method allows to sort the songs in the playList according to its title.
	 * It uses Collections.sort.
	 */
	public void sortSongsByTitle() {
		Collections.sort(songs, new TitleComparator());
	}
	
	/**The method allows to sort the songs in the playList according to its artist.
	 * It uses bubble sort.
	 */
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
	
	/**The method allows to sort the songs in the playList according to its album.
	 * It uses insertion sort.
	 */
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
	
	/**The method allows to sort the songs in the playList according to its size.
	 * It uses selection sort.
	 */
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
	
	/**The method allows to sort the songs in the playList according to its genre.
	 * It uses Collections.sort.
	 */
	public void sortSongsByGenre() {
		Collections.sort(songs, new GenreComparator());
	}
	
	/**The method allows to obtain a Song ArrayList that represents all the songs inside the music folder.
	 * @return A Song ArrayList that represents all the songs inside the music folder.
	 */
	public ArrayList<Song> getSongs() {
		return songs;
	}
}
