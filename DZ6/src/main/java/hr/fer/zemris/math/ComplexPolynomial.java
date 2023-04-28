package hr.fer.zemris.math;

public class ComplexPolynomial {

	private Complex[] faktori;
	// constructor
	public ComplexPolynomial(Complex ...factors) {
		this.faktori = factors;
	}
	// returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	public short order() {
		return (short)(faktori.length - 1);
	}
	// computes a new polynomial this*p
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] noviFaktori = new Complex[faktori.length + p.faktori.length - 1];
		for(int i = 0; i < noviFaktori.length; i++) {
			noviFaktori[i] = Complex.ZERO;
		}
		for(int i = 0; i < faktori.length; i++) {
			for(int j = 0; j < p.faktori.length; j++) {
				noviFaktori[i + j] = noviFaktori[i + j].add(faktori[i].multiply(p.faktori[j]));
			}
		}
		return new ComplexPolynomial(noviFaktori);
	}
	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	public ComplexPolynomial derive() {
		Complex[] noviFaktori = new Complex[faktori.length - 1];
		for(int i = 1; i < faktori.length; i++) {
			noviFaktori[i - 1] = faktori[i].multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(noviFaktori);
	}
	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex rez = Complex.ZERO;
		for(int i = 0; i < faktori.length; i++) {
			rez = rez.add(faktori[i].multiply(z.power(i)));
		}
		return rez;
	}
	@Override
	public String toString() {
		String s = "";
		for(int i = faktori.length - 1; i > 0; i--) {
			s += "(" + faktori[i].toString() + ")*z^" + i + " + ";
		}
		return s + "(" + faktori[0].toString() + ")";
	}
}