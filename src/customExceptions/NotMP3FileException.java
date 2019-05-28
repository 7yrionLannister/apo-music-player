package customExceptions;

public class NotMP3FileException extends IllegalArgumentException {
	/** It represents the extension of the file that caused the exception.
	 */
	private String extension;
	
	/**The method allows to instance a new NotMP3FileException with the corresponding extension of the file that caused the exception.
	 * @param extension The extension of the file that caused the exception.
	 */
	public NotMP3FileException(String extension) {
		super("Only mp3 files are supported");
		this.extension = extension;
	}
	
	/** This method throws a message in console indicating what was the extension that caused the exception.
	 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html#getMessage--" title="getMessage">getMessage</a> in class Throwable.
	 */
	@Override
	public String getMessage() {
		String msg = super.getMessage();
		msg += ": " + extension;
		return msg;
	}
}
