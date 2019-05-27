
package customExceptions;

public class FolderWithoutMP3ContentException extends Exception {
	/**
	 * It represents the name of the folder without mp3 files.
	 */
	private String folder;
	/**
	 * FolderWithoutMP3ContentException constructor method that receives the folder without mp3 files name as parameter.
	 * @param f A String that represents the name of the folder without mp3 files.
	 */
	public FolderWithoutMP3ContentException(String f) {
		folder = f;
	}
	/**
	 * This method throws a message in console indicating what was the folder without music that the user tried to add.
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html#getMessage--" title="getMessage">getMessage</a> in class Throwable.
	 */
	@Override
	public String getMessage() {
		return "The requested folder does not contain MP3 files: "+folder;
	}
}

