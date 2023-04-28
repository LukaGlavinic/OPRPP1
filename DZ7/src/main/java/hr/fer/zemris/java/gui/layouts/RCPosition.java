package hr.fer.zemris.java.gui.layouts;

public class RCPosition {

	private int row;
	private int column;
	
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public static RCPosition parse(String text) {
		String[] polje = text.split("\\s*,\\s*");
		int row, column;
		try {
			row = Integer.parseInt(polje[0]);
			column = Integer.parseInt(polje[1]);
			return new RCPosition(row, column);
		}catch(NumberFormatException e) {
			throw new CalcLayoutException("Ogranièenja se ne mogu parsirati!");
		}
	}

	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}