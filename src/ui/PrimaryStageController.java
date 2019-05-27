package ui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import customExceptions.AttemptedToRemoveCurrentPlayListException;
import customExceptions.AttemptedToRemoveDemoLibraryException;
import customExceptions.FolderWithoutMP3ContentException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.MusicFolder;
import model.MusicPlayer;
import model.Song;
import threads.CoverArtAnimationThread;
import threads.CurrentTrackTimeUpdaterThread;


public class PrimaryStageController {
	
	/** It represents the default cover art image in the left corner when the current song metadata does not have this property. 
	 */
	public final static Image DEFAULT_THUMBNAIL = new Image(new File("imgs"+File.separator+"music-player.png").toURI().toString());
	
	/** It represents the pause icon when the current song is paused.
	 */
	public final static Image PAUSE_ICON = new Image(new File("imgs"+File.separator+"pause.png").toURI().toString(), 50, 50, false, false);
	
	/** It represents the play icon when the current song is being played.
	 */
	public final static Image PLAY_ICON = new Image(new File("imgs"+File.separator+"play-button.png").toURI().toString(), 50, 50, false, false);
	
	/** It represents the mute enabled icon when the current song has been muted.
	 */
	public final static Image MUTE_ENABLED_ICON = new Image(new File("imgs"+File.separator+"mute.png").toURI().toString(), 40, 40, false, false);
	
	/** It represents the mute disabled icon when the current song has not been muted.
	 */
	public final static Image MUTE_DISABLED_ICON = new Image(new File("imgs"+File.separator+"volume-1.png").toURI().toString(), 40, 40, false, false);
	
	public final static Image SHUFFLE_ACTIVED = new Image(new File("imgs"+File.separator+"volume-1.png").toURI().toString(), 40, 40, false, false);
	/** It represents the MusicPlayer that will manage all the mp3 files.
	 */
	private MusicPlayer musicPlayer;
	
	/** It represents the thread in charge of moving the cover art in circle form.
	 */
	private CoverArtAnimationThread caat;

	@FXML private Circle backgroundCircle;
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
	
	/** This method starts and setups all the necessary components inside the interface and bind them with the model when is started.
	 */
	@FXML
	public void initialize() {
		try {
			musicPlayer = new MusicPlayer();
			musicPlayer.getSongLoaded().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> ov, 
						Number old_val, Number new_val) {
					applyChangesToPlayPauseButton();
					refreshIcons();
				}
			});
			songTitleLabel.textProperty().bind(musicPlayer.getCurrentSongTitle());
			songAlbumLabel.textProperty().bind(musicPlayer.getCurrentSongAlbum());
			songArtistLabel.textProperty().bind(musicPlayer.getCurrentSongArtist());
		} catch (FolderWithoutMP3ContentException | ClassNotFoundException | IOException e) {
			e.printStackTrace();;
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
				musicPlayer.setCurrentPlayList(newSelection);
			}
		});
		musicInfoTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null && !musicPlayer.getCurrentSongTitle().get().equals(newSelection.getTitle())) {
				int cI = musicPlayer.getCurrentPlayList().indexOf(newSelection);
				musicPlayer.setMedia(cI);
				restartThreads();
			}
		});
		CurrentTrackTimeUpdaterThread cttu = new CurrentTrackTimeUpdaterThread(this, false);
		cttu.setDaemon(true);
		cttu.start();
		refreshIcons();
	}
	
	/** This method shows an emergent window with info about this application.
	 * @param event An ActionEvent that represents the event when the associated about button is pressed.
	 */
	@FXML
	public void aboutButtonPressed(ActionEvent event) {
		Stage about = new Stage();
		Pane pane = new Pane();
		Label l = new Label();
		Label l2 = new Label();
		Label l3 = new Label();
		Label l4 = new Label();
		l.setText("Apo Music Player is a music player presented as a final ");
		l2.setText("project of the algorithms and programming 2 course.");
		l3.setText("It was meant to apply the knowledge learned in the first");
		l4.setText("two algorithm courses. Thanks for reading.");
		Image java = new Image(new File("imgs/java.png").toURI().toString());
		ImageView jav = new ImageView();
		jav.setImage(java);
		l2.relocate(0, 15);
		l3.relocate(0, 30);
		l4.relocate(0, 45);
		jav.relocate(63, 67);
		pane.getChildren().addAll(l, l2, l3, l4, jav);
		Scene scene = new Scene(pane, 286, 100);
		about.setTitle("About Apo Music Player");
		about.setResizable(false);
		about.setScene(scene);
		about.getIcons().add(new Image(new File("imgs"+File.separator+"cd.png").toURI().toString()));
		about.show();
	}
	
	/** This method allows to add a new music library inside the application invoking a DirectoryChooser.
	 * @param event An ActionEvent that represents the event when the associated add list button is pressed.
	 */
	@FXML
	public void addListButtonPressed(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Choose a music directory");
		File directory = dc.showDialog(coverImageCircle.getParent().getScene().getWindow());
		try {
			musicPlayer.addMusicFolder(directory);
		} catch (IOException e) {
			showErrorAlert("Error loading the library", "The music folder could not be loaded");
		} catch (FolderWithoutMP3ContentException e) {
			showErrorAlert("No MP3 files found", "The chosen directory does not contain MP3 files to add to a new library");
		}
		librariesTableView.setItems(musicPlayer.getMusicFolders());
	}
	
	/** This method play the next track in the music folder when this exist.
	 * @param event An ActionEvent that represents the event when the associated next track button is pressed.
	 */
	@FXML
	public void nextTrackButtonPressed(ActionEvent event) {
		try {
			if(caat != null) {
				caat.pause();
			}
			int cI = musicPlayer.getCurrentPlayList().indexOf(musicPlayer.getCurrentSong());
			int nI = cI+1;
			musicPlayer.setMedia(nI);
		} catch(IndexOutOfBoundsException aioobe) {
			restartThreads();
		}
	}
	
	/** This method calls the method that modifies the media player and cover art animation state.
	 * @param event An ActionEvent that represents the event when the associated play pause button is pressed.
	 */
	@FXML
	public void playPauseButtonPressed(ActionEvent event) {
		applyChangesToPlayPauseButton();
	}
	
	/** This method stops the current song and the cover animation when the play button state is paused, else maintains 
	 * the song and the cover art animation working until the current song finishes.
	 */
	public void applyChangesToPlayPauseButton() {
		if(musicPlayer.getMediaPlayer().getStatus().compareTo(MediaPlayer.Status.PLAYING) == 0) {
			musicPlayer.getMediaPlayer().pause();
			playPauseButton.setGraphic(new ImageView(PLAY_ICON));
			caat.pause();
		} else {
			musicPlayer.getMediaPlayer().play();
			playPauseButton.setGraphic(new ImageView(PAUSE_ICON));
			restartThreads();
		}
	}
	
	/** This method play the previous track in the music folder when this exist.
	 * @param event An ActionEvent that represents the event when the associated previous track button is pressed.
	 */
	@FXML
	public void prevTrackButtonPressed(ActionEvent event) {
		try {
			if(caat != null) {
				caat.pause();
			}
			int cI = musicPlayer.getCurrentPlayList().indexOf(musicPlayer.getCurrentSong());
			int nI = cI-1;
			musicPlayer.setMedia(nI);
		} catch(IndexOutOfBoundsException aioobe) {
			restartThreads();
		}
	}
	
	/** This method shows a panel with some visual changes for the interface.
	 * @param event An ActionEvent that represents the event when the associated settings button is pressed.
	 */
	@FXML
	public void settingsButtonPressed(ActionEvent event) {
		//TODO implement the pane with the options
	}
	
	/** This method allows to set the media player in shuffle mode.
	 * @param event An ActionnEvent that represents the event when the associated shuffle switch button is pressed.
	 */
	@FXML
	public void shuffleSwitchButtonPressed(ActionEvent event) {
		
	}
	
	/** This method allows to change the icon in the volume section when the song is muted or not.
	 * @param event An ActionEvent that represents the event when the associated volume switch button is pressed.
	 */
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
	
	/** This method allow to delete a existing music folder unlike the default music folder. 
	 * @param event An ActionEvent that represents the event when the associated delete list button is pressed.
	 */
	@FXML
	public void deleteListButtonPressed(ActionEvent event) {
		try {
			musicPlayer.removeMusicFolderFromLibraries(librariesTableView.getSelectionModel().getSelectedItem());
		} catch(NullPointerException npe) {
			showErrorAlert("No target selected", "You must select a music folder before performing this action");
		} catch (AttemptedToRemoveDemoLibraryException e) {
			showErrorAlert(e.getMessage(), "The music folder could not be removed from libraries due it is the demo library");
		} catch (AttemptedToRemoveCurrentPlayListException e) {
			showErrorAlert(e.getMessage(), "The music folder could not be removed from libraries due it is being played right now");
		}
		librariesTableView.setItems(musicPlayer.getMusicFolders());
	}
	
	/** This method saves the current music folders inside a serializable file.
	 * @param event A WindowEvent that represents the data is being saved.
	 */
	public void save(WindowEvent event) {
		try {
			musicPlayer.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** This method allows to obtain the Label with current time passed.
	 * @return A Label that represents the current time passed.
	 */
	public Label getCurrentTimeLabel() {
		return currentTimeLabel;
	}
	
	/** This method allows to obtain the Slider with the current time passed.
	 * @return A Slider that represents the current time passed.
	 */
	public Slider getTrackTimeSlider() {
		return trackTimeSlider;
	}
	
	/** This method allows to obtain a Label with the current song duration.
	 * @return A Label with the current song duration.
	 */
	public Label getDurationLabel() {
		return durationLabel;
	}
	
	/** This method refresh all the icons when another song is picked from the music folder.
	 */
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
		refreshPlayerBackground();
	}
	
	/** This method refresh the player background when another song is picked from the music folder.
	 */
	public void refreshPlayerBackground() {
		Color color1 = songThumbnail.getImage().getPixelReader().getColor((int)songThumbnail.getImage().getWidth()/2, (int)songThumbnail.getImage().getHeight()/2).brighter().brighter();
		Color color2 = color1.brighter().darker().darker().darker().darker();
		Pane background = (Pane)coverImageCircle.getParent();
		int red = (int)(color1.getRed()*255);
		int green = (int)(color1.getGreen()*255);
		int blue = (int)(color1.getBlue()*255);
		String rgba1 = "rgba("+red+","+green+","+blue+")";
		red = (int)(color2.getRed()*255);
		green = (int)(color2.getGreen()*255);
		blue = (int)(color2.getBlue()*255);
		String rgba2 = "rgba("+(red)+","+(green)+","+(blue)+")";
		background.setStyle("-fx-background-color: linear-gradient(to bottom, "+rgba1+", "+rgba2+")");
		LinearGradient linearGrad = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, 
                new Stop(0.1f, color2.brighter().brighter().brighter()),
                new Stop(1.0f, color1.brighter().brighter()));
		backgroundCircle.setFill(linearGrad);
	}
	
	/** This method allows to obtain the current music player.
	 * @return A MusicPlayer that represents the current music player.
	 */
	public MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}
	
	/** This method restarts the cover art animation when the song is changed or the player changes from pause to play.
	 */
	public void restartThreads() {
		if(caat != null) {
			caat.pause();
		}
		caat = new CoverArtAnimationThread(coverImageCircle);
		caat.setDaemon(true);
		caat.start();
	}
	
	/** This method shows an AlertType when an exception is thrown.
	 * @param header A String that represents the header of the thrown exception or error.
	 * @param message A String that represents the message of the thrown exception or error.
	 */
	public void showErrorAlert(String header, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(message);
		alert.setHeaderText(header);
		alert.showAndWait();
	}
}
