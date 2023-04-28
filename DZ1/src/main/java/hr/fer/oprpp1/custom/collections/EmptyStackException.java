package hr.fer.oprpp1.custom.collections;

public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EmptyStackException() {}
	
	public EmptyStackException(String poruka) {
		super(poruka);
	}
	
	public EmptyStackException(Throwable uzrok) {
		super(uzrok);
	}
	
	public EmptyStackException(String poruka, Throwable uzrok) {
		super(poruka, uzrok);
	}
}