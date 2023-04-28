package hr.fer.zemris.java.gui.charts;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class BarChartDemo extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		String staza = args[0];
		Path putanja = Paths.get(staza);
		
		try {
			BufferedReader br = Files.newBufferedReader(putanja);
			String opisX = br.readLine();
			String opisY = br.readLine();
			String tocke = br.readLine();
			String miniY = br.readLine();
			String maxiY = br.readLine();
			String razmak = br.readLine();
			
			int minY = Integer.valueOf(miniY);
			int maxY = Integer.valueOf(maxiY);
			int raz = Integer.valueOf(razmak);
			String[] poljeTocki = tocke.split("\\s*");
			List<XYValue> listaTocki = new ArrayList<>();
			for(int i = 0; i < poljeTocki.length; i++) {
				String[] xIY = poljeTocki[i].split(",");
				int x = Integer.valueOf(xIY[0]);
				int y = Integer.valueOf(xIY[1]);
				listaTocki.add(new XYValue(x, y));
			}
			int brojTocaka = listaTocki.size();
			BarChart bc = new BarChart(opisX, opisY, listaTocki, minY, maxY, raz);
			JLabel labelaPuta = new JLabel(staza);
			labelaPuta.setOpaque(true);
		}catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Greška pri èitanju datoteke!");
		}catch(PatternSyntaxException e) {
			System.out.println("Nepravilno zadan redak za toèke!");
		}catch(IllegalArgumentException e) {
			System.out.println("Krivo zadani argumenti za crtanje plota!");
		}
		
		
	}
}