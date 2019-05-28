package threads;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.MusicPlayer;
import model.Song;
import ui.PrimaryStageController;

public class CurrentTrackTimeUpdaterThread extends Thread {
	
	/** It represents music player interface controller.
	 */
	private PrimaryStageController psc;
	
	/** It represents if the shuffle mode is activated or not.
	 */
	public boolean shuffle;
	
	/** CurrentTrackTimeUpdaterThread constructor method that receives the music player interface controller as parameter.
	 * @param psc A PrimaryStageController that represents the music player interface controller.
	 */
	public CurrentTrackTimeUpdaterThread(PrimaryStageController psc, boolean sh) {
		this.psc = psc;
		shuffle = sh;
	}
	
	/** This method allows to run and update the song time duration when a song is selected and played. Besides, it changes
	 * the reproduction order when shuffle is enabled.
	 */
	@Override
	public void run() {
		while(true) {
			if(psc.getMusicPlayer().getMediaPlayer() != null) {
				try {
					MediaPlayer mep = psc.getMusicPlayer().getMediaPlayer(); 
					Media currentAudio = psc.getMusicPlayer().getCurrentAudio();
					double totalMillis = currentAudio.getDuration().toMillis();
					int totalSeconds = (int) (totalMillis / 1000) % 60;
					int totalMinutes = (int) (totalMillis / (1000 * 60));

					double millis = mep.getCurrentTime().toMillis();
					int seconds = (int) (millis / 1000) % 60;
					int minutes = (int) (millis / (1000 * 60));

					Platform.runLater(() -> {
						psc.getDurationLabel().setText(String.format("%02d:%02d", totalMinutes, totalSeconds));
						psc.getCurrentTimeLabel().setText(String.format("%02d:%02d", minutes, seconds));

						psc.getTrackTimeSlider().setValue(millis/totalMillis*psc.getTrackTimeSlider().getMax());
					});
					if(totalMillis == millis) {
						Platform.runLater(() -> {
							MusicPlayer mp = psc.getMusicPlayer();
							ArrayList<Song> toShuffle = mp.getCurrentPlayList();
							if(shuffle) {
								int song = (int) (Math.random() * toShuffle.size()) + 1;
								psc.getMusicPlayer().setMedia(song);
							} else {
								int song = toShuffle.indexOf(mp.getCurrentSong()) + 1;
								if(song < toShuffle.size()) {
									mp.setMedia(song);
								} else {
									mp.setMedia(0);
								}
							}
						});
					}
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
	
	/** This method allows to set the shuffle value when is needed.
	 * @param sh A boolean that represents if the shuffle mode is activated or not.
	 */
	public void setShuffle(boolean sh) {
		shuffle = sh;
	}
	
	/** This method allows to obtain the shuffle value when is needed.
	 * @return A boolean that represents if the shuffle mode is activated or not.
	 */
	public boolean getShuffle() {
		return shuffle;
	}
}
