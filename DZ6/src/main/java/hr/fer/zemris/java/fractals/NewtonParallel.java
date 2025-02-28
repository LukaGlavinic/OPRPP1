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
		numWorkers = Runtime.getRuntime().availableProcessors();
		try {
			if(args.length == 0) {// if no arguments
				numTracks = 4 * numWorkers;
			}else {
				boolean workers = false, tracks = false, searchW = false, searchT = false;
				for(String s : args) {
					if(searchT) {
						searchT = false;
						numTracks = Integer.parseInt(s);
						if(numTracks < 1) {
							throw new Exception("Too few tracks!");
						}
						continue;
					}else if(searchW) {
						searchW = false;
						numWorkers = Integer.parseInt(s);
						continue;
					}
					if(s.startsWith("--workers") || s.startsWith("-w")) {
						if(workers) {
							throw new Exception("You may not enter number of workers again!");
						}else {
							workers = true;
							if(s.startsWith("-w")) {
								searchW = true;
								continue;
							}else {
								numWorkers = Integer.parseInt(s.substring(s.indexOf("=") + 1));
							}
						}
					}
					if(s.startsWith("--tracks") || s.startsWith("-t")) {
						if(tracks) {
							throw new Exception("You may not enter number of tracks again!");
						}else {
							tracks = true;
							if(s.startsWith("-t")) {
								searchT = true;
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
		System.out.println("Number of tracks: " + numTracks + " Number of workers: " + numWorkers);
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when you are done.");
		int numberOfRead = 0, indexOfSample;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<String> lines = new ArrayList<>();
		String line = "";
		String prompt;
		
		while(!line.trim().equals("done")) {
			prompt = "Root " + (numberOfRead + 1) + " > ";
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
				System.out.println("Too few roots... TERMINATING!");
			}
		}catch(Exception e) {
			System.out.println("The root must not be empty string and must contain only numbers and the letter 'i'!");
		}
	}

	public static class Calculation implements Runnable {
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
		public static Calculation NO_JOB = new Calculation();
		
		private Calculation() {}
		
		public Calculation(double reMin, double reMax, double imMin,
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
		}
	}
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Starting the calculation...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			if(numTracks > height) {
				numTracks = height;
			}
			final int numberOfTracks = numTracks;
			int numberYPerTrack = height / numberOfTracks;
			ComplexPolynomial cp = crp.toComplexPolynomial();
			
			final BlockingQueue<Calculation> queue = new LinkedBlockingQueue<>();

			Thread[] workers = new Thread[numWorkers];
			for(int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(() -> {
                    while(true) {
                        Calculation p;
                        try {
                            p = queue.take();
                            if(p== Calculation.NO_JOB) break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                        p.run();
                    }
                });
			}
            for (Thread thread : workers) {
                thread.start();
            }
			for(int i = 0; i < numberOfTracks; i++) {
				int yMin = i*numberYPerTrack;
				int yMax = (i + 1) * numberYPerTrack - 1;
				if(i==numberOfTracks-1) {
					yMax = height-1;
				}
				Calculation work = new Calculation(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, cp);
				while(true) {
					try {
						queue.put(work);
						break;
					} catch (InterruptedException ignored) {
					}
				}
			}
			for(int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						queue.put(Calculation.NO_JOB);
						break;
					} catch (InterruptedException ignored) {
					}
				}
			}
            for (Thread thread : workers) {
                while (true) {
                    try {
                        thread.join();
                        break;
                    } catch (InterruptedException ignored) {
                    }
                }
            }
			System.out.println("Calculating finished. Alerting observers - GUI!");
			observer.acceptResult(data, (short)(cp.order() + 1), requestNo);
		}
	}
}