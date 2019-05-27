package threads;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ui.PrimaryStageController;

public class CurrentTrackTimeUpdaterThread extends Thread {
	
	/** It represents music player interface controller.
	 */
	private PrimaryStageController psc;
	
	public boolean shuffle;
	
	/** CurrentTrackTimeUpdaterThread constructor method that receives the music player interface controller as parameter.
	 * @param psc A PrimaryStageController that represents the music player interface controller.
	 */
	public CurrentTrackTimeUpdaterThread(PrimaryStageController psc, boolean sh) {
		this.psc = psc;
		shuffle = sh;
	}
	
	/** This method allows to run and update the song time duration when a song is selected and played.
	 */
	@Override
	public void run() {
		while(true) {
			if(psc.getMusicPlayer().getMediaPlayer() != null) {
				try {
					MediaPlayer mp = psc.getMusicPlayer().getMediaPlayer(); 
					Media currentAudio = psc.getMusicPlayer().getCurrentAudio();
					double totalMillis = currentAudio.getDuration().toMillis();
					int totalSeconds = (int) (totalMillis / 1000) % 60;
					int totalMinutes = (int) (totalMillis / (1000 * 60));

					double millis = mp.getCurrentTime().toMillis();
					int seconds = (int) (millis / 1000) % 60;
					int minutes = (int) (millis / (1000 * 60));

					Platform.runLater(() -> {
						psc.getDurationLabel().setText(String.format("%02d:%02d", totalMinutes, totalSeconds));
						psc.getCurrentTimeLabel().setText(String.format("%02d:%02d", minutes, seconds));

						psc.getTrackTimeSlider().setValue(millis/totalMillis*psc.getTrackTimeSlider().getMax());
					});
					/*
					 * if(llego al final de la cancion) {
					 * 	if(shuffle) {
					 * 		elija una cancion aleatroia
					 * 	} else {
					 * 		elija la siguiente cancion
					 * 	}
					 * }
					 */
				} catch(NullPointerException npe) {

				}
			}
			try {
				sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setShuffle(boolean sh) {
		shuffle = sh;
	}
}
