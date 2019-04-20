package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import ui.PrimaryStageController;

public class MusicPlayer {
	public final static String MUSIC_FOLDERS_PATH = "data/mscfldrs.got";

	private MediaPlayer mediaPlayer;
	private MusicFolder firstMusicFolder;
	private PrimaryStageController psc;
	private SimpleStringProperty currentSongTitle;
	private SimpleStringProperty currentSongArtist;
	private SimpleStringProperty currentSongAlbum;
	private ObjectProperty<Image> currentCoverArt; 

	//TODO delete this line
	private Song currentSong;
	
	public MusicPlayer(PrimaryStageController psc) throws ClassNotFoundException, IOException {
		this.psc = psc;

		currentSongAlbum = new SimpleStringProperty();
		currentSongArtist = new SimpleStringProperty();
		currentSongTitle = new SimpleStringProperty();
		currentCoverArt = new SimpleObjectProperty<Image>();
		
		firstMusicFolder = new MusicFolder(new File("samples").toURI());

		File file = new File(MUSIC_FOLDERS_PATH);
		if(file.exists()) {
			loadMusicFolders(file);
		}
		//TODO the next song is a test, delete it an uncomment bensound-summer.mp3
		currentSong = new Song(new File(/*"samples/bensound-summer.mp3"*/"E:/Spectre.mp3"));
		chargeMedia();
	}

	public void chargeMedia() {
		if(mediaPlayer != null) {
			mediaPlayer.stop();
		}
		mediaPlayer = new MediaPlayer(currentSong.getSong());
		mediaPlayer.stop();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				psc.getSongThumbnail().setImage(currentSong.getCoverArt().get());
				psc.getCoverImageCircle().setFill(new ImagePattern(currentSong.getCoverArt().get()));
			}
		});
		
		currentSongAlbum.setValue(currentSong.getAlbum().getValue());
		currentSongArtist.setValue(currentSong.getArtist().getValue());
		currentSongTitle.setValue(currentSong.getTitle().getValue());
		currentCoverArt.setValue(currentSong.getCoverArt().getValue());
	}

	private void loadMusicFolders(File mf) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(mf);
		ObjectInputStream ois = new ObjectInputStream(fis);

		firstMusicFolder = (MusicFolder)ois.readObject();

		ois.close();
		fis.close();
	}

	public Song getCurrentSong() {
		return currentSong;
	}

	public void setMedia(Song media) {
		this.currentSong = media;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	public MusicFolder getFirstMusicFolder() {
		return firstMusicFolder;
	}

	public void setFirstMusicFolder(MusicFolder firstMusicFolder) {
		this.firstMusicFolder = firstMusicFolder;
	}

	public SimpleStringProperty getCurrentSongTitle() {
		return currentSongTitle;
	}

	public SimpleStringProperty getCurrentSongArtist() {
		return currentSongArtist;
	}

	public SimpleStringProperty getCurrentSongAlbum() {
		return currentSongAlbum;
	}

	public ObjectProperty<Image> getCurrentCoverArt() {
		return currentCoverArt;
	}
}
