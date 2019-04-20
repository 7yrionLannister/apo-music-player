package model;

import java.io.IOException;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class Song {
	private String title;
	private String artist;
	private String album;
	private Image coverArt;
	private Media song;

	public Song(Media song) {
		this.song = song;
		ObservableMap<String, Object> metadata = this.song.getMetadata();metadata.get("artist");
		metadata.addListener(new MapChangeListener<String, Object>() {
			@Override
			public void onChanged(Change<? extends String, ? extends Object> ch) {
				if (ch.wasAdded()) {
					try {
						handleMetadata(ch.getKey(), ch.getValueAdded());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void handleMetadata(String key, Object value) throws IOException {
		if (key.equals("album")) {
			album = value.toString();
		} if (key.equals("artist")) {
			artist = value.toString();
		} if (key.equals("title")) {
			title = value.toString();
		}
		if (key.equals("image")) {
			coverArt = (Image)value;
		}
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
}
