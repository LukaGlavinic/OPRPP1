package hr.fer.zemris.math;

import java.util.Arrays;

public class ComplexPolynomial {

	private final Complex[] factors;

	/**
	 * constructor
	 * @param factors factor of each member of the polynomial
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = factors;
	}

	/**
	 * returns order of this polynomial; e.g. For (7+2i)z^3+2z^2+5z+1 returns 3
	 * @return the order of this polynomial
	 */
	public short order() {
		return (short)(factors.length - 1);
	}

	/**
	 * computes a new polynomial as a product of this and the argument
	 * @param p the polynomial to multiply with this
	 * @return this * p
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[factors.length + p.factors.length - 1];
        Arrays.fill(newFactors, Complex.ZERO);
		for(int i = 0; i < factors.length; i++) {
			for(int j = 0; j < p.factors.length; j++) {
				newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(p.factors[j]));
			}
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * computes a new polynomial as a product of this and the argument
	 * @param z a complex number to multiply with this
	 * @return this * z
	 */
	public ComplexPolynomial mul(Complex z) {
		for(int i = 0; i < factors.length; i++) {
			factors[i] = factors[i].multiply(z);
		}
		return new ComplexPolynomial(factors);
	}

	/**
	 * computes first derivative of this polynomial; for example, for
	 * 	(7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * @return complex polynomial representing a first derivation
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length - 1];
		for(int i = 1; i < factors.length; i++) {
			newFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * computes polynomial value at given point z
	 * @param z a complex point at which to compute value
	 * @return the polynomial value at the give point
	 */
	public Complex apply(Complex z) {
		Complex rez = Complex.ZERO;
		for(int i = 0; i < factors.length; i++) {
			rez = rez.add(factors[i].multiply(z.power(i)));
		}
		return rez;
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i = factors.length - 1; i > 0; i--) {
			s.append("(").append(factors[i].toString()).append(")*z^").append(i).append(" + ");
		}
		return s + "(" + factors[0].toString() + ")";
	}
}