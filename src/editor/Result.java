package editor;

public class Result {
	private String message = null;

	public Result() {}

	public Result(final String message) {
		this.message = message;
	}

	public boolean isOk() { return message == null; }

	public String getMessage() { return isOk() ? "Ok" : message; }
}
