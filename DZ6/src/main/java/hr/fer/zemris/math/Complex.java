package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class Complex {
	
	private double real;
	private double imaginary;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);
	
	public Complex() {}
	
	public Complex(double re, double im) {
		real = re;
		imaginary = im;
	}

	/**
	 * returns the module of this
	 * @return |this|
	 */
	public double module() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * returns the product of this and complex number c
	 * @param c the complex number to multiply this
	 * @return this * c
	 */
	public Complex multiply(Complex c) {
		return new Complex(real * c.real - imaginary * c.imaginary, real * c.imaginary + imaginary * c.real);
	}

	/**
	 * divides this with complex number c
	 * @param c denominator
	 * @return this/c
	 */
	public Complex divide(Complex c) {
		double r1 = module();
		double psi1 = Math.atan2(imaginary, real);
		double r2 = c.module();
		double psi2 = Math.atan2(c.imaginary, c.real);
		return new Complex(r1/r2 * Math.cos(psi1 - psi2), r1/r2 * Math.sin(psi1 - psi2));
	}

	/**
	 * adds complex number c to this
	 * @param c complex number to add
	 * @return this + c
	 */
	public Complex add(Complex c) {
		return new Complex(real + c.real, imaginary + c.imaginary);
	}

	/**
	 * subtracts complex number c from this
	 * @param c complex number to subtract
	 * @return this - c
	 */
	public Complex sub(Complex c) {
		return new Complex(real - c.real, imaginary - c.imaginary);
	}

	/**
	 * negates this complex number
	 * @return -this
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * method to raise the current complex number to the n-th exponent
	 * @param n non-negative integer
	 * @return this^n
	 */
	public Complex power(int n) {
		double radius = module();
		double psi = Math.atan2(imaginary, real);
		return new Complex(Math.pow(radius, n) * Math.cos(n * psi), Math.pow(radius, n) * Math.sin(n * psi));
	}

	/**
	 * returns the n-th root of this complex number
	 * @param n positive integer
	 * @return this^(1/n)
	 * @throws Exception if n is not positive
	 */
	public List<Complex> root(int n) throws Exception {
		if(n <= 0) {
			throw new Exception("Not allowed!");
		}
		double radius = module();
		double psi = Math.atan2(imaginary, real);
		List<Complex> list = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			list.add(new Complex(Math.pow(radius, (double) 1 /n) * Math.cos((psi + 2 * i * Math.PI) / n), Math.pow(radius, (double) 1 /n) * Math.sin((psi + 2 * i * Math.PI) / n)));
		}
		return list;
	}
	@Override
	public String toString() {
		double positiveIm = Math.abs(imaginary);
		String s = "" + real;
		s += imaginary >= 0 ? " + i" + imaginary : " - i" + positiveIm;
		return s;
	}
}