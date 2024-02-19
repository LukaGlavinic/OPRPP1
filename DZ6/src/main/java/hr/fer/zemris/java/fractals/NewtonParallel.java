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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class NewtonParallel {
	
	private static ComplexRootedPolynomial crp;
	private static int numWorkers;
	private static int numTracks;
	
	public static void main(String[] args) {
		
		for(String s : args) {//ispis provjera
			System.out.println(s);
		}
		
		numWorkers = Runtime.getRuntime().availableProcessors();
		try {
			if(args.length == 0) {//ako nema argumenata
				numTracks = 4 * numWorkers;
			}else {
				boolean workeri = false, trackovi = false, traziseW = false, traziseT = false;
				for(String s : args) {
					if(traziseT) {
						traziseT = false;
						numTracks = Integer.parseInt(s);
						if(numTracks < 1) {
							throw new Exception("Premali zadani broj tracks!");
						}
						continue;
					}else if(traziseW) {
						traziseW = false;
						numWorkers = Integer.parseInt(s);
						continue;
					}
					if(s.startsWith("--workers") || s.startsWith("-w")) {
						if(workeri) {
							throw new Exception("Ne smije se opet zadavati workers!");
						}else {
							workeri = true;
							if(s.startsWith("-w")) {
								traziseW = true;
								continue;
							}else {
								numWorkers = Integer.parseInt(s.substring(s.indexOf("=") + 1));
							}
						}
					}
					if(s.startsWith("--tracks") || s.startsWith("-t")) {
						if(trackovi) {
							throw new Exception("Ne smije se opet zadavati tracks!");
						}else {
							trackovi = true;
							if(s.startsWith("-t")) {
								traziseT = true;
                            }else {
								numTracks = Integer.parseInt(s.substring(s.indexOf("=") + 1));
							}
						}
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		System.out.println("Broj tracks: " + numTracks + " Broj workers: " + numWorkers);//ispis provjera
		
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

	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		ComplexPolynomial cp;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel, ComplexPolynomial cp) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.cp = cp;
		}
		
		@Override
		public void run() {
			int offset = yMin * width, index;
			for(int y = yMin; y <= yMax; y++) {
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
		}
	}
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			if(numTracks > height) {
				numTracks = height;
			}
			final int brojTraka = numTracks;
			int brojYPoTraci = height / brojTraka;
			ComplexPolynomial cp = crp.toComplexPolynom();
			
			final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[numWorkers];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(() -> {
                    while(true) {
                        PosaoIzracuna p;
                        try {
                            p = queue.take();
                            if(p==PosaoIzracuna.NO_JOB) break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                        p.run();
                    }
                });
			}
            for (Thread thread : radnici) {
                thread.start();
            }
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, cp);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException ignored) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(PosaoIzracuna.NO_JOB);
						break;
					} catch (InterruptedException ignored) {
					}
				}
			}

            for (Thread thread : radnici) {
                while (true) {
                    try {
                        thread.join();
                        break;
                    } catch (InterruptedException ignored) {
                    }
                }
            }
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(cp.order() + 1), requestNo);
		}
	}
}