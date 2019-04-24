package ui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.WindowEvent;
import model.MusicFolder;
import model.MusicPlayer;
import model.Song;
import threads.CoverArtAnimationThread;

public class PrimaryStageController {
	public final static Image DEFAULT_THUMBNAIL = new Image(new File("imgs/music-player.png").toURI().toString());
	public final static Image PAUSE_ICON = new Image(new File("imgs/pause.png").toURI().toString(), 50, 50, false, false);
	public final static Image PLAY_ICON = new Image(new File("imgs/play-button.png").toURI().toString(), 50, 50, false, false);
	public final static Image MUTE_ENABLED_ICON = new Image(new File("imgs/mute.png").toURI().toString(), 40, 40, false, false);
	public final static Image MUTE_DISABLED_ICON = new Image(new File("imgs/volume-1.png").toURI().toString(), 40, 40, false, false);

	private MusicPlayer musicPlayer;
	private CoverArtAnimationThread caat;

	@FXML private Circle coverImageCircle;
	@FXML private ImageView songThumbnail;
	@FXML private Label songTitleLabel;
	@FXML private Label songAlbumLabel;
	@FXML private Label songArtistLabel;
	@FXML private Button prevTrackButton;
	@FXML private Button playPauseButton;
	@FXML private Button nextTrackButton;
	@FXML private Label currentTimeLabel;
	@FXML private ProgressBar trackTimeProgressBar;
	@FXML private Slider trackTimeSlider;
	@FXML private Label durationLabel;
	@FXML private Button volumeSwitchButton;
	@FXML private ProgressBar volumeProgressBar;
	@FXML private Slider volumeSlider;
	@FXML private Button equalizerSwitchButton;
	@FXML private Button shuffleSwitchButton;
	@FXML private Button addListButton;
	@FXML private Button settingsButton;
	@FXML private Button aboutButton;
	@FXML private TableView<MusicFolder> librariesTableView;
	@FXML private TableColumn<MusicFolder, String> libraryTableColumn;
	@FXML private TableColumn<MusicFolder, Integer> songsTableColumn;
	@FXML private Button deleteListButton;
	@FXML private TableView<Song> musicInfoTableView;
	@FXML private TableColumn<Song, String> genreTableColumn;
	@FXML private TableColumn<Song, String> titleTableColumn;
	@FXML private TableColumn<Song, String> albumTableColumn;
	@FXML private TableColumn<Song, String> artistTableColumn;
	@FXML private TableColumn<Song, Double> sizeTableColumn;

	@FXML
	public void initialize() {
		try {
			musicPlayer = new MusicPlayer(this);
			songTitleLabel.textProperty().bind(musicPlayer.getCurrentSongTitle());
			songAlbumLabel.textProperty().bind(musicPlayer.getCurrentSongAlbum());
			songArtistLabel.textProperty().bind(musicPlayer.getCurrentSongArtist());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				volumeProgressBar.setProgress(new_val.doubleValue()/100.0);
				musicPlayer.getMediaPlayer().setVolume(new_val.doubleValue() / 100.0);
			}
		});
		trackTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				trackTimeProgressBar.setProgress(new_val.doubleValue()/100.0);
				//TODO hacer que se pueda mover la reproduccion con el slider
			}
		});
		librariesTableView.setItems(musicPlayer.getMusicFolders());
		musicInfoTableView.setItems(FXCollections.observableArrayList(musicPlayer.getFirstMusicFolder().getSongs()));
		volumeSwitchButton.setUserData(false);

		libraryTableColumn.setCellValueFactory(new PropertyValueFactory<MusicFolder, String>("folderName"));
		songsTableColumn.setCellValueFactory(new PropertyValueFactory<MusicFolder, Integer>("numberOfSongs"));

		genreTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));
		titleTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
		albumTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
		artistTableColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
		sizeTableColumn.setCellValueFactory(new PropertyValueFactory<Song, Double>("size"));
		librariesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null) {
				musicInfoTableView.setItems(FXCollections.observableArrayList(newSelection.getSongs()));
			}
		});
		musicInfoTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null) {
				musicPlayer.setMedia(newSelection);
			}
		});

		refreshIcons();
	}

	@FXML
	public void aboutButtonPressed(ActionEvent event) {

	}

	@FXML
	public void equalizerSwitchButtonPressed(ActionEvent event) {

	}

	@FXML
	public void addListButtonPressed(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Choose a music directory");
		File directory = dc.showDialog(coverImageCircle.getParent().getScene().getWindow());
		try {
			musicPlayer.addMusicFolder(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		librariesTableView.setItems(musicPlayer.getMusicFolders());
	}

	@FXML
	public void nextTrackButtonPressed(ActionEvent event) {
		try {
			if(caat != null) {
				caat.pause();
			}
			ArrayList<Song> songs = musicPlayer.getCurrentSong().getContainer().getSongs();
			int currentIndex = musicPlayer.getCurrentSong().getIndexInContainer();
			int newIndex = currentIndex+1;
			musicPlayer.setMedia(songs.get(newIndex));
		} catch(IndexOutOfBoundsException aioobe) {
			if(caat != null) {
				caat.pause();
			}
			caat = new CoverArtAnimationThread(coverImageCircle);
			caat.setDaemon(true);
			caat.start();
		}
	}

	@FXML
	public void playPauseButtonPressed(ActionEvent event) {
		applyChangesToPlayPauseButton();
	}

	public void applyChangesToPlayPauseButton() {
		if(musicPlayer.getMediaPlayer().getStatus().compareTo(MediaPlayer.Status.PLAYING) == 0) {
			musicPlayer.getMediaPlayer().pause();
			playPauseButton.setGraphic(new ImageView(PLAY_ICON));
			caat.pause();
		} else {
			musicPlayer.getMediaPlayer().play();
			playPauseButton.setGraphic(new ImageView(PAUSE_ICON));
			caat = new CoverArtAnimationThread(coverImageCircle);
			caat.setDaemon(true);
			caat.start();
		}
	}

	@FXML
	public void prevTrackButtonPressed(ActionEvent event) {
		try {
			if(caat != null) {
				caat.pause();
			}
			ArrayList<Song> songs = musicPlayer.getCurrentSong().getContainer().getSongs();
			int currentIndex = musicPlayer.getCurrentSong().getIndexInContainer();
			int newIndex = currentIndex-1;
			musicPlayer.setMedia(songs.get(newIndex));
		} catch(IndexOutOfBoundsException aioobe) {
			if(caat != null) {
				caat.pause();
			}
			caat = new CoverArtAnimationThread(coverImageCircle);
			caat.setDaemon(true);
			caat.start();
		}
	}

	@FXML
	public void settingsButtonPressed(ActionEvent event) {

	}

	@FXML
	public void shuffleSwitchButtonPressed(ActionEvent event) {

	}

	@FXML
	public void volumeSwitchButtonPressed(ActionEvent event) {
		volumeSwitchButton.setUserData(!(boolean)volumeSwitchButton.getUserData());
		musicPlayer.getMediaPlayer().setMute((boolean)volumeSwitchButton.getUserData());
		if(musicPlayer.getMediaPlayer().isMute()) {
			volumeSwitchButton.setGraphic(new ImageView(MUTE_ENABLED_ICON));
		} else {
			volumeSwitchButton.setGraphic(new ImageView(MUTE_DISABLED_ICON));
		}
	}

	@FXML
	public void deleteListButtonPressed(ActionEvent event) {

	}

	public void save(WindowEvent event) {
		try {
			musicPlayer.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Circle getCoverImageCircle() {
		return coverImageCircle;
	}

	public ImageView getSongThumbnail() {
		return songThumbnail;
	}

	public Label getSongTitleLabel() {
		return songTitleLabel;
	}

	public Label getSongAlbumLabel() {
		return songAlbumLabel;
	}

	public Label getSongArtistLabel() {
		return songArtistLabel;
	}

	public Button getPrevTrackButton() {
		return prevTrackButton;
	}

	public Button getNextTrackButton() {
		return nextTrackButton;
	}

	public Label getCurrentTimeLabel() {
		return currentTimeLabel;
	}

	public Slider getTrackTimeSlider() {
		return trackTimeSlider;
	}

	public Label getDurationLabel() {
		return durationLabel;
	}

	public Slider getVolumeSlider() {
		return volumeSlider;
	}

	public Button getShuffleSwitchButton() {
		return shuffleSwitchButton;
	}

	public void refreshIcons() {
		songThumbnail.setImage(DEFAULT_THUMBNAIL);
		coverImageCircle.setFill(new ImagePattern(songThumbnail.getImage()));
		byte[] picture = musicPlayer.getCurrentCoverArt();
		if(picture != null && picture.length>0) {
			ByteArrayInputStream bais = new ByteArrayInputStream(picture);
			try {
				Image img = SwingFXUtils.toFXImage(ImageIO.read(bais), null);
				songThumbnail.setImage(img);
				coverImageCircle.setFill(new ImagePattern(songThumbnail.getImage()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
