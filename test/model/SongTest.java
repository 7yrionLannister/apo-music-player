package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import customExceptions.NotMP3FileException;

public class SongTest {
	private Song s;
	
	private void setupScenario1() {
		s = null;
	}
	
	@Test
	public void createSongWithValidPathAndValidAudioFormatTest() {
		setupScenario1();
		try {
			String fileName = "bensound-happyrock.mp3";
			String songPath = "music"+File.separator+fileName;
			s = new Song(new File(songPath));
			assertTrue("The name of the file does not correspond to the requested one", fileName.equals(s.getFileName()));
		} catch (IOException e) {
			fail("The song should have been created as the file is the one that comes in the repository as a demo audio with MP3 format");
		}
	}
	
	@Test
	public void createSongWithInvalidPathTest() {
		setupScenario1();
		try {
			s = new Song(new File("idonotexist.mp3"));
			fail("The song shouldn't have been created as the file named idonotexist.mp3 literally doesn't exist in the repository");
		} catch (IOException e) {
			assertTrue(true);
		}
	}	
	
	@Test
	public void createSongWithValidPathButInvalidFileFormat() {
		setupScenario1();
		try {
			s = new Song(new File("data"+File.separator+"testfile.txt"));
			fail("The song shouldn't have been created as the file does not have mp3 format");
		} catch (NotMP3FileException | IOException e) {
			assertTrue(true);
		}
	}
}
