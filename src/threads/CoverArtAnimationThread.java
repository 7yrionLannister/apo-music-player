package threads;

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
			coverArt.setRotate(coverArt.getRotate()+5);
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
