package hr.fer.zemris.java.gui.calc.model;

import java.util.function.DoubleBinaryOperator;

/**
 * Su�elje specificira model jednog jednostavnog kalkulatora.
 * 
 * @author marcupic
 */
public interface CalcModel {
	/**
	 * Prijava promatra�a koje treba obavijestiti kada se
	 * promijeni vrijednost pohranjena u kalkulatoru.
	 * 
	 * @param l promatra�; ne smije biti <code>null</code>
	 * 
	 * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
	 */
	void addCalcValueListener(CalcValueListener l);

	/**
	 * Odjava promatra�a s popisa promatra�a koje treba 
	 * obavijestiti kada se promijeni vrijednost 
	 * pohranjena u kalkulatoru.
	 * 
	 * @param l promatra�; ne smije biti <code>null</code>
	 * 
	 * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
	 */
	void removeCalcValueListener(CalcValueListener l);
	
	/**
	 * Vra�a tekst koji treba prikazati na zaslonu kalkulatora.
	 * Detaljnija specifikacija dana je u uputi za doma�u zada�u.
	 * 
	 * @return tekst za prikaz na zaslonu kalkulatora
	 */
	String toString();
	
	/**
	 * Vra�a trenutnu vrijednost koja je pohranjena u kalkulatoru.
	 * 
	 * @return vrijednost pohranjena u kalkulatoru
	 */
	double getValue();

	/**
	 * Upisuje decimalnu vrijednost u kalkulator. Vrijednost smije
	 * biti i beskona�no odnosno NaN. Po upisu kalkulator 
	 * postaje needitabilan.
	 * 
	 * @param value vrijednost koju treba upisati
	 */
	void setValue(double value);
	
	/**
	 * Vra�a informaciju je li kalkulator editabilan (drugim rije�ima,
	 * smije li korisnik pozivati metode {@link #swapSign()},
	 * {@link #insertDecimalPoint()} te {@link #insertDigit(int)}).
	 * @return <code>true</code> ako je model editabilan, <code>false</code> ina�e
	 */
	boolean isEditable();

	/**
	 * Resetira trenutnu vrijednost na neunesenu i vra�a kalkulator u
	 * editabilno stanje.
	 */
	void clear();

	/**
	 * Obavlja sve �to i {@link #clear()}, te dodatno uklanja aktivni
	 * operand i zakazanu operaciju.
	 */
	void clearAll();

	/**
	 * Mijenja predznak unesenog broja.
	 * 
	 * @throws CalculatorInputException ako kalkulator nije editabilan
	 */
	void swapSign() throws CalculatorInputException;
	
	/**
	 * Dodaje na kraj trenutnog broja decimalnu to�ku.
	 * 
	 * @throws CalculatorInputException ako nije jo� unesena niti jedna znamenka broja,
	 *         ako broj ve� sadr�i decimalnu to�ku ili ako kalkulator nije editabilan
	 */
	void insertDecimalPoint() throws CalculatorInputException;

	/**
	 * U broj koji se trenutno upisuje na kraj dodaje poslanu znamenku.
	 * Ako je trenutni broj "0", dodavanje jo� jedne nule se potiho
	 * ignorira.
	 * 
	 * @param digit znamenka koju treba dodati
	 * @throws CalculatorInputException ako bi dodavanjem predane znamenke broj postao prevelik za kona�an prikaz u tipu {@link Double}, ili ako kalkulator nije editabilan.
	 * @throws IllegalArgumentException ako je <code>digit &lt; 0</code> ili <code>digit &gt; 9</code>
	 */
	void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException;

	/**
	 * Provjera je li upisan aktivni operand.
	 * 
	 * @return <code>true</code> ako je aktivani operand upisan, <code>false</code> ina�e
	 */
	boolean isActiveOperandSet();
	
	/**
	 * Dohvat aktivnog operanda.
	 * 
	 * @return aktivni operand
	 * 
	 * @throws IllegalStateException ako aktivni operand nije postavljen
	 */
	double getActiveOperand() throws IllegalStateException;

	/**
	 * Metoda postavlja aktivni operand na predanu vrijednost. 
	 * Ako kalkulator ve� ima postavljen aktivni operand, predana
	 * vrijednost ga nadja�ava.
	 * 
	 * @param activeOperand vrijednost koju treba pohraniti kao aktivni operand
	 */
	void setActiveOperand(double activeOperand);
	
	/**
	 * Uklanjanje zapisanog aktivnog operanda.
	 */
	void clearActiveOperand();
	
	/**
	 * Dohvat zakazane operacije.
	 * 
	 * @return zakazanu operaciju, ili <code>null</code> ako nema zakazane operacije
	 */
	DoubleBinaryOperator getPendingBinaryOperation();

	/**
	 * Postavljanje zakazane operacije. Ako zakazana operacija ve�
	 * postoji, ovaj je poziv nadja�ava predanom vrijedno��u.
	 * 
	 * @param op zakazana operacija koju treba postaviti; smije biti <code>null</code>
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
	
}
