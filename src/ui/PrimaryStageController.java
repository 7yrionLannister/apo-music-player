package ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class PrimaryStageController {

    @FXML
    private ImageView songThumbnail;

    @FXML
    private Label songNameLabel;

    @FXML
    private Label SongAlbumLabel;

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
    void aboutButtonPressed(ActionEvent event) {

    }

    @FXML
    void equalizerSwitchButtonPressed(ActionEvent event) {

    }

    @FXML
    void listOptionsButtonPressed(ActionEvent event) {

    }

    @FXML
    void nextTrackButtonPressed(ActionEvent event) {

    }

    @FXML
    void playPauseButtonPressed(ActionEvent event) {

    }

    @FXML
    void prevTrackButtonPressed(ActionEvent event) {

    }

    @FXML
    void settingsButtonPressed(ActionEvent event) {

    }

    @FXML
    void shuffleSwitchButtonPressed(ActionEvent event) {

    }

    @FXML
    void volumeSwitchPressed(ActionEvent event) {

    }

}
