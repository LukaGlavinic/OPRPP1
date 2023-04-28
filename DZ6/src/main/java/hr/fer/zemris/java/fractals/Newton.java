package hr.fer.zemris.java.fractals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {
	
	private static ComplexRootedPolynomial crp;

	public static void main(String[] args) {

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		int brojProcitanih = 0, indexUzorka;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<String> listaLinija = new ArrayList<>();
		String linija = "";
		String prompt;
		
		while(!linija.trim().equals("done")) {
			prompt = "Root " + (brojProcitanih + 1) + ">";
			System.out.print(prompt);
			try {
				linija = reader.readLine();
				if(linija.trim().equals("done")) {
					break;
				}
				listaLinija.add(linija);
				brojProcitanih++;
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		try {
			if(brojProcitanih > 1) {
				Complex[] predaniKorijeni = new Complex[listaLinija.size()];
				double realni, imag, predznak;
				String s;
				for(int i = 0; i < listaLinija.size(); i++) {
					s = listaLinija.get(i);
					if(s.isBlank()) {
						throw new Exception();
					}
					s = s.trim();
					realni = imag = 0;
					predznak = 1;
					if(s.contains(" + i") || s.contains(" - i")) {
						String[] realIImag = new String[2];
						if(s.contains(" - i")) {
							predznak = -1;
							indexUzorka = s.indexOf(" - i");
						}else {
							indexUzorka = s.indexOf(" + i");
						}
						realIImag[0] = s.substring(0, indexUzorka);
						realIImag[1] = s.substring(indexUzorka + 4);
						if(realIImag[1].isBlank()) {
							imag = 1 * predznak;
						}else {
							imag = Double.parseDouble(realIImag[1]) * predznak;
						}
						realni = Double.parseDouble(realIImag[0]);
					}else if(s.contains("i")){
						if(s.startsWith("-")) {
							predznak = -1;
							s = s.substring(2);
						}else {
							s = s.substring(1);
						}
						if(s.isBlank()) {
							imag = 1 * predznak;
						}else {
							imag = Double.parseDouble(s) * predznak;
						}
					}else {
						realni = Double.parseDouble(s);
					}
					predaniKorijeni[i] = new Complex(realni, imag);
				}
				crp = new ComplexRootedPolynomial(new Complex(1, 0), predaniKorijeni);
				System.out.println("Image of fractal will appear shortly. Thank you.");
				FractalViewer.show(new MojProducer());
			}else {
				System.out.println("Nedovoljno korijena... TERMINIRAM!");
			}
		}catch(Exception e) {
			System.out.println("Korijen ne smije biti prazan unos i smije sadržavati samo brojeve i slovo 'i'!");
		}
	}
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16, offset = 0, index;
			short[] data = new short[width * height];
			ComplexPolynomial cp = crp.toComplexPolynom();

			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					double module;
					int iters = 0;
					Complex zn = new Complex(cre, cim);
					do {
						Complex numerator = cp.apply(zn);
						Complex denominator = cp.derive().apply(zn);
						Complex znOld = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = zn.sub(znOld).module();
						iters++;
					} while(iters < m && module > 0.001);
					index = crp.indexOfClosestRootFor(zn, 0.002);
					data[offset++] = (short) (index+1);
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(cp.order() + 1), requestNo);
		}
	}
}