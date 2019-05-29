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
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.MusicFolder;
import model.MusicPlayer;
import model.Song;
import threads.CoverArtAnimationThread;
import threads.CurrentTrackTimeUpdaterThread;


public class PrimaryStageController {

	/** It represents the current thread to modify the song time inside the application and the shuffle mode.	 
	 */
	private CurrentTrackTimeUpdaterThread cttu;

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

	/** It represents the shuffle activated icon when the shuffle mode is activated. 	
	 */
	public final static Image SHUFFLE_ACTIVATED = new Image(new File("imgs"+File.separator+"shuffle.png").toURI().toString(), 40, 40, false, false);

	/** It represents the shuffle disabled icon when the shuffle mode is disabled..
	 */
	public final static Image SHUFFLE_DISABLED = new Image(new File("imgs"+File.separator+"not-shuffle.png").toURI().toString(), 40, 40, false, false);

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
	@FXML private TextField searchTextField;

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
		shuffleSwitchButton.setGraphic(new ImageView(SHUFFLE_DISABLED));
		cttu = new CurrentTrackTimeUpdaterThread(this, false);
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
		if(!cttu.getShuffle()) {
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
		} else {
			int song = (int) (Math.random() * musicPlayer.getCurrentPlayList().size()) + 1;
			musicPlayer.setMedia(song);
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
		if(!cttu.getShuffle()) {
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
		} else {
			int song = (int) (Math.random() * musicPlayer.getCurrentPlayList().size()) + 1;
			musicPlayer.setMedia(song);
		}
	}

	/** This method shows play list history text files when these exist.
	 * @param event An ActionEvent that represents the event when the associated history button is pressed.
	 */
	@FXML
	public void historyButtonPressed(ActionEvent event) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("HistoryViewer.fxml"));
			Scene s = new Scene(root);
			Stage st = new Stage();
			st.setScene(s);
			st.initOwner(songTitleLabel.getParent().getScene().getWindow());
			st.initModality(Modality.WINDOW_MODAL);
			st.showAndWait();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/** This method allows to set the media player in shuffle mode.
	 * @param event An ActionnEvent that represents the event when the associated shuffle switch button is pressed.
	 */
	@FXML
	public void shuffleSwitchButtonPressed(ActionEvent event) {
		if(cttu.getShuffle() == true) {
			shuffleSwitchButton.setGraphic(new ImageView(SHUFFLE_DISABLED));
			cttu.setShuffle(false);
		} else {
			shuffleSwitchButton.setGraphic(new ImageView(SHUFFLE_ACTIVATED));
			cttu.setShuffle(true);
		}
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
			musicPlayer.saveHistory();
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

	/** This method allows to sort the songs in the table view by title.
	 * @param event An ActionEvent that represents the event when the associated sort by title button is pressed.
	 */
	@FXML
	public void sortByTitle(ActionEvent event) {
		musicPlayer.getCurrentMusicFolder().sortSongsByTitle();
		musicInfoTableView.setItems(FXCollections.observableArrayList(musicPlayer.getFirstMusicFolder().getSongs()));
	}

	/** This method allows to sort the songs in the table view by genre.
	 * @param event An ActionEvent that represents the event when the associated sort by genre button is pressed.
	 */
	@FXML
	public void sortByGenre(ActionEvent event) {
		musicPlayer.getCurrentMusicFolder().sortSongsByGenre();
		musicInfoTableView.setItems(FXCollections.observableArrayList(musicPlayer.getFirstMusicFolder().getSongs()));
	}

	/** This method allows to sort the songs in the table view by album.
	 * @param event An ActionEvent that represents the event when the associated sort by album button is pressed.
	 */
	@FXML
	public void sortByAlbum(ActionEvent event) {
		musicPlayer.getCurrentMusicFolder().sortSongsByAlbum();
		musicInfoTableView.setItems(FXCollections.observableArrayList(musicPlayer.getFirstMusicFolder().getSongs()));
	}

	/** This method allows to sort the songs in the table view by artist.
	 * @param event An ActionEvent that represents the event when the associated sort by artist button is pressed.
	 */
	@FXML
	public void sortByArtist(ActionEvent event) {
		musicPlayer.getCurrentMusicFolder().sortSongsByArtist();
		musicInfoTableView.setItems(FXCollections.observableArrayList(musicPlayer.getFirstMusicFolder().getSongs()));
	}

	/** This method allows to sort the songs in the table view by size.
	 * @param event An ActionEvent that represents the event when the associated sort by size button is pressed.
	 */
	@FXML
	public void sortBySize(ActionEvent event) {
		musicPlayer.getCurrentMusicFolder().sortSongsBySize();
		musicInfoTableView.setItems(FXCollections.observableArrayList(musicPlayer.getFirstMusicFolder().getSongs()));
	}

	/** This method allows to found a song in the current folder specifying its file name.
	 * @param event An ActionEvent that represents the event when the associated search enter button is pressed.
	 */
	@FXML
	public void searchPressed(ActionEvent event) {
		Song match = musicPlayer.getCurrentMusicFolder().search(searchTextField.getText());
		showResult(match);
	}

	/** This method allows to show a new window with the searched song in a table view. 
	 * @param s A Song that represents the searched song in the current music folder.
	 */
	public void showResult(Song s) {
		if(s != null) {
			ObservableList<Song> SongResults = FXCollections.observableArrayList();
			SongResults.add(s);

			TableView<Song> result = new TableView<Song>();
			TableColumn<Song, String> titleColumn = new TableColumn<Song, String>("Title");
			TableColumn<Song, String> genreColumn = new TableColumn<Song, String>("Genre");
			TableColumn<Song, Integer> albumColumn = new TableColumn<Song, Integer>("Album");
			TableColumn<Song, String> artistColumn = new TableColumn<Song, String>("Artist");
			TableColumn<Song, String> sizeColumn = new TableColumn<Song, String>("Size");

			titleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
			genreColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));
			albumColumn.setCellValueFactory(new PropertyValueFactory<Song, Integer>("album"));
			artistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
			sizeColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("size"));

			result.getColumns().addAll(titleColumn, genreColumn, albumColumn, artistColumn, sizeColumn);
			result.setItems(SongResults);

			Stage popUp = new Stage();
			Scene scene = new Scene(result);
			popUp.setWidth(350);
			popUp.setHeight(90);
			popUp.setScene(scene);
			popUp.setTitle("Your song has been found");
			popUp.setResizable(false);
			popUp.show();
		}
		else {
			showDialog("Your Song has not been found");
		}
	}

	/** This method allows to know what was the searching time if the song was found. Else, it allows to know why the 
	 * wasn't be found in the music folder.
	 * @param message A String that represents the sorting time if the searching is achieved. Else, it shows that searching
	 * can't be performed such by the searched song is not in that folder or the user introduced a invalid input.
	 */
	public void showDialog(String message) {

		Dialog dialog = new Dialog();
		dialog.setContentText(message);
		if(message.substring(0, 4).equalsIgnoreCase("time")) {
			dialog.setTitle("Time sorting");
		}
		else if(message.substring(0, 4).equalsIgnoreCase("your")) {
			dialog.setTitle("Unsuccessful search");
		}
		else {
			dialog.setTitle("Invalid input");
		}
		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(event -> window.hide());
		dialog.showAndWait();
	}
}
