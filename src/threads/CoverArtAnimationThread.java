package threads;

import javafx.application.Platform;
import javafx.scene.shape.Circle;

public class CoverArtAnimationThread extends Thread {
	private Circle coverArt;
	private boolean onPause;
	
	public CoverArtAnimationThread(Circle ca) {
		coverArt = ca;
		onPause = false;
	}
	
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
	
	public void pause() {
		onPause = true;
	}
}
