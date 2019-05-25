package customExceptions;

public class NotMP3FileException extends IllegalArgumentException {
	private String extension;
	
	public NotMP3FileException(String extension) {
		super("Only mp3 files are supported");
		this.extension = extension;
	}
	
	@Override
	public String getMessage() {
		String msg = super.getMessage();
		msg += ": " + extension;
		return msg;
	}
}
