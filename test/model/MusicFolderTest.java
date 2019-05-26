package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import customExceptions.FolderWithoutMP3ContentException;

class MusicFolderTest {
	private MusicFolder mf;
	
	public void setupScenario1() {
		mf = null;
	}
	
	@Test
	public void createMusicFolderWithInvalidPathTest() {
		setupScenario1();
		try {
			mf = new MusicFolder(new File("idonotexist"));
			fail("The music folder shouldn't have been created as the path does not reference an existing folder");
		} catch (IOException | FolderWithoutMP3ContentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void createMusicFolderWithValidPathTest() {
		
	}
}
