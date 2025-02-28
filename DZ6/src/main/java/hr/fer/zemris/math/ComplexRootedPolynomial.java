package hr.fer.zemris.math;

public class ComplexRootedPolynomial {

	private final Complex constant;
	private final Complex[] roots;

	/**
	 *
	 * @param constant complex number that multiplies the polynomial
	 * @param roots root of each member of the polynomial
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}

	/**
	 * computes polynomial value at given point z
	 * @param z a complex point at which to compute value
	 * @return the polynomial value at the give point
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
        for (Complex root : roots) {
            result = result.multiply(z.sub(root));
        }
		return result;
	}

	/**
	 * converts this representation to ComplexPolynomial type
	 * @return a ComplexPolynomial representing the current ComplexRootedPolynomial
	 */
	public ComplexPolynomial toComplexPolynomial() {
		Complex[] factors = {Complex.ONE};
		ComplexPolynomial result = new ComplexPolynomial(factors);
		for (Complex root : roots) {
			Complex[] factors2 = {root.negate(), Complex.ONE};
			ComplexPolynomial factor = new ComplexPolynomial(factors2);
			result = result.multiply(factor);
		}
		return result.mul(constant);
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("(" + constant.toString() + ")");
        for (Complex complex : roots) {
            s.append("*(z - (").append(complex.toString()).append("))");
        }
		return s.toString();
	}

	/**
	 * finds index of closest root for given complex number z that is within first root has index 0, second index 1, etc
	 * @param z complex number to find the index of the closest root to
	 * @param threshold if there is no such root, returns -1
	 * @return index of the closest root for the given complex number
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		int index = -1;
		double distance = Double.MAX_VALUE, module;
		Complex difference;
		for(int i = 0; i < roots.length; i++) {
			difference = z.sub(roots[i]);
			module = difference.module();
			if(module <= threshold && module < distance) {
				index = i;
				distance = module;
			}
		}
		if(index == -1) {
			return 0;
		}
		return index;
	}
}