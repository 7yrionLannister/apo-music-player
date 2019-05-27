package threads;

import javafx.application.Platform;
import javafx.scene.shape.Circle;

public class CoverArtAnimationThread extends Thread {
	/**
	 * It represents the song cover art in circle form.
	 */
	private Circle coverArt;
	/**
	 * It represents if the song is paused or not.
	 */
	private boolean onPause;
	/**
	 * CoverArtAnimationThread constructor method allows to instance this class through a Circle that represents
	 * the song cover art.
	 * @param ca A Circle represents the song cover art in circle form.
	 */
	public CoverArtAnimationThread(Circle ca) {
		coverArt = ca;
		onPause = false;
	}
	/**
	 * This method allows to run the circle animation when the song is not paused. 
	 */
	@Override
	public void run() {
		while(!onPause) {
			Platform.runLater(new Runnable() {
				public void run() {
					coverArt.setRotate(coverArt.getRotate()+1);
				}
			});
			
			try {
				sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * This method allows to set the song running state in paused.
	 */
	public void pause() {
		onPause = true;
	}
}
