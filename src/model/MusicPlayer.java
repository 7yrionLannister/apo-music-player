package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import ui.PrimaryStageController;

public class MusicPlayer {
	public final static String MUSIC_FOLDERS_PATH = "data/mscfldrs.got";
	
	private Media media;
	private MediaPlayer mediaPlayer;
	private MusicFolder firstMusicFolder;
	private PrimaryStageController psc;

	public MusicPlayer(PrimaryStageController psc) throws ClassNotFoundException, IOException {
		this.psc = psc;
		firstMusicFolder = new MusicFolder(new File("samples").toURI());
		
		File file = new File(MUSIC_FOLDERS_PATH);
		if(file.exists()) {
			loadMusicFolders(file);
		}
		chargeMedia();
	}

	public void chargeMedia() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				psc.getSongAlbumLabel().setText("unknown");
				psc.getSongArtistLabel().setText("unknown");
				psc.getSongNameLabel().setText("unknown");
				psc.getSongThumbnail().setImage(PrimaryStageController.DEFAULT_THUMBNAIL);
				psc.getCoverImageCircle().setFill(new ImagePattern(PrimaryStageController.DEFAULT_THUMBNAIL));
			}
		});
		//TODO update the interface according to the current song (labels and images)
	}
	
	private void loadMusicFolders(File mf) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(mf);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		firstMusicFolder = (MusicFolder)ois.readObject();
		
		ois.close();
		fis.close();
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

}
