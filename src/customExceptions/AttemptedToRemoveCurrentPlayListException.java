package customExceptions;

public class AttemptedToRemoveCurrentPlayListException extends IllegalStateException {
	/**
	 * It represents the current play list path that the user tried to remove.
	 */
	private String library;
	/**
	 * AttemptedToRemoveCurrentPlayListException constructor method the received the current play list path.
	 * @param path A String that represents the current play list path that the user tried to remove.
	 */
	public AttemptedToRemoveCurrentPlayListException(String path) {
		library = path;
	}
	/**
	 * This method throws a message in console indicating what was the current play list path that the user tried to remove.
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html#getMessage--" title="getMessage">getMessage</a> in class Throwable.
	 */
	@Override
	public String getMessage() {
		return "It is not possible to remove the playlist when it is being played: " + library;	
	}
}
