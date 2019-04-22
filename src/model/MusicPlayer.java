package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.beaglebuddy.id3.enums.PictureType;
import com.beaglebuddy.id3.pojo.AttachedPicture;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import ui.PrimaryStageController;

public class MusicPlayer {
	public final static String MUSIC_FOLDERS_PATH = "resources/mscfldrs.got";

	private MediaPlayer mediaPlayer;
	private MusicFolder firstMusicFolder;
	private PrimaryStageController psc;
	private SimpleStringProperty currentSongTitle;
	private SimpleStringProperty currentSongArtist;
	private SimpleStringProperty currentSongAlbum;
	private byte[] currentCoverArt; 

	private Song currentSong;
	
	public MusicPlayer(PrimaryStageController psc) throws ClassNotFoundException, IOException {
		this.psc = psc;

		currentSongAlbum = new SimpleStringProperty();
		currentSongArtist = new SimpleStringProperty();
		currentSongTitle = new SimpleStringProperty();
		
		firstMusicFolder = new MusicFolder(new File("resources"));

		File file = new File(MUSIC_FOLDERS_PATH);
		if(file.exists()) {
			loadMusicFolders(file);
		}
		currentSong = new Song(new File("resources/Spectre.mp3"));
		chargeMedia();
	}

	public void chargeMedia() {
		if(mediaPlayer != null) {
			mediaPlayer.stop();
		}
		mediaPlayer = new MediaPlayer(currentSong.getSong());
		mediaPlayer.stop();

		currentCoverArt = currentSong.getImage();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				psc.refreshIcons();
			}
		});
		
		currentSongAlbum.setValue(currentSong.getAlbum());
		currentSongArtist.setValue(currentSong.getArtist());
		currentSongTitle.setValue(currentSong.getTitle());
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

	public byte[] getCurrentCoverArt() {
		return currentCoverArt;
	}
	
	public void addMusicFolder(File dir) throws IOException {
		if(dir != null) {
			MusicFolder toAdd = new MusicFolder(dir);
			MusicFolder current = firstMusicFolder;
			boolean done = false;
			while(current != null && !done) {
				if(current.equals(toAdd)) {
					done = true;
				} else if(current.getNextMusicFolder() == null) {
					current.setNextMusicFolder(toAdd);
				}
				current = current.getNextMusicFolder();
			}
		}
	}
	
	public ObservableList<MusicFolder> getMusicFolders() {
		ObservableList<MusicFolder> folders = FXCollections.observableArrayList();
		MusicFolder current = firstMusicFolder;
		while(current != null) {
			folders.add(current);
			current = current.getNextMusicFolder();
		}
		return folders;
	}
	
	public void save() throws IOException {
		File file = new File(MUSIC_FOLDERS_PATH);
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(firstMusicFolder);
		
		oos.close();
		fos.close();
	}
}
