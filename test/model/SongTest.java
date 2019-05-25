package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import customExceptions.NotMP3FileException;

public class SongTest {
	private Song s;
	
	public void setupScenario1() {
		s = null;
	}
	
	@Test
	public void createSongWithValidPathAndValidAudioFormatTest() {
		setupScenario1();
		try {
			s = new Song(new File("resources"+File.separator+"Spectre.mp3"));
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
			s = new Song(new File("resources"+File.separator+"testfile.txt"));
			fail("The song shouldn't have been created as the file does not have mp3 format");
		} catch (NotMP3FileException | IOException e) {
			assertTrue(true);
		}
	}
}
