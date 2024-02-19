package hr.fer.zemris.java.hw06.shell;

import java.io.Serial;

public class ShellIOException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;
	
	public ShellIOException() {}
	
	public ShellIOException(String message) {
	super(message);
	}
	
	public ShellIOException(Throwable cause) {
		super(cause);
	}
	
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
}