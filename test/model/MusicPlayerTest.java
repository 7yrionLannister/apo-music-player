package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import customExceptions.FolderWithoutMP3ContentException;

public class MusicPlayerTest {
	private MusicPlayer mp;
	
	private void setupScenario1() {
		mp = null;
	}
	
	@Test
	public void createMusicPlayerTest() {
		setupScenario1();
		try {
			mp = new MusicPlayer();
		} catch (ClassNotFoundException | IOException | FolderWithoutMP3ContentException e) {
			fail("Music Player is the main connection between the ui and the model packages and doesn't receive parameters, so it should be possible to instance it without trouble");
		}
	}

}
