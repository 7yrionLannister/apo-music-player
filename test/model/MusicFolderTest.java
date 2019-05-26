package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import customExceptions.FolderWithoutMP3ContentException;

//TODO si se puede, usar DirectoryChooser para que el que pruebe elija una carpeta con musica para hacer pruebas del arbol y ordenamiento
public class MusicFolderTest {
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
		setupScenario1();
		try {
			String dir = "resources";
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
		//TODO implement
	}
	
	@Test
	public void sortSongsByTitleTest() {
		//TODO implement
	}
	
	@Test
	public void sortSongsByAlbumTest() {
		//TODO implement
	}
	
	@Test
	public void sortSongsBySizeTest() {
		//TODO implement
	}
	
	@Test
	public void sortSongsByGenreTest() {
		//TODO implement
	}
}
