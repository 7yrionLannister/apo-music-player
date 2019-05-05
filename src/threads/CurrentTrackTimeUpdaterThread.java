package threads;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ui.PrimaryStageController;

public class CurrentTrackTimeUpdaterThread extends Thread {
	private PrimaryStageController psc;

	public CurrentTrackTimeUpdaterThread(PrimaryStageController psc) {
		this.psc = psc;
	}

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
}
