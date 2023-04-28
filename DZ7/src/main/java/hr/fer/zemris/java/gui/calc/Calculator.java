package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private CalcModelImpl kalkulator;
	private List<Double> stog;
	private boolean inverzan, unosDrugogOperanda;
	
	public Calculator() {
		this.inverzan = false;
		this.kalkulator = new CalcModelImpl();
		this.stog = new LinkedList<>();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 50);
		setSize(650, 350);
		setTitle("Java Calculator v1.0");
		initGUI();
	}
	
	class Plus implements DoubleBinaryOperator{
		@Override
		public double applyAsDouble(double left, double right) {
			return left + right;
		}
	}
	
	class Minus implements DoubleBinaryOperator{
		@Override
		public double applyAsDouble(double left, double right) {
			return left - right;
		}
	}
	
	class Puta implements DoubleBinaryOperator{
		@Override
		public double applyAsDouble(double left, double right) {
			return left * right;
		}
	}
	
	class Podjeljeno implements DoubleBinaryOperator{
		@Override
		public double applyAsDouble(double left, double right) {
			return left / right;
		}
	}
	
	class XnaN implements DoubleBinaryOperator{
		@Override
		public double applyAsDouble(double left, double right) {
			return Math.pow(left, right);
		}
	}
	
	class Korijen implements DoubleBinaryOperator{
		@Override
		public double applyAsDouble(double left, double right) {
			return Math.pow(left, 1 / right);
		}
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(2));
		
		JLabel labelaKalkulatora = ekran(kalkulator.toString());
		
		//brojke
		ArrayList<JButton> listaGumbiBrojeva = new ArrayList<>();
		JButton t0= new JButton("0");
		listaGumbiBrojeva.add(t0);
		JButton t1 = new JButton("1");
		listaGumbiBrojeva.add(t1);
		JButton t2 = new JButton("2");
		listaGumbiBrojeva.add(t2);
		JButton t3 = new JButton("3");
		listaGumbiBrojeva.add(t3);
		JButton t4 = new JButton("4");
		listaGumbiBrojeva.add(t4);
		JButton t5 = new JButton("5");
		listaGumbiBrojeva.add(t5);
		JButton t6 = new JButton("6");
		listaGumbiBrojeva.add(t6);
		JButton t7 = new JButton("7");
		listaGumbiBrojeva.add(t7);
		JButton t8 = new JButton("8");
		listaGumbiBrojeva.add(t8);
		JButton t9 = new JButton("9");
		listaGumbiBrojeva.add(t9);
		for(JButton b : listaGumbiBrojeva) {
			b.addActionListener(e -> {
				try {
					if(unosDrugogOperanda) {
						kalkulator.clear();
						unosDrugogOperanda = false;
					}
					kalkulator.insertDigit(Integer.valueOf(b.getText()));
					labelaKalkulatora.setText(kalkulator.toString());
				}catch(CalculatorInputException ex) {
					System.out.println(ex.getMessage());
				}
			});
		}
		cp.add(labelaKalkulatora, new RCPosition(1,1));
		cp.add(t0, new RCPosition(5,3));
		cp.add(t1, new RCPosition(4,3));
		cp.add(t2, new RCPosition(4,4));
		cp.add(t3, new RCPosition(4,5));
		cp.add(t4, new RCPosition(3,3));
		cp.add(t5, new RCPosition(3,4));
		cp.add(t6, new RCPosition(3,5));
		cp.add(t7, new RCPosition(2,3));
		cp.add(t8, new RCPosition(2,4));
		cp.add(t9, new RCPosition(2,5));
		
		//funkcije s lijeva
		JButton f0= new JButton("x^n");
		f0.addActionListener(e -> {
			try {
				if(kalkulator.getPendingBinaryOperation() != null) {
					throw new CalculatorInputException("Vec je postavljena operacija!");
				}else {
					kalkulator.setActiveOperand(kalkulator.getValue());
					kalkulator.setPendingBinaryOperation(!inverzan ? new XnaN() : new Korijen());
					unosDrugogOperanda = true;
				}
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		JButton f1 = new JButton("ctg");
		f1.addActionListener(e -> {
			try {
				kalkulator.setValue(!inverzan ? 1 / Math.tan(kalkulator.getValue()) : Math.atan(1 / Math.tan(kalkulator.getValue())));
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		JButton f2 = new JButton("ln");
		f2.addActionListener(e -> {
			try {
				kalkulator.setValue(!inverzan ? Math.log(kalkulator.getValue()) : Math.pow(Math.E, kalkulator.getValue()));
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		JButton f3 = new JButton("tan");
		f3.addActionListener(e -> {
			try {
				kalkulator.setValue(!inverzan ? Math.tan(kalkulator.getValue()) : Math.atan(kalkulator.getValue()));
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		JButton f4 = new JButton("log");
		f4.addActionListener(e -> {
			try {
				kalkulator.setValue(!inverzan ? Math.log10(kalkulator.getValue()) : Math.pow(10, kalkulator.getValue()));
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		JButton f5 = new JButton("cos");
		f5.addActionListener(e -> {
			try {
				kalkulator.setValue(!inverzan ? Math.cos(kalkulator.getValue()) : Math.acos(kalkulator.getValue()));
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		JButton f6 = new JButton("1/x");
		f6.addActionListener(e -> {
			try {
				kalkulator.setValue(1 / kalkulator.getValue());
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		JButton f7 = new JButton("sin");
		f7.addActionListener(e -> {
			try {
				kalkulator.setValue(!inverzan ? Math.sin(kalkulator.getValue()) : Math.asin(kalkulator.getValue()));
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		cp.add(f0, new RCPosition(5,1));
		cp.add(f1, new RCPosition(5,2));
		cp.add(f2, new RCPosition(4,1));
		cp.add(f3, new RCPosition(4,2));
		cp.add(f4, new RCPosition(3,1));
		cp.add(f5, new RCPosition(3,2));
		cp.add(f6, new RCPosition(2,1));
		cp.add(f7, new RCPosition(2,2));
		
		//jednakost
		JButton jednako = new JButton("=");
		jednako.addActionListener(e -> {
			try {
				double prviOperand = kalkulator.getActiveOperand();
				DoubleBinaryOperator operacija = kalkulator.getPendingBinaryOperation();
				if(operacija != null) {
					kalkulator.setValue(operacija.applyAsDouble(prviOperand, kalkulator.getValue()));
					kalkulator.clearActiveOperand();
					kalkulator.setPendingBinaryOperation(null);
				}
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}catch(IllegalStateException ignore) {}
		});
		cp.add(jednako, new RCPosition(1,6));
		
		//decimalna tocka
		JButton decTocka = new JButton(".");
		decTocka.addActionListener(e -> {
			try {
				kalkulator.insertDecimalPoint();
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		cp.add(decTocka, new RCPosition(5,5));
		
		//promjena predznaka
		JButton promPredz = new JButton("+/-");
		promPredz.addActionListener(e -> {
			try {
				kalkulator.swapSign();
				/*if(!unosDrugogOperanda) {
					kalkulator.setValue(kalkulator.getValue());
				}*/
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		cp.add(promPredz, new RCPosition(5,4));
		
		//operacije zbrajanja oduzimanja mnozenja dijeljenje
		JButton o0= new JButton("+");
		o0.addActionListener(e -> {
			try {
				if(kalkulator.getPendingBinaryOperation() != null && unosDrugogOperanda) {
					throw new CalculatorInputException("Vec je postavljena operacija!");
				}
				if(kalkulator.getPendingBinaryOperation() != null && !unosDrugogOperanda) {
					jednako.doClick();
				}
				kalkulator.setActiveOperand(kalkulator.getValue());
				kalkulator.setPendingBinaryOperation(new Plus());
				unosDrugogOperanda = true;
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		JButton o1 = new JButton("-");
		o1.addActionListener(e -> {
			try {
				if(kalkulator.getPendingBinaryOperation() != null && unosDrugogOperanda) {
					throw new CalculatorInputException("Vec je postavljena operacija!");
				}
				if(kalkulator.getPendingBinaryOperation() != null && !unosDrugogOperanda) {
					jednako.doClick();
				}
				kalkulator.setActiveOperand(kalkulator.getValue());
				kalkulator.setPendingBinaryOperation(new Minus());
				unosDrugogOperanda = true;
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		JButton o2 = new JButton("*");
		o2.addActionListener(e -> {
			try {
				if(kalkulator.getPendingBinaryOperation() != null && unosDrugogOperanda) {
					throw new CalculatorInputException("Vec je postavljena operacija!");
				}
				if(kalkulator.getPendingBinaryOperation() != null && !unosDrugogOperanda) {
					jednako.doClick();
				}
				kalkulator.setActiveOperand(kalkulator.getValue());
				kalkulator.setPendingBinaryOperation(new Puta());
				unosDrugogOperanda = true;
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		JButton o3 = new JButton("/");
		o3.addActionListener(e -> {
			try {
				if(kalkulator.getPendingBinaryOperation() != null && unosDrugogOperanda) {
					throw new CalculatorInputException("Vec je postavljena operacija!");
				}
				if(kalkulator.getPendingBinaryOperation() != null && !unosDrugogOperanda) {
					jednako.doClick();
				}
				kalkulator.setActiveOperand(kalkulator.getValue());
				kalkulator.setPendingBinaryOperation(new Podjeljeno());
				unosDrugogOperanda = true;
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		
		cp.add(o0, new RCPosition(5,6));
		cp.add(o1, new RCPosition(4,6));
		cp.add(o2, new RCPosition(3,6));
		cp.add(o3, new RCPosition(2,6));
		
		//funkcije na desnom rubu
		JButton clr = new JButton("clr");
		clr.addActionListener(e -> {
			try {
				kalkulator.clear();
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		cp.add(clr, new RCPosition(1,7));
		
		JButton push = new JButton("push");
		push.addActionListener(e -> {
			try {
				stog.add(kalkulator.getValue());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		cp.add(push, new RCPosition(3,7));
		
		JButton pop = new JButton("pop");
		pop.addActionListener(e -> {
			try {
				if(stog.size() > 0) {
					double saStoga = stog.remove(stog.size() - 1);
					kalkulator.setValue(saStoga);
					labelaKalkulatora.setText(kalkulator.toString());
				}else {
					System.out.println("Stog je prazan!");
				}
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		cp.add(pop, new RCPosition(4,7));
		
		JCheckBox Inv = new JCheckBox("Inv");
		Inv.setSelected(false);
		Inv.addActionListener(e -> {
			inverzan = !inverzan;
			if(inverzan) {
				Inv.setSelected(true);
			}else {
				Inv.setSelected(false);
			}
		});
		cp.add(Inv, new RCPosition(5,7));
		
		JButton res = new JButton("res");
		res.addActionListener(e -> {
			try {
				kalkulator.clearAll();
				stog.clear();
				inverzan = false;
				Inv.setSelected(false);
				labelaKalkulatora.setText(kalkulator.toString());
			}catch(CalculatorInputException ex) {
				System.out.println(ex.getMessage());
			}
		});
		cp.add(res, new RCPosition(2,7));
	}

	private JLabel ekran(String text) {
		JLabel l = new JLabel(text, SwingConstants.RIGHT);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
}