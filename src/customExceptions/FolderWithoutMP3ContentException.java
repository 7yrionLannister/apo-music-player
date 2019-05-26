package customExceptions;

public class FolderWithoutMP3ContentException extends Exception {
	private String folder;
	
	public FolderWithoutMP3ContentException(String f) {
		folder = f;
	}
	
	@Override
	public String getMessage() {
		return "The requested folder does not contain MP3 files: "+folder;
	}
}
