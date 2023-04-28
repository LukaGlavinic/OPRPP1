package hr.fer.zemris.java.gui.calc.model;

import java.util.function.DoubleBinaryOperator;

/**
 * Suèelje specificira model jednog jednostavnog kalkulatora.
 * 
 * @author marcupic
 */
public interface CalcModel {
	/**
	 * Prijava promatraèa koje treba obavijestiti kada se
	 * promijeni vrijednost pohranjena u kalkulatoru.
	 * 
	 * @param l promatraè; ne smije biti <code>null</code>
	 * 
	 * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
	 */
	void addCalcValueListener(CalcValueListener l);

	/**
	 * Odjava promatraèa s popisa promatraèa koje treba 
	 * obavijestiti kada se promijeni vrijednost 
	 * pohranjena u kalkulatoru.
	 * 
	 * @param l promatraè; ne smije biti <code>null</code>
	 * 
	 * @throws NullPointerException ako je za <code>l</code> predana vrijednost <code>null</code>
	 */
	void removeCalcValueListener(CalcValueListener l);
	
	/**
	 * Vraæa tekst koji treba prikazati na zaslonu kalkulatora.
	 * Detaljnija specifikacija dana je u uputi za domaæu zadaæu.
	 * 
	 * @return tekst za prikaz na zaslonu kalkulatora
	 */
	String toString();
	
	/**
	 * Vraæa trenutnu vrijednost koja je pohranjena u kalkulatoru.
	 * 
	 * @return vrijednost pohranjena u kalkulatoru
	 */
	double getValue();

	/**
	 * Upisuje decimalnu vrijednost u kalkulator. Vrijednost smije
	 * biti i beskonaèno odnosno NaN. Po upisu kalkulator 
	 * postaje needitabilan.
	 * 
	 * @param value vrijednost koju treba upisati
	 */
	void setValue(double value);
	
	/**
	 * Vraæa informaciju je li kalkulator editabilan (drugim rijeèima,
	 * smije li korisnik pozivati metode {@link #swapSign()},
	 * {@link #insertDecimalPoint()} te {@link #insertDigit(int)}).
	 * @return <code>true</code> ako je model editabilan, <code>false</code> inaèe
	 */
	boolean isEditable();

	/**
	 * Resetira trenutnu vrijednost na neunesenu i vraæa kalkulator u
	 * editabilno stanje.
	 */
	void clear();

	/**
	 * Obavlja sve što i {@link #clear()}, te dodatno uklanja aktivni
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
	 * Dodaje na kraj trenutnog broja decimalnu toèku.
	 * 
	 * @throws CalculatorInputException ako nije još unesena niti jedna znamenka broja,
	 *         ako broj veæ sadrži decimalnu toèku ili ako kalkulator nije editabilan
	 */
	void insertDecimalPoint() throws CalculatorInputException;

	/**
	 * U broj koji se trenutno upisuje na kraj dodaje poslanu znamenku.
	 * Ako je trenutni broj "0", dodavanje još jedne nule se potiho
	 * ignorira.
	 * 
	 * @param digit znamenka koju treba dodati
	 * @throws CalculatorInputException ako bi dodavanjem predane znamenke broj postao prevelik za konaèan prikaz u tipu {@link Double}, ili ako kalkulator nije editabilan.
	 * @throws IllegalArgumentException ako je <code>digit &lt; 0</code> ili <code>digit &gt; 9</code>
	 */
	void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException;

	/**
	 * Provjera je li upisan aktivni operand.
	 * 
	 * @return <code>true</code> ako je aktivani operand upisan, <code>false</code> inaèe
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
	 * Ako kalkulator veæ ima postavljen aktivni operand, predana
	 * vrijednost ga nadjaèava.
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
	 * Postavljanje zakazane operacije. Ako zakazana operacija veæ
	 * postoji, ovaj je poziv nadjaèava predanom vrijednošæu.
	 * 
	 * @param op zakazana operacija koju treba postaviti; smije biti <code>null</code>
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
	
}
