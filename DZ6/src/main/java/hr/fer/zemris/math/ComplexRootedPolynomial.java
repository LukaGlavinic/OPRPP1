package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class ComplexRootedPolynomial {
	
	private List<Complex> prolaziKrozPolje(int kolikiPribrojnik) {
		List<Complex> noviKoeficijenti = new ArrayList<>();
		List<int[]> combinations = new ArrayList<>();
    	int[] comb = new int[kolikiPribrojnik];
    	for (int i = 1; i < kolikiPribrojnik; i++) {
        	comb[i] = i;
    	}
    	while (comb[kolikiPribrojnik - 1] < poljeKorijena.length) {
        	combinations.add(comb.clone());

        	int t = kolikiPribrojnik - 1;
        	while (t != 0 && comb[t] == poljeKorijena.length - kolikiPribrojnik + t) {
            	t--;
        	}
        	comb[t]++;
        	for (int i = t + 1; i < kolikiPribrojnik; i++) {
            	comb[i] = comb[i - 1] + 1;
        	}
    	}
    	for(int i = 0; i < combinations.size(); i++) {
    		int[] poljeIndexa = combinations.get(i);
    		Complex konst = Complex.ONE;
    		for(int j = 0; j < poljeIndexa.length; j++) {
    			konst = konst.multiply(poljeKorijena[poljeIndexa[j]]);
    		}
    		noviKoeficijenti.add(konst);
    	}
		return noviKoeficijenti;
	}

	private Complex konstanta;
	private Complex[] poljeKorijena;
	// constructor
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.konstanta = constant;
		this.poljeKorijena = roots;
	}
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex rez = konstanta;
		for(int i = 0; i < poljeKorijena.length; i++) {
			rez = rez.multiply(z.sub(poljeKorijena[i]));
		}
		return rez;
	}
	// converts this representation to ComplexPolynomial type
	public ComplexPolynomial toComplexPolynom() {
		Complex[] poljeFaktora = new Complex[poljeKorijena.length + 1];
		List<Complex> koeficijentiZaZbrojit;
		Complex predznak = poljeKorijena.length % 2 == 0 ? Complex.ONE : Complex.ONE_NEG, zbroj;
		poljeFaktora[0] = predznak;
		for(int i = 0; i < poljeKorijena.length; i++) {
			poljeFaktora[0] = poljeFaktora[0].multiply(poljeKorijena[i]);
		}
		predznak = predznak.negate();
		for(int i = 1; i < poljeFaktora.length - 1; i++) {
			koeficijentiZaZbrojit = prolaziKrozPolje(poljeKorijena.length - i);
			zbroj = Complex.ZERO;
			for(int j = 0; j < koeficijentiZaZbrojit.size(); j++) {
				zbroj = zbroj.add(koeficijentiZaZbrojit.get(j));
			}
			poljeFaktora[i] = zbroj.multiply(predznak);
			predznak = predznak.negate();
		}
		poljeFaktora[poljeFaktora.length - 1] = Complex.ONE;
		for(int i = 0; i < poljeFaktora.length; i++) {
			poljeFaktora[i] = poljeFaktora[i].multiply(konstanta);
		}
		return new ComplexPolynomial(poljeFaktora);
	}
	@Override
	public String toString() {
		String s = "" + "(" + konstanta.toString() + ")";
		for(int i = 0; i < poljeKorijena.length; i++) {
			s += "*(z - (" + poljeKorijena[i].toString() + "))";
		}
		return s;
	}
	// finds index of closest root for given complex number z that is within
	// treshold; if there is no such root, returns -1
	// first root has index 0, second index 1, etc
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double udalj = Double.MAX_VALUE, modul;
		Complex razlika;
		for(int i = 0; i < poljeKorijena.length; i++) {
			razlika = z.sub(poljeKorijena[i]);
			modul = razlika.module();
			if(modul <= treshold && modul < udalj) {
				index = i;
				udalj = modul;
			}
		}
		if(index == -1) {
			return 0;
		}
		return index;
	}
}