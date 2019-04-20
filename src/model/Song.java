package model;

import java.io.File;
import java.io.IOException;

import com.beaglebuddy.mp3.MP3;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import ui.PrimaryStageController;

public class Song {
	private SimpleStringProperty title;
	private SimpleStringProperty artist;
	private SimpleStringProperty album;
	private ObjectProperty<Image> coverArt;
	private Media song;

	public Song(File song) throws IOException {
		this.song = new Media(song.toURI().toString());
		MP3 mp3 = new MP3(song);
		coverArt = new SimpleObjectProperty<Image>(PrimaryStageController.DEFAULT_THUMBNAIL);
		album = new SimpleStringProperty(mp3.getAlbum());
		artist = new SimpleStringProperty(mp3.getBand()!=null?mp3.getBand():mp3.getLeadPerformer());
		title = new SimpleStringProperty(mp3.getTitle());
	}

	public SimpleStringProperty getTitle() {
		return title;
	}

	public SimpleStringProperty getArtist() {
		return artist;
	}

	public SimpleStringProperty getAlbum() {
		return album;
	}

	public ObjectProperty<Image> getCoverArt() {
		return coverArt;
	}
	
	public void setCoverArt() {
		//TODO complete this
	}

	public Media getSong() {
		return song;
	}
}
