
package customExceptions;

public class AttemptedToRemoveDemoLibraryException extends Exception {
	/**This method throws a message in console indicating that the user tried to remove the default music folder.
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html#getMessage--" title="getMessage">getMessage</a> in class Throwable.
	 */
	@Override
	public String getMessage() {
		return "Unable to remove demo library, permission denied";
	}
}

