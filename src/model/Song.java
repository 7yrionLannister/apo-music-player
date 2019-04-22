package model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.beaglebuddy.mp3.MP3;

import javafx.scene.media.Media;

public class Song implements Serializable {
	private String title;
	private String artist;
	private String album;
	private String genre;
	private double size;
	private Media song;
	private byte[] image;

	public Song(File song) throws IOException {
		this.song = new Media(song.toURI().toString());
		MP3 mp3 = new MP3(song);
		
		album = mp3.getAlbum()!=null?mp3.getAlbum():"unknown";
		artist = mp3.getBand()!=null?mp3.getBand():mp3.getLeadPerformer();
		title = mp3.getTitle()!=null?mp3.getTitle():"unknown";
		size = mp3.getAudioSize() / 1000000.0;
		genre = mp3.getMusicType()!=null?mp3.getMusicType():"unknown";
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

	public Media getSong() {
		return song;
	}
	
	public double getSize() {
		return size;
	}
}
