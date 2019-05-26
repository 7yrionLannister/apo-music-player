package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import com.beaglebuddy.id3.enums.PictureType;
import com.beaglebuddy.mp3.MP3;

import customExceptions.NotMP3FileException;

public class Song implements Serializable, Comparable<Song>{
	private String title;
	private String artist;
	private String album;
	private String genre;
	private double size;
	private String songPath;
	private String fileName;
	private String parentFolderPath;
	private byte[] image;
	
	private Song right;
	private Song left;
	
	/**
	 * Song constructor method that receives a mp3 file as parameter and starts the entire song metadata. 
	 * @param song A mp3 file that represents the song to instance this class.
	 * @throws IOException
	 * @throws NotMP3FileException
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
	
	/**
	 * Method that allows to get the song title.
	 * @return A string that represents the song title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Method that allows to get the song artist.
	 * @return A string that represents the song artist.
	 */
	public String getArtist() {
		return artist;
	}
	/**
	 * Method that allows to get the song title.
	 * @return A string that represents the song title.
	 */
	public String getAlbum() {
		return album;
	}
	/**
	 * Method that allows to get the song image as a byte array.
	 * @return A byte array that represents the song image.
	 */
	public byte[] getImage() {
		return image;
	}
	/**
	 * Method that allows to get the song genre.
	 * @return A string that represents the song genre.
	 */
	public String getGenre() {
		return genre;
	}
	
	public void setCoverArt() {
		//TODO complete this
	}
	/**
	 * Method that allows to get the song path.
	 * @return A string that represents the song path.
	 */
	public String getSongPath() {
		return songPath;
	}
	/** 
	 * Method that allows to get the song size as a double type number. 
	 * @return A double that represents the song size.
	 */
	public double getSize() {
		return size;
	}
	/**
	 * Method that allows to get the song file name.
	 * @return A string that represents the song file name.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * Method that allows to set the song title name.
	 * @param title A string that represents the song title name.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public void setSongPath(String songPath) {
		this.songPath = songPath;
	}

	public void setParentFolderPath(String parentFolderPath) {
		this.parentFolderPath = parentFolderPath;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getParentFolderPath() {
		return parentFolderPath;
	}

	public Song getRight() {
		return right;
	}

	public void setRight(Song right) {
		this.right = right;
	}

	public Song getLeft() {
		return left;
	}

	public void setLeft(Song left) {
		this.left = left;
	}

	@Override
	public int compareTo(Song s) {
		return fileName.compareTo(s.fileName);
	}
}
