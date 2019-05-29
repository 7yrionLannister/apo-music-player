package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import com.beaglebuddy.id3.enums.PictureType;
import com.beaglebuddy.mp3.MP3;

import customExceptions.NotMP3FileException;


public class Song implements Serializable, Comparable<Song>{
	/**It represents the song title.
	 */
	private String title;
	/** It represents the song artist.
	 */
	private String artist;
	/** It represents the song album.
	 */
	private String album;
	/**It represents the song genre.
	 */
	private String genre;
	/**It represents the song size as double value.
	 */
	private double size;
	/**It represents the song path as String.
	 */
	private String songPath;
	/**It represents the song file name.
	 */
	private String fileName;
	/**It represents the song parent folder path as String.
	 */
	private String parentFolderPath;
	/**It represents the song image as a byte array.
	 */
	private byte[] image;
	/**It represents the right Song of this Song in the binary search three.
	 */
	private Song right;
	/**It represents the left Song of this Song in the binary search three.
	 */
	private Song left;
	
	/**Song constructor method that receives a mp3 file as parameter and starts the entire song metadata. 
	 * @param song A mp3 file that represents the song to instance this class.
	 * @throws IOException if the file has not been found, deleted or moved to another location. 
	 * @throws NotMP3FileException if the file delivered as parameter is not of mp3 type. 
	 */
	public Song(File song) throws IOException, NotMP3FileException {
		String path = song.toURI().toString();
		if(!song.exists()) {
			throw new FileNotFoundException();
		}
		parentFolderPath = song.getParentFile().toURI().toString();
		fileName = song.getName();
		if(!path.endsWith(".mp3")) {
			String[] parts = path.split("[.]");
			throw new NotMP3FileException(parts[parts.length-1]);
		}
		this.songPath = song.toURI().toString();
		MP3 mp3 = new MP3(song);
		
		album = mp3.getAlbum()!=null?mp3.getAlbum():"unknown";
		artist = mp3.getBand()!=null?mp3.getBand():mp3.getLeadPerformer();
		title = mp3.getTitle()!=null?mp3.getTitle():"unknown";
		size = mp3.getAudioSize() / 1000000.0;
		genre = mp3.getMusicType()!=null?mp3.getMusicType():"unknown";
		image = mp3.getPicture(PictureType.FRONT_COVER)!=null?mp3.getPicture(PictureType.FRONT_COVER).getImage():null;
	}
	
	/** Method that allows to get the song title.
	 * @return A string that represents the song title.
	 */
	public String getTitle() {
		return title;
	}
	
	/**Method that allows to get the song artist.
	 * @return A string that represents the song artist.
	 */
	public String getArtist() {
		return artist;
	}
	
	/**Method that allows to get the song title.
	 * @return A string that represents the song title.
	 */
	public String getAlbum() {
		return album;
	}
	
	/**Method that allows to get the song image as a byte array.
	 * @return A byte array that represents the song image.
	 */
	public byte[] getImage() {
		return image;
	}
	
	/**Method that allows to get the song genre.
	 * @return A string that represents the song genre.
	 */
	public String getGenre() {
		return genre;
	}
	
	/** This method allows to set the song cover art specifying a image file as parameter.
	 * @param imageFile A File that represents the song cover art.
	 */
	public void setCoverArt(File imageFile) {
		try {
			MP3 mp3 = new MP3(songPath);
			mp3.setPicture(PictureType.FRONT_COVER, imageFile);
			image = mp3.getPicture(PictureType.FRONT_COVER).getImage();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**Method that allows to get the song path.
	 * @return A string that represents the song path.
	 */
	public String getSongPath() {
		return songPath;
	}
	
	/** Method that allows to get the song size as a double type number. 
	 * @return A double that represents the song size.
	 */
	public double getSize() {
		return size;
	}
	
	/**Method that allows to get the song file name.
	 * @return A string that represents the song file name.
	 */
	public String getFileName() {
		return fileName;
	}
	
	/** Method that allows to get the song parent folder path.
	 * @return A string that represents the song parent folder path.
	 */
	public String getParentFolderPath() {
		return parentFolderPath;
	}
	
	/**Method that allows to get the right Song associated with this Song in the binary search three.
	 * @return A Song that represents the right Song associated with this Song in the binary search three.
	 */
	public Song getRight() {
		return right;
	}
	
	/**
	 * Method that allows to set the right Song associated with this Song in the binary search three.
	 * @param right A Song that represents the right Song associated with this Song in the binary search three.
	 */
	public void setRight(Song right) {
		this.right = right;
	}
	
	/**Method that allows to get the left Song associated with this Song in the binary search three.
	 * @return A Song that represents the left Song associated with this Song in the binary search three.
	 */
	public Song getLeft() {
		return left;
	}
	
	/** Method that allows to set the left Song associated with this Song in the binary search three.
	 * @param left A Song that represents the left Song associated with this Song in the binary search three.
	 */
	public void setLeft(Song left) {
		this.left = left;
	}
	
	/**This method compares two song title lexicographically as natural order.
	 * @param s A song that represents the song that will be compared with this song lexicographically.
	 * @return An integer that represents if this title is greater, less or equal to s title lexicographically.
	 */
	@Override
	public int compareTo(Song s) {
		return title.compareTo(s.title);
	}
}
