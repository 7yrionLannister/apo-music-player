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
	 * Song constructor method that receives a mp3 file as parameter. 
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

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbum() {
		return album;
	}

	public byte[] getImage() {
		return image;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setCoverArt() {
		//TODO complete this
	}

	public String getSongPath() {
		return songPath;
	}
	
	public double getSize() {
		return size;
	}
	
	public String getFileName() {
		return fileName;
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
