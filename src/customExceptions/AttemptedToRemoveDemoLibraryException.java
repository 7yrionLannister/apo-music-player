package customExceptions;

public class AttemptedToRemoveDemoLibraryException extends Exception {
	@Override
	public String getMessage() {
		return "Unable to remove demo library, permission denied";
	}
}
