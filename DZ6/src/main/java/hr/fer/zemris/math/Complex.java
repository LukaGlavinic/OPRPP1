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
		this.real = re;
		this.imaginary = im;
	}
	// returns module of complex number
	public double module() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}
	// returns this*c
	public Complex multiply(Complex c) {
		return new Complex(real * c.real - imaginary * c.imaginary, real * c.imaginary + imaginary * c.real);
	}
	// returns this/c
	public Complex divide(Complex c) {
		double r1 = this.module();
		double psi1 = Math.atan2(imaginary, real);
		double r2 = c.module();
		double psi2 = Math.atan2(c.imaginary, c.real);
		return new Complex(r1/r2 * Math.cos(psi1 - psi2), r1/r2 * Math.sin(psi1 - psi2));
	}
	// returns this+c
	public Complex add(Complex c) {
		return new Complex(real + c.real, imaginary + c.imaginary);
	}
	// returns this-c
	public Complex sub(Complex c) {
		return new Complex(real - c.real, imaginary - c.imaginary);
	}
	// returns -this
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}
	// returns this^n, n is non-negative integer
	public Complex power(int n) {
		double radius = this.module();
		double psi = Math.atan2(imaginary, real);
		return new Complex(Math.pow(radius, n) * Math.cos(n * psi), Math.pow(radius, n) * Math.sin(n * psi));
	}
	// returns n-th root of this, n is positive integer
	public List<Complex> root(int n) throws Exception {
		if(n <= 0) {
			throw new Exception("Nije dozvoljeno tako korjenovati!");
		}
		double radius = this.module();
		double psi = Math.atan2(imaginary, real);
		List<Complex> lista = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			lista.add(new Complex(Math.pow(radius, 1/n) * Math.cos((psi + 2 * i * Math.PI) / n), Math.pow(radius, 1/n) * Math.sin((psi + 2 * i * Math.PI) / n)));
		}
		return lista;
	}
	@Override
	public String toString() {
		double pozitIm = Math.abs(imaginary);
		String s = "" + this.real;
		s += imaginary >= 0 ? " + i" + imaginary : " - i" + pozitIm;
		return s;
	}
}