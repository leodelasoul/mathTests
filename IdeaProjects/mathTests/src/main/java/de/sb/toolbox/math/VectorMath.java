package de.sb.toolbox.math;

import de.sb.toolbox.Copyright;
import de.sb.toolbox.math.FunctionTables.SwapEntry;
import de.sb.toolbox.util.ArrayCopy;


/**
 * This facade adds additional mathematical operations for vector arithmetics. Note that this class
 * is declared final because it is a facade, and therefore not supposed to be extended. Also note
 * that most operations will likely be inlined by any JVM.
 */
@Copyright(year = 2013, holders = "Sascha Baumeister")
public final class VectorMath {

	/**
	 * {@code Normalizes} the given vector so it's absolute value (the Euclidean norm) becomes
	 * {@code 1}.
	 * @param vector the operand vector
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalArgumentException if the given argument has no non-zero elements, or if it
	 *         contains infinite or NaN elements
	 */
	static public void norm (final float[] vector) throws NullPointerException, IllegalArgumentException {
		final float abs = abs(vector);
		if (abs == 0 | Float.isInfinite(abs) | Float.isNaN(abs)) throw new IllegalArgumentException();

		mul(vector, 1 / abs);
	}


	/**
	 * {@code Normalizes} the given vector so it's absolute value (the Euclidean norm) becomes
	 * {@code 1}.
	 * @param vector the operand vector
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalArgumentException if the given argument has no non-zero elements, or if it
	 *         contains infinite or NaN elements
	 */
	static public void norm (final double[] vector) throws NullPointerException, IllegalArgumentException {
		final double abs = abs(vector);
		if (abs == 0 | Double.isInfinite(abs) | Double.isNaN(abs)) throw new IllegalArgumentException();

		mul(vector, 1 / abs);
	}


	/**
	 * Returns the {@code absolute value} (the Euclidean norm) of the given vector.
	 * @param vector the operand vector
	 * @throws NullPointerException if the given argument is {@code null}
	 */
	static public float abs (final float[] vector) throws NullPointerException {
		return (float) Math.sqrt(mul(vector, vector));
	}


	/**
	 * Returns the {@code absolute value} (the Euclidean norm) of the given vector.
	 * @param vector the operand vector
	 * @throws NullPointerException if the given argument is {@code null}
	 */
	static public double abs (final double[] vector) throws NullPointerException {
		return Math.sqrt(mul(vector, vector));
	}


	/**
	 * Returns the {@code argument angle} between the given vectors.
	 * @param left the left vector operand
	 * @param right the right vector operand
	 * @return the angle in radians within range <tt>[-&pi;,&pi;[</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given arguments do not share the same length
	 */
	static public float arg (final float[] left, final float[] right) throws NullPointerException, IllegalArgumentException {
		final float dotProduct = mul(left, right);
		final float absProduct = abs(left) * abs(right);
		final float arg = (float) Math.acos(dotProduct / absProduct);
		return arg < .5f * (float) Math.PI ? arg : arg - (float) Math.PI;
	}


	/**
	 * Returns the {@code argument angle} between the given vectors.
	 * @param left the left vector operand
	 * @param right the right vector operand
	 * @return the angle in radians within range <tt>[-&pi;,&pi;[</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given arguments do not share the same length
	 */
	static public double arg (final double[] left, final double[] right) throws NullPointerException, IllegalArgumentException {
		final double dotProduct = mul(left, right);
		final double absProduct = abs(left) * abs(right);
		final double arg = Math.acos(dotProduct / absProduct);
		return arg < .5d * Math.PI ? arg : arg - Math.PI;
	}


	/**
	 * {@code Adds} the right vector to the left vector, and assigns the result to the latter.
	 * @param left the left vector operand
	 * @param right the right vector operand
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given arguments do not share the same length
	 */
	static public void add (final float[] left, final float[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length) throw new IllegalArgumentException();

		for (int index = 0; index < left.length; ++index) {
			left[index] += right[index];
		}
	}


	/**
	 * {@code Adds} the right vector to the left vector, and assigns the result to the latter.
	 * @param left the left vector operand
	 * @param right the right vector operand
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given arguments do not share the same length
	 */
	static public void add (final double[] left, final double[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length) throw new IllegalArgumentException();

		for (int index = 0; index < left.length; ++index) {
			left[index] += right[index];
		}
	}


	/**
	 * {@code Subtracts} the right vector from the left vector, and assigns the result to the
	 * latter.
	 * @param left the left vector operand
	 * @param right the right vector operand
	 * @throws NullPointerException if any of the given vectors is {@code null}
	 * @throws IllegalArgumentException if the two vectors do not share the same length
	 */
	static public void sub (final float[] left, final float[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length) throw new IllegalArgumentException();

		for (int index = 0; index < left.length; ++index) {
			left[index] -= right[index];
		}
	}


	/**
	 * {@code Subtracts} the right vector from the left vector, and assigns the result to the
	 * latter.
	 * @param left the left vector operand
	 * @param right the right vector operand
	 * @throws NullPointerException if any of the given vectors is {@code null}
	 * @throws IllegalArgumentException if the two vectors do not share the same length
	 */
	static public void sub (final double[] left, final double[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length) throw new IllegalArgumentException();

		for (int index = 0; index < left.length; ++index) {
			left[index] -= right[index];
		}
	}


	/**
	 * {@code Scalar-multiplies} the given given value with the given vector, and assigns the result
	 * to the latter.
	 * @param left the left vector operand
	 * @param right the right scalar operand
	 * @throws NullPointerException if the given vector is {@code null}
	 */
	static public void mul (final float[] left, final float right) throws NullPointerException {
		for (int index = 0; index < left.length; ++index) {
			left[index] *= right;
		}
	}


	/**
	 * {@code Scalar-multiplies} the given given value with the given vector, and assigns the result
	 * to the latter.
	 * @param left the left vector operand
	 * @param right the right scalar operand
	 * @throws NullPointerException if the given vector is {@code null}
	 */
	static public void mul (final double[] left, final double right) throws NullPointerException {
		for (int index = 0; index < left.length; ++index) {
			left[index] *= right;
		}
	}


	/**
	 * Returns the result of {@code dot-multiplying} the left vector with the right vector.
	 * @param left the left vector operand
	 * @param right the right vector operand
	 * @return the {@code dot-product} of both vectors
	 * @throws NullPointerException if any of the given vectors is {@code null}
	 * @throws IllegalArgumentException if the two vectors do not share the same length
	 */
	static public float mul (final float[] left, final float[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length) throw new IllegalArgumentException();

		float result = 0;
		for (int index = 0; index < left.length; ++index) {
			result += left[index] * right[index];
		}
		return result;
	}


	/**
	 * Returns the result of {@code dot-multiplying} the left vector with the right vector.
	 * @param left the left vector operand
	 * @param right the right vector operand
	 * @return the {@code dot-product} of both vectors
	 * @throws NullPointerException if any of the given vectors is {@code null}
	 * @throws IllegalArgumentException if the two vectors do not share the same length
	 */
	static public double mul (final double[] left, final double[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length) throw new IllegalArgumentException();

		double result = 0;
		for (int index = 0; index < left.length; ++index) {
			result += left[index] * right[index];
		}
		return result;
	}


	/**
	 * Returns the result of {@code cross-multiplying} the left vector with the right vector.
	 * @param left the left vector operand
	 * @param right the right vector operand
	 * @return the {@code cross-product} of both vectors, as an array of the same length
	 * @throws NullPointerException if any of the given vectors is {@code null}
	 * @throws IllegalArgumentException if the two vectors do not share the same length, or the
	 *         length is neither {@code 3} nor {@code 7}
	 */
	static public float[] cross (final float[] left, final float[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length) throw new IllegalArgumentException();

		switch (left.length) {
			case 3:
				// r[i] = [(i+1) % 3] x [(i+2) % 3]
				return new float[] {
					left[1] * right[2] - left[2] * right[1],
					left[2] * right[0] - left[0] * right[2],
					left[0] * right[1] - left[1] * right[0]
				};
			case 7:
				// r[i] = [(i+1) % 7] x [(i+3) % 7] + [(i+2) % 7] x [(i+6) % 7] + [(i+4) % 7] x [(i+5) % 7]
				return new float[] {
					left[1] * right[3] - left[3] * right[1] + left[2] * right[6] - left[6] * right[2] + left[4] * right[5] - left[5] * right[4],
					left[2] * right[4] - left[4] * right[2] + left[3] * right[0] - left[0] * right[3] + left[5] * right[6] - left[6] * right[5],
					left[3] * right[5] - left[5] * right[3] + left[4] * right[1] - left[1] * right[4] + left[6] * right[0] - left[0] * right[6],
					left[4] * right[6] - left[6] * right[4] + left[5] * right[2] - left[2] * right[5] + left[0] * right[1] - left[1] * right[0],
					left[5] * right[0] - left[0] * right[5] + left[6] * right[3] - left[3] * right[6] + left[1] * right[2] - left[2] * right[1],
					left[6] * right[1] - left[1] * right[6] + left[0] * right[4] - left[4] * right[0] + left[2] * right[3] - left[3] * right[2],
					left[0] * right[2] - left[2] * right[0] + left[1] * right[5] - left[5] * right[1] + left[3] * right[4] - left[4] * right[3]
				};
			default:
				throw new IllegalArgumentException();
		}
	}


	/**
	 * Returns the result of {@code cross-multiplying} the left vector with the right vector.
	 * @param left the left vector operand
	 * @param right the right vector operand
	 * @return the {@code cross-product} of both vectors, as an array of the same length
	 * @throws NullPointerException if any of the given vectors is {@code null}
	 * @throws IllegalArgumentException if the two vectors do not share the same length, or the
	 *         length is neither {@code 3} nor {@code 7}
	 */
	static public double[] cross (final double[] left, final double[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length) throw new IllegalArgumentException();

		switch (left.length) {
			case 3:
				// r[i] = [(i+1) % 3] x [(i+2) % 3]
				return new double[] {
					left[1] * right[2] - left[2] * right[1],
					left[2] * right[0] - left[0] * right[2],
					left[0] * right[1] - left[1] * right[0]
				};
			case 7:
				// r[i] = [(i+1) % 7] x [(i+3) % 7] + [(i+2) % 7] x [(i+6) % 7] + [(i+4) % 7] x [(i+5) % 7]
				return new double[] {
					left[1] * right[3] - left[3] * right[1] + left[2] * right[6] - left[6] * right[2] + left[4] * right[5] - left[5] * right[4],
					left[2] * right[4] - left[4] * right[2] + left[3] * right[0] - left[0] * right[3] + left[5] * right[6] - left[6] * right[5],
					left[3] * right[5] - left[5] * right[3] + left[4] * right[1] - left[1] * right[4] + left[6] * right[0] - left[0] * right[6],
					left[4] * right[6] - left[6] * right[4] + left[5] * right[2] - left[2] * right[5] + left[0] * right[1] - left[1] * right[0],
					left[5] * right[0] - left[0] * right[5] + left[6] * right[3] - left[3] * right[6] + left[1] * right[2] - left[2] * right[1],
					left[6] * right[1] - left[1] * right[6] + left[0] * right[4] - left[4] * right[0] + left[2] * right[3] - left[3] * right[2],
					left[0] * right[2] - left[2] * right[0] + left[1] * right[5] - left[5] * right[1] + left[3] * right[4] - left[4] * right[3]
				};
			default:
				throw new IllegalArgumentException();
		}
	}


	//**********************//
	// transform operations //
	//**********************//

	/**
	 * This operation performs a {@code fast fourier transform} on the given vector of braided real
	 * and imaginary parts, using an optimized {@code Cooley-Tukey} based algorithm. Only vectors of
	 * two times power two length {@code 2*N} with <tt>magnitude = log<sub>2</sub>(N)</tt> are
	 * supported; this implementation renormalizes the values using factor
	 * <tt>N<sup>-&half;</sup> = 2<sup>-&half;magnitude</sup></tt>.
	 * @param inverse whether or not an {@code inverse} fourier transform shall be performed
	 * @param separate whether or not {@code channel separation} shall be performed
	 * @param vector an array of <tt>N=2*2<sup>magnitude</sup></tt> complex numbers in Cartesian
	 *        form, alternating even indexed real parts with odd indexed imaginary ones
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws IllegalArgumentException if the given vector's length is odd or not a power of two
	 */
	static public void fft (final boolean inverse, final boolean separate, final float[] vector) throws NullPointerException, IllegalArgumentException {
		if (vector.length == 0 | vector.length == 2) return;
		final int magnitude = ScalarMath.floorLog2(vector.length) - 1;
		if (vector.length != 2 << magnitude) throw new IllegalArgumentException();

		if (inverse) {
			for (int index = 1; index < vector.length; index += 2) vector[index] *= -1;
		} else {
			if (separate) fold(inverse, vector);
		}

		fft(magnitude, vector);
		mul(vector, (float) Math.sqrt(Math.scalb(1.0, -magnitude)));

		if (inverse) {
			for (int index = 1; index < vector.length; index += 2) vector[index] *= -1;
			if (separate) fold(inverse, vector);
		}
	}


	/**
	 * This method provides an implementation for {@link #fft(boolean, float[])}.
	 * @param magnitude the value <tt>log<sub>2</sub>(N)</tt>
	 * @param vector an array of <tt>N=2*2<sup>magnitude</sup></tt> complex numbers in Cartesian
	 *        form, alternating even indexed real parts with odd indexed imaginary ones
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if the given vector's length is odd or not a power of
	 *         two
	 */
	static private void fft (final int magnitude, final float[] vector) throws NullPointerException, ArrayIndexOutOfBoundsException {
		assert vector.length == 2 << magnitude;

		for (final SwapEntry entry : FunctionTables.getPerfectShuffleTable(magnitude)) {
			final int left = entry.getLeft() << 1, right = entry.getRight() << 1;
			ArrayCopy.swap(vector, left, vector, right);
			ArrayCopy.swap(vector, left + 1, vector, right + 1);
		}

		final FunctionTables.Trigonometric trigonometricTable = FunctionTables.getTrigonometricTable(magnitude);
		for (int depth = 0; depth < magnitude; ++depth) {
			for (int offset = 0; offset < 1 << depth; ++offset) {
				final int angleIndex = offset << magnitude - depth - 1;
				final float sin = (float) trigonometricTable.sin(angleIndex);
				final float cos = (float) trigonometricTable.cos(angleIndex);

				for (int left = offset << 1, right = left + (2 << depth); left < vector.length; left += 4 << depth, right += 4 << depth) {
					float re = vector[right], im = vector[right + 1];
					final float taoRe = cos*re - sin*im;
					final float taoIm = cos*im + sin*re;
					re = vector[left]; im = vector[left + 1];
					vector[right]     = re - taoRe;
					vector[right + 1] = im - taoIm;
					vector[left]      = re + taoRe;
					vector[left  + 1] = im + taoIm;
				}
			}
		}
	}


	/**
	 * This operation performs a {@code fast fourier transform} on the given vector of braided real
	 * and imaginary parts, using an optimized {@code Cooley-Tukey} based algorithm. Only vectors of
	 * two times power two length {@code 2*N} with <tt>magnitude = log<sub>2</sub>(N)</tt> are
	 * supported; this implementation renormalizes the values using factor
	 * <tt>N<sup>-&half;</sup> = 2<sup>-&half;magnitude</sup></tt>.
	 * @param inverse whether or not an {@code inverse} fourier transform shall be performed
	 * @param separate whether or not {@code channel separation} shall be performed
	 * @param vector an array of <tt>N=2*2<sup>magnitude</sup></tt> complex numbers in Cartesian
	 *        form, alternating even indexed real parts with odd indexed imaginary ones
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws IllegalArgumentException if the given vector's length is odd or not a power of two
	 */
	static public void fft (final boolean inverse, final boolean separate, final double[] vector) throws NullPointerException, IllegalArgumentException {
		if (vector.length == 0 | vector.length == 2) return;
		final int magnitude = ScalarMath.floorLog2(vector.length) - 1;
		if (vector.length != 2 << magnitude) throw new IllegalArgumentException();

		if (inverse) {
			for (int index = 1; index < vector.length; index += 2) vector[index] *= -1;
		} else {
			if (separate) fold(inverse, vector);
		}

		fft(magnitude, vector);
		mul(vector, Math.sqrt(Math.scalb(1.0, -magnitude)));

		if (inverse) {
			for (int index = 1; index < vector.length; index += 2) vector[index] *= -1;
			if (separate) fold(inverse, vector);
		}
	}


	/**
	 * This method provides an implementation for {@link #fft(boolean, float[])}.
	 * @param magnitude the value <tt>log<sub>2</sub>(N)</tt>
	 * @param vector an array of <tt>N=2*2<sup>magnitude</sup></tt> complex numbers in Cartesian
	 *        form, alternating even indexed real parts with odd indexed imaginary ones
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if the given vector's length is odd or not a power of
	 *         two
	 */
	static private void fft (final int magnitude, final double[] vector) throws NullPointerException, ArrayIndexOutOfBoundsException {
		assert vector.length == 2 << magnitude;

		for (final SwapEntry entry : FunctionTables.getPerfectShuffleTable(magnitude)) {
			final int left = entry.getLeft() << 1, right = entry.getRight() << 1;
			ArrayCopy.swap(vector, left, vector, right);
			ArrayCopy.swap(vector, left + 1, vector, right + 1);
		}

		final FunctionTables.Trigonometric trigonometricTable = FunctionTables.getTrigonometricTable(magnitude);
		for (int mag = 0; mag < magnitude; ++mag) {
			for (int offset = 0; offset < 1 << mag; ++offset) {
				for (int left = offset << 1, right = left + (2 << mag); left < vector.length; left += 4 << mag, right += 4 << mag) {
					final int angleIndex = offset << magnitude - mag - 1;
					final double sin = trigonometricTable.sin(angleIndex);
					final double cos = trigonometricTable.cos(angleIndex);

					double re = vector[right], im = vector[right + 1];
					final double taoRe = cos * re - sin * im;
					final double taoIm = cos * im + sin * re;
					re = vector[left]; im = vector[left + 1];
					vector[right]     = re - taoRe;
					vector[right + 1] = im - taoIm;
					vector[left]      = re + taoRe;
					vector[left  + 1] = im + taoIm;
				}
			}
		}
	}


	/**
	 * If inverse is {@code true}, the given natural spectrum is untangled/unfolded using the
	 * following operations for each corresponding pair of spectrum entries (r,l,t &isin; &#x2102;):
	 * <br />
	 * <tt>forEach(l,r): t = r<sup>*</sup>; r = i&sdot;(t-l)/&radic;2; l = (t+l)/&radic;2;</tt>
	 * <br /><br />
	 * If inverse is {@code false}, the given unfolded spectrum is folded again using the following
	 * operations (again r,l,t &isin; &#x2102;):<br />
	 * <tt>r,l,t &isin; &#x2102;: t = i&sdot;r; r = (l-t)<sup>*</sup>/&radic;2; l = (l+t)/&radic;2</tt>
	 * <br /><br />
	 * The spectrum entries at index <tt>1</tt> and <tt>&frac12;N</tt> are swapped, which implies
	 * that after inverse folding the
	 * <ul>
	 * <li>spectrum entries <tt>0</tt> and <tt>1</tt> both belong to the left channel, representing
	 * frequencies <tt>f<sub>0</sub></tt> and <tt>f<sub>Nyquist</sub></tt></li>
	 * <li>spectrum entries <tt>&frac12;N</tt> and <tt>&frac12;N+1</tt> both belong to the right
	 * channel, again representing frequencies <tt>f<sub>0</sub></tt> and
	 * <tt>f<sub>Nyquist</sub></tt></li>
	 * </ul>
	 * The untangling/unfolding of a spectrum created by an iFFT operation is required in order to
	 * cleanly separate it's left and right halves; in unfolded state, a spectrum's left half is
	 * solely influenced by the real parts of the values that went into the iFFT, and the right half
	 * is solely influenced by the corresponding imaginary parts.
	 * @param vector the spectrum
	 * @throws NullPointerException if the given spectrum is {@code null}
	 * @throws IllegalArgumentException if the given spectrum's length isn't even
	 */
	static private void fold (final boolean inverse, final float[] vector) {
		if ((vector.length & 1) == 1) throw new IllegalArgumentException();

		final int halfLength = vector.length >> 1;
		final float norm = .5f * (float) ScalarMath.B_ROOT, swap = vector[halfLength];
		vector[halfLength] = vector[1];
		vector[1] = swap;

		if (inverse) {
			for (int leftIndex = 2, rightIndex = vector.length - 2; leftIndex < halfLength; leftIndex += 2, rightIndex -= 2) {
				final float leftRe = vector[leftIndex], leftIm = vector[leftIndex + 1];
				final float rightRe = vector[rightIndex], rightIm = vector[rightIndex + 1];
				vector[leftIndex  + 0] = (+leftRe +rightRe) * norm;
				vector[leftIndex  + 1] = (+leftIm -rightIm) * norm;
				vector[rightIndex + 0] = (+leftIm +rightIm) * norm;
				vector[rightIndex + 1] = (-leftRe +rightRe) * norm;
			}
		} else {
			for (int leftIndex = 2, rightIndex = vector.length - 2; leftIndex < halfLength; leftIndex += 2, rightIndex -= 2) {
				final float leftRe = vector[leftIndex], leftIm = vector[leftIndex + 1];
				final float rightRe = vector[rightIndex], rightIm = vector[rightIndex + 1];
				vector[leftIndex  + 0] = (+leftRe -rightIm) * norm;
				vector[leftIndex  + 1] = (+leftIm +rightRe) * norm;
				vector[rightIndex + 0] = (+leftRe +rightIm) * norm;
				vector[rightIndex + 1] = (-leftIm +rightRe) * norm;
			}
		}
	}


	/**
	 * If inverse is {@code true}, the given natural spectrum is untangled/unfolded using the
	 * following operations for each corresponding pair of spectrum entries (r,l,t &isin; &#x2102;):
	 * <br />
	 * <tt>forEach(l,r): t = r<sup>*</sup>; r = i&sdot;(t-l)/&radic;2; l = (t+l)/&radic;2;</tt>
	 * <br /><br />
	 * If inverse is {@code false}, the given unfolded spectrum is folded again using the following
	 * operations (again r,l,t &isin; &#x2102;):<br />
	 * <tt>r,l,t &isin; &#x2102;: t = i&sdot;r; r = (l-t)<sup>*</sup>/&radic;2; l = (l+t)/&radic;2</tt>
	 * <br /><br />
	 * The spectrum entries at index <tt>1</tt> and <tt>&frac12;N</tt> are swapped, which implies
	 * that after inverse folding the
	 * <ul>
	 * <li>spectrum entries <tt>0</tt> and <tt>1</tt> both belong to the left channel, representing
	 * frequencies <tt>f<sub>0</sub></tt> and <tt>f<sub>Nyquist</sub></tt></li>
	 * <li>spectrum entries <tt>&frac12;N</tt> and <tt>&frac12;N+1</tt> both belong to the right
	 * channel, again representing frequencies <tt>f<sub>0</sub></tt> and
	 * <tt>f<sub>Nyquist</sub></tt></li>
	 * </ul>
	 * The untangling/unfolding of a spectrum created by an iFFT operation is required in order to
	 * cleanly separate it's left and right halves; in unfolded state, a spectrum's left half is
	 * solely influenced by the real parts of the values that went into the iFFT, and the right half
	 * is solely influenced by the corresponding imaginary parts.
	 * @param vector the spectrum
	 * @throws NullPointerException if the given spectrum is {@code null}
	 * @throws IllegalArgumentException if the given spectrum's length isn't even
	 */
	static private void fold (final boolean inverse, final double[] vector) {
		if ((vector.length & 1) == 1) throw new IllegalArgumentException();

		final int halfLength = vector.length >> 1;
		final double norm = .5d * ScalarMath.B_ROOT, swap = vector[halfLength];
		vector[halfLength] = vector[1];
		vector[1] = swap;

		if (inverse) {
			for (int leftIndex = 2, rightIndex = vector.length - 2; leftIndex < halfLength; leftIndex += 2, rightIndex -= 2) {
				final double leftRe = vector[leftIndex], leftIm = vector[leftIndex + 1];
				final double rightRe = vector[rightIndex], rightIm = vector[rightIndex + 1];
				vector[leftIndex  + 0] = (+leftRe +rightRe) * norm;
				vector[leftIndex  + 1] = (+leftIm -rightIm) * norm;
				vector[rightIndex + 0] = (+leftIm +rightIm) * norm;
				vector[rightIndex + 1] = (-leftRe +rightRe) * norm;
			}
		} else {
			for (int leftIndex = 2, rightIndex = vector.length - 2; leftIndex < halfLength; leftIndex += 2, rightIndex -= 2) {
				final double leftRe = vector[leftIndex], leftIm = vector[leftIndex + 1];
				final double rightRe = vector[rightIndex], rightIm = vector[rightIndex + 1];
				vector[leftIndex  + 0] = (+leftRe -rightIm) * norm;
				vector[leftIndex  + 1] = (+leftIm +rightRe) * norm;
				vector[rightIndex + 0] = (+leftRe +rightIm) * norm;
				vector[rightIndex + 1] = (-leftIm +rightRe) * norm;
			}
		}
	}
}