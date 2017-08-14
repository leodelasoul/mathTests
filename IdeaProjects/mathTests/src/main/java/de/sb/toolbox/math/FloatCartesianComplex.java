package de.sb.toolbox.math;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import de.sb.toolbox.Copyright;
import de.sb.toolbox.math.FunctionTables.SwapEntry;


/**
 * This class implements mutable {@code complex numbers} that store single precision
 * {@Cartesian} coordinates.
 */
@Copyright(year=2008, holders="Sascha Baumeister")
public final class FloatCartesianComplex extends Complex.AbstractSinglePrecision<FloatCartesianComplex> implements Complex.MutableSinglePrecision<FloatCartesianComplex> {
	static private final long serialVersionUID = 1;
	static private final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
	static private final int PARALLEL_MAGNITUDE_THRESHOLD = 14;

	private float re;
	private float im;


	/**
	 * Construct a new instance with a real and an imaginary part of zero.
	 */
	public FloatCartesianComplex () {
		this(0f, 0f);
	}


	/**
	 * Construct a new instance with the given real part and an imaginary part of zero.
	 * @param real the real part
	 */
	public FloatCartesianComplex (final float re) {
		this(re, 0f);
	}


	/**
	 * Construct a new instance with the given real and imaginary parts.
	 * @param re the real part
	 * @param im the imaginary part
	 */
	public FloatCartesianComplex (final float re, final float im) {
		this.re = re;
		this.im = im;
	}


	/**
	 * Construct a new instance with the given complex value.
	 * @param value the complex value
	 * @throws NullPointerException if the given argument is {@code null}
	 */
	public FloatCartesianComplex (final SinglePrecision<?> value) throws NullPointerException {
		this(value.re(), value.im());
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex setCartesian (final float re, final float im) {
		this.re = re;
		this.im = im;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex setPolar (final float abs, final float arg) {
		this.re = abs * (float) Math.cos(arg);
		this.im = abs * (float) Math.sin(arg);
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isReal () {
		return this.im == 0f;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isImaginary () {
		return this.re == 0f;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isZero () {
		return this.re == 0f & this.im == 0f;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isInfinite () {
		return Float.isInfinite(this.re) | Float.isInfinite(this.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isNaN () {
		return Float.isNaN(this.re) | Float.isNaN(this.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public float re () {
		return this.re;
	}


	/**
	 * {@inheritDoc}
	 */
	public float im () {
		return this.im;
	}


	/**
	 * {@inheritDoc}
	 */
	public float abs () {
		return ScalarMath.abs(this.re, this.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public float arg () {
		return ScalarMath.arg(this.re, this.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex conj () {
		this.im = -this.im;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex neg () {
		return this.setCartesian(-this.re, -this.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex imul () {
		return this.setCartesian(-this.im, +this.re);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex idiv () {
		return this.setCartesian(+this.im, -this.re);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex inv () {
		final float re = this.re, im = this.im, norm = 1f / (re*re + im*im);
		return this.setCartesian(+re * norm, -im * norm);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex add (final float value) {
		this.re += value;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex sub (final float value) {
		this.re -= value;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex mul (final float value) {
		this.re *= value;
		this.im *= value;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex div (final float value) {
		return this.mul(1f / value);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex set (final FloatCartesianComplex value) throws NullPointerException {
		return this.setCartesian(value.re, value.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex add (final FloatCartesianComplex value) throws NullPointerException {
		this.re += value.re;
		this.im += value.im;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex sub (final FloatCartesianComplex value) throws NullPointerException {
		this.re -= value.re;
		this.im -= value.im;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex mul (final FloatCartesianComplex value) throws NullPointerException {
		final float re1 = this.re, im1 = this.im, re2 = value.re, im2 = value.im;
		return this.setCartesian(re1*re2 - im1*im2, re1*im2 + im1*re2);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex div (final FloatCartesianComplex value) throws NullPointerException {
		final float re1 = this.re, im1 = this.im, re2 = value.re, im2 = value.im, norm = 1f / (re2*re2 + im2*im2);
		return this.setCartesian((re1*re2 + im1*im2) * norm, (im1*re2 - re1*im2) * norm);
	}


	/**
	 * {@inheritDoc}
	 */
	public void mux (final FloatCartesianComplex value) throws NullPointerException {
		final float re1 = this.re, im1 = this.im, re2 = value.re, im2 = value.im;
		this.setCartesian(re1 + re2, im1 + im2);
		value.setCartesian(re1 - re2, im1 - im2);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex sq () {
		final float re = this.re, im = this.im;
		return this.setCartesian(re*re - im*im, 2f*re*im);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex sqrt () {
		final float re = this.re, im = this.im;
		if (re >= 0f & im == 0f) {
			this.re = (float) Math.sqrt(re);
			return this;
		}

		final float abs = this.abs(), signum = im >= 0f ? +1f : -1f;
		return this.setCartesian((float) Math.sqrt(.5d * (abs+re)), (float) Math.sqrt(.5d * (abs-re)) * signum);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex cb () {
		final float re = this.re, im = this.im;
		return this.setCartesian(re*re*re - 3f*re*im*im, 3f*re*re*im - im*im*im);
	}


	/**
	 * {@inheritDoc}
	 */
	public FloatCartesianComplex cbrt () {
		if (this.isReal()) {
			this.re = (float) Math.cbrt(this.re);
			return this;
		}

		final float abs = (float) Math.cbrt(this.abs());
		final float arg = this.arg() / 3f;
		return this.setPolar(abs, arg);
	}


	/**
	 * {@inheritDoc}
	 * @see #fft(FloatCartesianComplex[], int, int)
	 */
	@Override
	public void fft (final boolean inverse, final boolean separate, final FloatCartesianComplex[] vector) throws NullPointerException, IllegalArgumentException {
		if (vector.length < 2) return;

		final int magnitude = ScalarMath.floorLog2(vector.length);
		if (1 << magnitude != vector.length) throw new IllegalArgumentException();
		if (inverse) {
			for (int index = 0; index < vector.length; ++index) vector[index].conj();
		} else {
			if (separate) fold(inverse, vector);
		}

		if (vector.length < PARALLEL_MAGNITUDE_THRESHOLD) {
			this.fft(magnitude, vector);
		} else {
			FORK_JOIN_POOL.invoke(new RecursiveFourierTransformer(magnitude, vector));
		}

		final float norm = (float) Math.sqrt(Math.scalb(1.0, -magnitude));
		if (inverse) {
			for (int index = 0; index < vector.length; ++index) vector[index].mul(norm).conj();
			if (separate) fold(inverse, vector);
		} else {
			for (int index = 0; index < vector.length; ++index) vector[index].mul(norm);
		}


	}


	/**
	 * This operation performs a {@code fast fourier transform} on the given vector. Only
	 * vectors of power two length {@code N} with <tt>magnitude = log<sub>2</sub>(N)</tt> are
	 * supported.
	 * @param magnitude the value <tt>log<sub>2</sub>(N)</tt>
	 * @param vector an array of <tt>N = 2<sup>magnitude</sup></tt> complex numbers
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if the given vector has no power of two length
	 */
	private void fft (final int magnitude, final FloatCartesianComplex[] vector) throws NullPointerException, ArrayIndexOutOfBoundsException {
		assert 1 << magnitude == vector.length;

		final FloatCartesianComplex tao = new FloatCartesianComplex();
		for (final SwapEntry entry : FunctionTables.getPerfectShuffleTable(magnitude)) {
			tao.set(vector[entry.getLeft()]);
			vector[entry.getLeft()].set(vector[entry.getRight()]);
			vector[entry.getRight()].set(tao);
		}

		final FunctionTables.Trigonometric trigonometricTable = FunctionTables.getTrigonometricTable(magnitude);
		for (int depth = 0; depth < magnitude; ++depth) {
			for (int offset = 0; offset < 1 << depth; ++offset) {
				final int angleIndex = offset << magnitude - depth - 1;
				this.setCartesian((float) trigonometricTable.cos(angleIndex), (float) trigonometricTable.sin(angleIndex));

				for (int index = offset + (1 << depth); index < 1 << magnitude; index += 2 << depth) {
					vector[index - (1 << depth)].mux(vector[index].mul(this));
				}
			}
		}
	}


	/**
	 * If inverse is {@code true}, the given natural spectrum is untangled/unfolded using the
	 * following operations for each corresponding pair of spectrum entries (r,l,t &isin; &#x2102;):
	 * <br />
	 * <tt>forEach(l,r): t = r<sup>*</sup>; r = i&sdot;(t-l)/√2; l = (t+l)/√2;</tt><br />
	 * <br />
	 * If inverse is {@code false}, the given unfolded spectrum is folded again using the following
	 * operations (again r,l,t &isin; &#x2102;):<br />
	 * <tt>r,l,t &isin; &#x2102;: t = i&sdot;r; r = (l-t)<sup>*</sup>/√2; l = (l+t)/√2</tt><br />
	 * <br />
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
	 * @param spectrum the spectrum
	 * @throws NullPointerException if the given spectrum is {@code null}
	 * @throws IllegalArgumentException if the given spectrum's length is odd
	 */
	static private void fold (final boolean inverse, final FloatCartesianComplex[] spectrum) throws NullPointerException, IllegalArgumentException {
		if ((spectrum.length & 1) == 1) throw new IllegalArgumentException();

		final int halfLength = spectrum.length >> 1;
		final float norm = (float) (ScalarMath.B_ROOT / 2);
		final FloatCartesianComplex left = new FloatCartesianComplex();
		spectrum[halfLength].set(spectrum[1]);
		spectrum[1].set(left);

		final FloatCartesianComplex right = new FloatCartesianComplex();
		if (inverse) {
			for (int asc = 1, desc = spectrum.length - 1; asc < desc; ++asc, --desc) {
				left.set(spectrum[asc]);
				right.set(spectrum[desc].conj());
				spectrum[desc].sub(left).imul().mul(norm);
				spectrum[asc].add(right).mul(norm);
			}
		} else {
			for (int asc = 1, desc = spectrum.length - 1; asc < desc; ++asc, --desc) {
				left.set(spectrum[asc]);
				right.set(spectrum[desc].imul());
				spectrum[desc].sub(left).conj().mul(-norm);
				spectrum[asc].add(right).mul(norm);
			}
		}
	}



	/**
	 * Resultless {@link ForkJoinTask} modelling a parallel-recursive FFT task, based on an
	 * optimized algorithm of (Danielson & Lanczos}, 1942.
	 */
	static private class RecursiveFourierTransformer extends RecursiveAction {
		static private final long serialVersionUID = 1L;

		private final int magnitude;
		private final FloatCartesianComplex[] vector;


		/**
		 * Creates a new instance.
		 * @param magnitude the value <tt>log<sub>2</sub>(N)</tt>
		 * @param vector an array of <tt>N = 2<sup>magnitude</sup></tt> complex numbers
		 */
		public RecursiveFourierTransformer (final int magnitude, final FloatCartesianComplex[] vector) {
			super();
			this.magnitude = magnitude;
			this.vector = vector;
		}


		/**
		 * {@inheritDoc}
		 */
		protected void compute () {
			final FloatCartesianComplex unit = new FloatCartesianComplex();
			if (this.magnitude < PARALLEL_MAGNITUDE_THRESHOLD) {
				unit.fft(this.magnitude, this.vector);
				return;
			}

			// prepare stage: divide vector into even and odd indexed parts
			final int half = 1 << this.magnitude-1;
			final FloatCartesianComplex[] even = new FloatCartesianComplex[half], odd = new FloatCartesianComplex[half];
			for (int index = 0; index < half; ++index) {
				even[index] = this.vector[(index << 1) + 0];
				odd [index] = this.vector[(index << 1) + 1];
			}

			// divide stage: transform partial terms
			final RecursiveFourierTransformer evenTask = new RecursiveFourierTransformer(this.magnitude - 1, even);
			final RecursiveFourierTransformer oddTask = new RecursiveFourierTransformer(this.magnitude - 1, odd);
			oddTask.fork();
			evenTask.compute();
			oddTask.join();

			// conquer stage: recombine partial results
			final FunctionTables.Trigonometric trigonometricTable = FunctionTables.getTrigonometricTable(this.magnitude);
			for (int index = 0; index < half; ++index) {
				this.vector[index] = even[index];
				this.vector[index + half] = odd[index];
				unit.setCartesian((float) trigonometricTable.cos(index), (float) trigonometricTable.sin(index));
				even[index].mux(odd[index].mul(unit));
			}
		}
	}
}