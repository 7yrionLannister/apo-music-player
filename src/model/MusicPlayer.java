package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import customExceptions.AttemptedToRemoveCurrentPlayListException;
import customExceptions.AttemptedToRemoveDemoLibraryException;
import customExceptions.FolderWithoutMP3ContentException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
	public final static String MUSIC_FOLDERS_PATH = "resources"+File.separator+"mscfldrs.got";

	private Media currentAudio;
	public Media getCurrentAudio() {
		return currentAudio;
	}

	private MediaPlayer mediaPlayer;
	private MusicFolder firstMusicFolder;
	private SimpleStringProperty currentSongTitle;
	private SimpleStringProperty currentSongArtist;
	private SimpleStringProperty currentSongAlbum;
	private ArrayList<Song> currentPlaylist;
	private SimpleIntegerProperty songLoaded;
	private byte[] currentCoverArt;

	private Song currentSong;
	
	/**
	 * Constructor MusicPlayer method that starts the entire current song metadata and the media player. 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FolderWithoutMP3ContentException
	 */
	public MusicPlayer() throws ClassNotFoundException, IOException, FolderWithoutMP3ContentException {
		songLoaded = new SimpleIntegerProperty(Integer.MIN_VALUE);

		currentSongAlbum = new SimpleStringProperty();
		currentSongArtist = new SimpleStringProperty();
		currentSongTitle = new SimpleStringProperty();

		firstMusicFolder = new MusicFolder(new File("resources"));
		currentPlaylist = firstMusicFolder.getSongs();
		currentSong = currentPlaylist.get(0);

		File file = new File(MUSIC_FOLDERS_PATH);
		if(file.exists()) {
			loadMusicFolders(file);
		}
		chargeMedia();
	}
	/**
	 * This method charges the entire metadata to the Media and the Media to the MediaPlayer.
	 */
	private void chargeMedia() {
		if(mediaPlayer != null) {
			mediaPlayer.stop();
		}
		currentAudio = new Media(currentSong.getSongPath());
		mediaPlayer = new MediaPlayer(currentAudio);
		mediaPlayer.stop();

		currentCoverArt = currentSong.getImage();

		currentSongAlbum.setValue(currentSong.getAlbum());
		currentSongArtist.setValue(currentSong.getArtist());
		currentSongTitle.setValue(currentSong.getTitle());

		songLoaded.set(songLoaded.get()+1);
	}
	/**
	 * Method that deserializes the folder with music when the application is started again.
	 * @param mf A File that represents the folder with music to deserialize when the application is started again.  
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
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

	public void setMedia(int index) {
		this.currentSong = currentPlaylist.get(index);
		mediaPlayer.stop();
		chargeMedia();
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

	public void addMusicFolder(File dir) throws IOException, FolderWithoutMP3ContentException {
		if(dir != null) {
			MusicFolder toAdd = new MusicFolder(dir);
			MusicFolder current = firstMusicFolder;
			boolean duplicated = false;
			while(current != null && !duplicated) {
				if(current.equals(toAdd)) {
					duplicated = true;
				} else if(current.getNextMusicFolder() == null) {
					current.setNextMusicFolder(toAdd);
					toAdd.setPrevMusicFolder(current);
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

	public SimpleIntegerProperty getSongLoaded() {
		return songLoaded;
	}

	public void setCurrentPlayList(MusicFolder current) {
		currentPlaylist = current.getSongs();
	}

	public ArrayList<Song> getCurrentPlayList() {
		return currentPlaylist;
	}

	public void removeMusicFolderFromLibraries(MusicFolder toremove) throws AttemptedToRemoveDemoLibraryException {
		if(toremove == firstMusicFolder) {
			throw new AttemptedToRemoveDemoLibraryException();
		}
		if(toremove.getSongs().contains(currentSong)) {
			throw new AttemptedToRemoveCurrentPlayListException(currentSong.getParentFolderPath());
		}

		MusicFolder prev = toremove.getPrevMusicFolder();
		MusicFolder next = toremove.getNextMusicFolder();
		if(prev != null) {
			prev.setNextMusicFolder(next);
		} else {
			firstMusicFolder = next;
		}
		if(next != null) {
			next.setPrevMusicFolder(prev);
		}
	}
}
