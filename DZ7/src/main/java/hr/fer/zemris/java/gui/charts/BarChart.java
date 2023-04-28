package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {

	private String opisX;
	private String opisY;
	
	private List<XYValue> lista;
	
	private int yMin;
	private int yMax;
	private int razmakY;
	
	public BarChart(String opisX, String opisY, List<XYValue> lista, int yMin, int yMax, int razmakY) {
		super();
		if(yMin < 0) {
			throw new IllegalArgumentException("Minimalni y manji od 0!");
		}
		if(yMax <= yMin) {
			throw new IllegalArgumentException("Maksimalni y nije strogo veæi od minimalnog!");
		}
		for(XYValue v : lista) {
			if(v.getY() < yMin) {
				throw new IllegalArgumentException("Y elementa liste je manji od minimalnog y!");
			}
		}
		if((yMax - yMin) % razmakY != 0) {
			for(int i = razmakY + 1; i < 2 * razmakY; i++) {
				if((yMax - yMin) % i == 0) {
					this.razmakY = i;
					break;
				}
			}
		}else {
			this.razmakY = razmakY;
		}
		this.opisX = opisX;
		this.opisY = opisY;
		this.lista = lista;
		this.yMin = yMin;
		this.yMax = yMax;
	}
}