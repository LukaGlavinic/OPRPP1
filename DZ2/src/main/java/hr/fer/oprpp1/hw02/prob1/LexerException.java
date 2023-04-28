package hr.fer.oprpp1.hw02.prob1;

public class LexerException extends RuntimeException{

	/**
	 * inmika koju baca razred Lexer
	 */
	private static final long serialVersionUID = 1L;

	public LexerException() {}
	
	public LexerException(String message) {
		super(message);
	}
		
	public LexerException(Throwable cause) {
		super(cause);
	}
		
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
}