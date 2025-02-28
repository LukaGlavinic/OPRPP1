package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Newton {
	
	private static ComplexRootedPolynomial crp;

	public static void main(String[] args) {

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		int numberOfRead = 0, indexOfSample;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<String> lines = new ArrayList<>();
		String line = "";
		String prompt;
		
		while(!line.trim().equals("done")) {
			prompt = "Root " + (numberOfRead + 1) + ">";
			System.out.print(prompt);
			try {
				line = reader.readLine();
				if(line.trim().equals("done")) {
					break;
				}
				lines.add(line);
				numberOfRead++;
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		try {
			if(numberOfRead > 1) {
				Complex[] passedRoots = new Complex[lines.size()];
				double real, imaginary, sign;
				String s;
				for(int i = 0; i < lines.size(); i++) {
					s = lines.get(i);
					if(s.isBlank()) {
						throw new Exception();
					}
					s = s.trim();
					real = imaginary = 0;
					sign = 1;
					if(s.contains(" + i") || s.contains(" - i")) {
						String[] realIImag = new String[2];
						if(s.contains(" - i")) {
							sign = -1;
							indexOfSample = s.indexOf(" - i");
						}else {
							indexOfSample = s.indexOf(" + i");
						}
						realIImag[0] = s.substring(0, indexOfSample);
						realIImag[1] = s.substring(indexOfSample + 4);
						if(realIImag[1].isBlank()) {
							imaginary = 1 * sign;
						}else {
							imaginary = Double.parseDouble(realIImag[1]) * sign;
						}
						real = Double.parseDouble(realIImag[0]);
					}else if(s.contains("i")){
						if(s.startsWith("-")) {
							sign = -1;
							s = s.substring(2);
						}else {
							s = s.substring(1);
						}
						if(s.isBlank()) {
							imaginary = 1 * sign;
						}else {
							imaginary = Double.parseDouble(s) * sign;
						}
					}else {
						real = Double.parseDouble(s);
					}
					passedRoots[i] = new Complex(real, imaginary);
				}
				crp = new ComplexRootedPolynomial(new Complex(1, 0), passedRoots);
				System.out.println("Image of fractal will appear shortly. Thank you.");
				FractalViewer.show(new MojProducer());
			}else {
				System.out.println("Not enough roots...TERMINATING!");
			}
		}catch(Exception e) {
			System.out.println("The root must not be empty string and must contain only numbers and the letter 'i'!");
		}
	}
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Starting the calculation...");
			int m = 16*16*16, offset = 0, index;
			short[] data = new short[width * height];
			ComplexPolynomial cp = crp.toComplexPolynomial();

			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					double module;
					int iterationCount = 0;
					Complex zn = new Complex(cre, cim);
					do {
						Complex numerator = cp.apply(zn);
						Complex denominator = cp.derive().apply(zn);
						Complex znOld = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = zn.sub(znOld).module();
						iterationCount++;
					} while(iterationCount < m && module > 0.001);
					index = crp.indexOfClosestRootFor(zn, 0.002);
					data[offset++] = (short) (index+1);
				}
			}
			System.out.println("Calculating finished. Alerting observers - GUI!");
			observer.acceptResult(data, (short)(cp.order() + 1), requestNo);
		}
	}
}