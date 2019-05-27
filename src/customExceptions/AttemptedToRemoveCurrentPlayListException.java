package customExceptions;

public class AttemptedToRemoveCurrentPlayListException extends IllegalStateException {
	private String library;
	
	public AttemptedToRemoveCurrentPlayListException(String path) {
		library = path;
	}
	
	@Override
	public String getMessage() {
		return "It is not possible to remove the playlist when it is being played: " + library;	
	}
}
