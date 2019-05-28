package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import customExceptions.FolderWithoutMP3ContentException;


public class MusicFolderTest {	
	private MusicFolder mf;
	
	private void setupScenario1() {
		mf = null;
	}
	
	private void setupScenario2() {
		try {
			mf = new MusicFolder(new File("music"));
		} catch (IOException | FolderWithoutMP3ContentException e) {
			
		}
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
		setupScenario1();
		try {
			String dir = "music";
			mf = new MusicFolder(new File(dir));
			assertTrue("The new folder is not the requested one", dir.equals(mf.getFolderName()));
		} catch (IOException | FolderWithoutMP3ContentException e) {
			fail("The music folder should have been created as the resources folder is the demo library that comes in the repository");
		}
	}
	
	@Test
	public void createMusicFolderWithValidPathButNoMP3Content() {
		setupScenario1();
		try {
			mf = new MusicFolder(new File("test"+File.separator+"model"));
		} catch (IOException | FolderWithoutMP3ContentException e) {
			if(e instanceof FolderWithoutMP3ContentException) {
				assertTrue(true);
			} else {
				fail("The type of the captured exception does not correspond to the suitable");
			}
		}
	}
	
	@Test
	public void inorderTest() {
		setupScenario2();
		ArrayList<Song> songs = mf.inorder();
		
		for(int i = 1; i < songs.size(); i++) {
			assertTrue("The returned list of songs is not in order", songs.get(i).compareTo(songs.get(i-1)) >= 0);
		}
	}
	
	@Test
	public void sortSongsByTitleTest() {
		setupScenario2();
		mf.sortSongsByTitle();
		ArrayList<Song> songs = mf.getSongs();
		TitleComparator tc = new TitleComparator();
		
		for(int i = 1; i < songs.size(); i++) {
			assertTrue("The list is not sorted by title", tc.compare(songs.get(i), songs.get(i-1)) >= 0);
		}
	}
	
	@Test
	public void sortSongsByAlbumTest() {
		setupScenario2();
		mf.sortSongsByAlbum();
		ArrayList<Song> songs = mf.getSongs();
		AlbumComparator ac = new AlbumComparator();
		
		for(int i = 1; i < songs.size(); i++) {
			assertTrue("The list is not sorted by album", ac.compare(songs.get(i), songs.get(i-1)) >= 0);
		}
	}
	
	@Test
	public void sortSongsBySizeTest() {
		setupScenario2();
		mf.sortSongsBySize();
		ArrayList<Song> songs = mf.getSongs();
		SizeComparator sc = new SizeComparator();
		
		for(int i = 1; i < songs.size(); i++) {
			assertTrue("The list is not sorted by size", sc.compare(songs.get(i), songs.get(i-1)) >= 0);
		}
	}
	
	@Test
	public void sortSongsByGenreTest() {
		setupScenario2();
		mf.sortSongsByGenre();
		ArrayList<Song> songs = mf.getSongs();
		GenreComparator gc = new GenreComparator();
		
		for(int i = 1; i < songs.size(); i++) {
			assertTrue("The list is not sorted by genre", gc.compare(songs.get(i), songs.get(i-1)) >= 0);
		}
	}

	@Test
	public void sortSongsByArtistTest() {
		setupScenario2();
		mf.sortSongsByArtist();
		ArrayList<Song> songs = mf.getSongs();
		ArtistComparator arc = new ArtistComparator();
	
		for(int i = 1; i < songs.size(); i++) {
			assertTrue("The list is not sorted by genre", arc.compare(songs.get(i), songs.get(i-1)) >= 0);
		}
	}
}