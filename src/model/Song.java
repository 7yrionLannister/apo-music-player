package model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.beaglebuddy.id3.enums.PictureType;
import com.beaglebuddy.mp3.MP3;

public class Song implements Serializable {
	private String title;
	private String artist;
	private String album;
	private String genre;
	private double size;
	private String songPath;
	private byte[] image;
	
	private MusicFolder container;

	public Song(File song, MusicFolder container) throws IOException {
		this.songPath = song.toURI().toString();
		this.container = container;
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
	
	public MusicFolder getContainer() {
		return container;
	}
	
	public int getIndexInContainer() {
		return container.getSongs().indexOf(this);
	}
	
	public int getPlayListSize() {
		return container.getSongs().size();
	}
}
