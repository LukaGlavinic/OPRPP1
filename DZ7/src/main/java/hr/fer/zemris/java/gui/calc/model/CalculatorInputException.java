package hr.fer.zemris.java.gui.calc.model;

import java.io.Serial;

/**
 * Iznimka koja signalizira da je korisnik probao napraviti
 * nedozvoljeni unos u kalkulator (primjerice, broj koji se 
 * unosi ve� ima decimalnu to�ku, a korisnik je probao dodati
 * jo� jednu).
 * 
 * @author marcupic
 */
public class CalculatorInputException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 */
	public CalculatorInputException() {}

	/**
	 * Konstruktor.
	 * @param message poruka koja opisuje pogre�ku
	 */
	public CalculatorInputException(String message) {
		super(message);
	}
}