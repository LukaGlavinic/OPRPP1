package hr.fer.zemris.java.gui.charts;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Grafovi extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BarChart bc;
	
	public Grafovi(BarChart bc) {
		this.bc = bc;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 50);
		setSize(650, 350);
		setTitle("Grafovi");
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new FlowLayout(FlowLayout.LEFT));
		JList gumbi = new JList();
		Container grafovi = new Container();
		//gumbi.setLayout(new FlowLayout(FlowLayout.TRAILING));
		grafovi.setLayout(new GridLayout());
		
		JButton prvi = new JButton("1");
		JButton drugi = new JButton("2");
		JButton treci = new JButton("3");
		JButton cetvrti = new JButton("4");
		
		gumbi.add(prvi);
		gumbi.add(drugi);
		gumbi.add(treci);
		gumbi.add(cetvrti);
		
		cp.add(gumbi);
		cp.add(grafovi);
		
		String opisX;
		String opisY;
		List<XYValue> lista;
		int yMin;
		int yMax;
		int razmakY;
		BarChart graf = bc;
		
		BarChartComponent graf1 = new BarChartComponent(graf);
		BarChartComponent graf2 = new BarChartComponent(graf);
		BarChartComponent graf3 = new BarChartComponent(graf);
		BarChartComponent graf4 = new BarChartComponent(graf);
	}

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
			SwingUtilities.invokeLater(()->{
				new Grafovi(bc).setVisible(true);
			});
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