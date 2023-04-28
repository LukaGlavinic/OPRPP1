package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel{
	
	private boolean editabilan, pozitivan;
	
	private String unos, zamrznutaVrijednost;
	
	private double broj, aktivanOperand;
	
	private DoubleBinaryOperator operacija;
	
	private List<CalcValueListener> setPromatracaZaObav;
	
	public List<CalcValueListener> getSetPromatracaZaObav() {
		return this.setPromatracaZaObav;
	}
	
	public CalcModelImpl() {
		this.zamrznutaVrijednost = null;
		this.broj = 0;
		this.unos = "";
		this.editabilan = true;
		this.pozitivan = true;
		this.aktivanOperand = Double.NaN;
		this.operacija = null;
		this.setPromatracaZaObav = new ArrayList<>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l == null) {
			throw new NullPointerException("Predani promatraè je null!");
		}
		setPromatracaZaObav.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if(l == null) {
			throw new NullPointerException("Predani promatraè je null!");
		}
		setPromatracaZaObav.remove(l);
	}

	@Override
	public double getValue() {
		if(unos.equals("")) {
			return 0.0;
		}
		return broj;
	}

	@Override
	public void setValue(double value) {
		if(Double.isNaN(value)) {
			broj = Double.NaN;
			unos = "NaN";
		}else if(Double.isInfinite(value)) {
			if(value == Double.NEGATIVE_INFINITY) {
				broj = Double.NEGATIVE_INFINITY;
				pozitivan = false;
			}else {
				broj = Double.POSITIVE_INFINITY;
				pozitivan = true;
			}
			unos = "Infinity";
		}else {
			broj = value;
			pozitivan = value >= 0;
			unos = String.valueOf(pozitivan ? value : -value);
		}
		editabilan = false;
	}

	@Override
	public boolean isEditable() {
		return editabilan;
	}

	@Override
	public void clear() {
		broj = 0;
		unos = "";
		editabilan = true;
	}

	@Override
	public void clearAll() {
		broj = 0;
		pozitivan = true;
		unos = "";
		editabilan = true;
		aktivanOperand = Double.NaN;
		operacija = null;
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editabilan) {
			throw new CalculatorInputException("Ne mogu promjeniti znak, kalkulator nije editabilan!");
		}
		pozitivan = !pozitivan;
		broj = -broj;
		zamrznutaVrijednost = null;
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editabilan || unos.contains(".") || unos.equals("")) {
			throw new CalculatorInputException("Ne mogu dodati decimalnu toèku!");
		}
		unos += ".";
		zamrznutaVrijednost = null;
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editabilan) {
			throw new CalculatorInputException("Kalkulator nije editabilan!");
		}
		String trenutniUnos = "";
		if(!pozitivan) {
			trenutniUnos += "-";
		}
		trenutniUnos += String.valueOf(unos);
		if(trenutniUnos.equals("0") || trenutniUnos.equals("-0")) {
			if(digit == 0) {
				trenutniUnos += "";
			}else {
				trenutniUnos = String.valueOf(digit);
			}
		}else {
			trenutniUnos += digit;
		}
		double dobiveniBroj;
		try {
			dobiveniBroj = Double.parseDouble(trenutniUnos);
			if(dobiveniBroj > Double.MAX_VALUE) {
				throw new CalculatorInputException("Broj prelazi granice!");
			}
		}catch(NumberFormatException e) {
			throw new CalculatorInputException("Unos se ne može izparsirati u broj!");
		}
		broj = dobiveniBroj;
		if(unos.equals("0")) {
			if(digit == 0) {
				unos += "";
			}else {
				unos = String.valueOf(digit);
			}
		}else {
			unos += digit;
		}
		zamrznutaVrijednost = null;
	}

	@Override
	public boolean isActiveOperandSet() {
		return !Double.isNaN(aktivanOperand);
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(Double.isNaN(aktivanOperand)) {
			throw new IllegalStateException("Aktivni operand nije postavljen!");
		}
		return aktivanOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		aktivanOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		aktivanOperand = Double.NaN;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return operacija;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		operacija = op;
	}

	@Override
	public String toString() {
		String s = "";
		if(zamrznutaVrijednost == null) {
			if(unos.equals("Infinity")) {
				if(!pozitivan) {
					s += "-";
				}
				s += unos;
				return s;
			}else if(unos.equals("NaN")) {
				s += unos;
				return s;
			}else if(unos.equals("")) {
				if(!pozitivan) {
					s += "-";
				}
				s += "0";
			}else {
				if(!pozitivan) {
					s += "-";
				}
				s += unos;
			}
		}else {
			s += zamrznutaVrijednost;
		}
		return s;
	}
}