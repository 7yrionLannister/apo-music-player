package model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.beaglebuddy.id3.enums.PictureType;
import com.beaglebuddy.id3.pojo.AttachedPicture;
import com.beaglebuddy.mp3.MP3;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import ui.PrimaryStageController;

public class Song {
	private String title;
	private String artist;
	private String album;
	private double size;
	private Image coverArt;
	private Media song;

	public Song(File song) throws IOException {
		this.song = new Media(song.toURI().toString());
		MP3 mp3 = new MP3(song);
		coverArt = PrimaryStageController.DEFAULT_THUMBNAIL;
		AttachedPicture cover = mp3.getPicture(PictureType.FRONT_COVER);
		if(cover != null) {
			byte[] data = cover.getImage();
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			coverArt = SwingFXUtils.toFXImage(ImageIO.read(bais), null);
		} 
		album = mp3.getAlbum();
		artist = mp3.getBand()!=null?mp3.getBand():mp3.getLeadPerformer();
		title = mp3.getTitle();
		size = mp3.getAudioSize() / 1000000.0;
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

	public Image getCoverArt() {
		return coverArt;
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
