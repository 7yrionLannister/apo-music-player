package ui;

import java.io.File;
import java.io.IOException;

import javafx.animation.Animation.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.MusicPlayer;

public class PrimaryStageController {
	public final static Image DEFAULT_THUMBNAIL = new Image(new File("imgs/music-player.png").toURI().toString());
	public final static Image PAUSE_ICON = new Image(new File("imgs/pause.png").toURI().toString(), 50, 50, false, false);
	public final static Image PLAY_ICON = new Image(new File("imgs/play-button.png").toURI().toString(), 50, 50, false, false);

	private MusicPlayer musicPlayer;
	
	@FXML
	private Circle coverImageCircle;
	
	@FXML
	private ImageView songThumbnail;

	@FXML
	private Label songNameLabel;

	@FXML
	private Label songAlbumLabel;

	@FXML
	private Label songArtistLabel;

	@FXML
	private Button prevTrackButton;

	@FXML
	private Button playPauseButton;

	@FXML
	private Button nextTrackButton;

	@FXML
	private Label currentTimeLabel;

	@FXML
	private ProgressBar trackTimeProgressBar;

	@FXML
	private Slider trackTimeSlider;

	@FXML
	private Label durationLabel;

	@FXML
	private Button volumeSwitch;

	@FXML
	private ProgressBar volumeProgressBar;

	@FXML
	private Slider volumeSlider;

	@FXML
	private Button equalizerSwitchButton;

	@FXML
	private Button shuffleSwitchButton;

	@FXML
	private Button listOptionsButton;

	@FXML
	private Button settingsButton;

	@FXML
	private Button aboutButton;

	@FXML
	public void initialize() {
		//TODO complete this
		try {
			musicPlayer = new MusicPlayer(this);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				volumeProgressBar.setProgress(new_val.doubleValue()/100.0);
			}
		});
		trackTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				trackTimeProgressBar.setProgress(new_val.doubleValue()/100.0);
			}
		});
	}

	@FXML
	public void aboutButtonPressed(ActionEvent event) {

	}

	@FXML
	public void equalizerSwitchButtonPressed(ActionEvent event) {

	}

	@FXML
	public void listOptionsButtonPressed(ActionEvent event) {

	}

	@FXML
	public void nextTrackButtonPressed(ActionEvent event) {

	}

	@FXML
	public void playPauseButtonPressed(ActionEvent event) {
		if(musicPlayer.getMediaPlayer().getStatus().compareTo(MediaPlayer.Status.PLAYING) == 0) {
			musicPlayer.getMediaPlayer().pause();
			playPauseButton.setGraphic(new ImageView(PLAY_ICON));
		} else {
			musicPlayer.getMediaPlayer().play();
			playPauseButton.setGraphic(new ImageView(PAUSE_ICON));
		}
	}

	@FXML
	public void prevTrackButtonPressed(ActionEvent event) {

	}

	@FXML
	public void settingsButtonPressed(ActionEvent event) {

	}

	@FXML
	public void shuffleSwitchButtonPressed(ActionEvent event) {

	}

	@FXML
	public void volumeSwitchPressed(ActionEvent event) {

	}

	public Circle getCoverImageCircle() {
		return coverImageCircle;
	}

	public ImageView getSongThumbnail() {
		return songThumbnail;
	}

	public Label getSongNameLabel() {
		return songNameLabel;
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
}
