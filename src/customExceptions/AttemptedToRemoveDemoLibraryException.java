<<<<<<< HEAD
package customExceptions;

public class AttemptedToRemoveDemoLibraryException extends Exception {
	@Override
	public String getMessage() {
		return "Unable to remove demo library, permission denied";
	}
}
=======
package customExceptions;

public class AttemptedToRemoveDemoLibraryException extends Exception {
	/**
	 * This method throws a message in console indicating that the user tried to remove the default music folder.
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html#getMessage--" title="getMessage">getMessage</a> in class Throwable.
	 */
	@Override
	public String getMessage() {
		return "Unable to remove demo library, permission denied";
	}
}
>>>>>>> d10f835effaad2e3e461e09675b443a1c38ad300
