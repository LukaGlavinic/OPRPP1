package hr.fer.zemris.java.gui.calc.model;

/**
 * Model promatra�a koji je zainteresiran za dojavu o
 * promjenama vrijednosti upisane u kalkulator.
 * 
 * @author marcupic
 */
public interface CalcValueListener {
	/**
	 * Metoda koja se poziva kao rezultat promjene
	 * vrijednosti zapisane u kalkulator. 
	 * 
	 * @param model model kalkulatora u kojemu je do�lo do promjene; ne mo�e biti <code>null</code>
	 */
	void valueChanged(CalcModel model);
}