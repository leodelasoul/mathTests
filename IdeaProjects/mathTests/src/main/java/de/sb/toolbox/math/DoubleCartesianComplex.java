package de.sb.toolbox.math;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import de.sb.toolbox.Copyright;
import de.sb.toolbox.math.FunctionTables.SwapEntry;

import com.aparapi.Kernel;
import com.aparapi.ProfileInfo;
import com.aparapi.Range;

/**
 * This class implements mutable {@code complex numbers} that store double precision
 * {@Cartesian} coordinates.
 */
@Copyright(year=2008, holders="Sascha Baumeister")
public final class DoubleCartesianComplex extends Complex.AbstractDoublePrecision<DoubleCartesianComplex> implements Complex.MutableDoublePrecision<DoubleCartesianComplex> {
	static private final long serialVersionUID = 1;
	static private final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
	static private final int PARALLEL_MAGNITUDE_THRESHOLD = 16;

	private double re;
	private double im;


	/**
	 * Construct a new instance with a real and an imaginary part of zero.
	 */
	public DoubleCartesianComplex () {
		this(0d, 0d);
	}


	/**
	 * Construct a new instance with the given real part and an imaginary part of zero.
	 * @param real the real part
	 */
	public DoubleCartesianComplex (final double re) {
		this(re, 0d);
	}


	/**
	 * Construct a new instance with the given real and imaginary parts.
	 * @param re the real part
	 * @param im the imaginary part
	 */
	public DoubleCartesianComplex (final double re, final double im) {
		this.re = re;
		this.im = im;
	}


	/**
	 * Construct a new instance with the given complex value.
	 * @param value the complex value
	 * @throws NullPointerException if the given argument is {@code null}
	 */
	public DoubleCartesianComplex (final DoublePrecision<?> value) throws NullPointerException {
		this(value.re(), value.im());
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex setCartesian (final double re, final double im) {
		this.re = re;
		this.im = im;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex setPolar (final double abs, final double arg) {
		this.re = abs * Math.cos(arg);
		this.im = abs * Math.sin(arg);
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isReal () {
		return this.im == 0d;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isImaginary () {
		return this.re == 0d;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isZero () {
		return this.re == 0d & this.im == 0d;
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isInfinite () {
		return Double.isInfinite(this.re) | Double.isInfinite(this.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isNaN () {
		return Double.isNaN(this.re) | Double.isNaN(this.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public double re () {
		return this.re;
	}


	/**
	 * {@inheritDoc}
	 */
	public double im () {
		return this.im;
	}


	/**
	 * {@inheritDoc}
	 */
	public double abs () {
		return ScalarMath.abs(this.re, this.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public double arg () {
		return ScalarMath.arg(this.re, this.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex conj () {
		this.im = -this.im;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex neg () {
		return this.setCartesian(-this.re, -this.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex imul () {
		return this.setCartesian(-this.im, +this.re);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex idiv () {
		return this.setCartesian(+this.im, -this.re);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex inv () {
		final double re = this.re, im = this.im, norm = 1d / (re*re + im*im);
		return this.setCartesian(+re * norm, -im * norm);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex add (final double value) {
		this.re += value;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex sub (final double value) {
		this.re -= value;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex mul (final double value) {
		this.re *= value;
		this.im *= value;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex div (final double value) {
		return this.mul(1d / value);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex set (final DoubleCartesianComplex value) throws NullPointerException {
		return this.setCartesian(value.re, value.im);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex add (final DoubleCartesianComplex value) throws NullPointerException {
		this.re += value.re;
		this.im += value.im;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex sub (final DoubleCartesianComplex value) throws NullPointerException {
		this.re -= value.re;
		this.im -= value.im;
		return this;
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex mul (final DoubleCartesianComplex value) throws NullPointerException {
		final double re1 = this.re, im1 = this.im, re2 = value.re, im2 = value.im;
		return this.setCartesian(re1*re2 - im1*im2, re1*im2 + im1*re2);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex div (final DoubleCartesianComplex value) throws NullPointerException {
		final double re1 = this.re, im1 = this.im, re2 = value.re, im2 = value.im, norm = 1d / (re2*re2 + im2*im2);
		return this.setCartesian((re1*re2 + im1*im2) * norm, (im1*re2 - re1*im2) * norm);
	}


	/**
	 * {@inheritDoc}
	 */
	public void mux (final DoubleCartesianComplex value) throws NullPointerException {
		final double re1 = this.re, im1 = this.im, re2 = value.re, im2 = value.im;
		this.setCartesian(re1 + re2, im1 + im2);
		value.setCartesian(re1 - re2, im1 - im2);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex sq () {
		final double re = this.re, im = this.im;
		return this.setCartesian(re*re - im*im, 2d*re*im);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex sqrt () {
		final double re = this.re, im = this.im;
		if (re >= 0d & im == 0d) {
			this.re = Math.sqrt(re);
			return this;
		}

		final double abs = this.abs(), signum = im >= 0d ? +1d : -1d;
		return this.setCartesian(Math.sqrt(.5d * (abs+re)), Math.sqrt(.5d * (abs-re)) * signum);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex cb () {
		final double re = this.re, im = this.im;
		return this.setCartesian(re*re*re - 3d*re*im*im, 3d*re*re*im - im*im*im);
	}


	/**
	 * {@inheritDoc}
	 */
	public DoubleCartesianComplex cbrt () {
		if (this.isReal()) {
			this.re = Math.cbrt(this.re);
			return this;
		}

		final double abs = Math.cbrt(this.abs());
		final double arg = this.arg() / 3d;
		return this.setPolar(abs, arg);
	}


	/**
	 * {@inheritDoc}
	 * @see #fft(DoubleCartesianComplex[], int, int)
	 */
	@Override
	public void fft (final boolean inverse, final boolean separate, final DoubleCartesianComplex[] vector) throws NullPointerException, IllegalArgumentException {

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
			new RecursiveFourierTransformer(PARALLEL_MAGNITUDE_THRESHOLD, vector);
		}
		final double norm = Math.sqrt(Math.scalb(1.0, -magnitude));
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
	 * supported, and implementations are required to renormalize the values using factor
	 * <tt>N<sup>-&half;</sup> = </tt> <tt>2<sup>-&half;magnitude</sup></tt>. Note that an
	 * inverse fourier transform can be achieved by conjugating all vector elements before and
	 * after calling this operation. <br />
	 * @param magnitude the value <tt>log<sub>2</sub>(N)</tt>
	 * @param vector an array of <tt>N = 2<sup>magnitude</sup></tt> complex numbers
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if the given vector has no power of two length
	 */
	private void fft (final int magnitude, final DoubleCartesianComplex[] vector) throws NullPointerException, ArrayIndexOutOfBoundsException {
		assert 1 << magnitude == vector.length;
		final FunctionTables.Trigonometric trigonometricTable = FunctionTables.getTrigonometricTable(magnitude);


		for (final SwapEntry entry : FunctionTables.getPerfectShuffleTable(magnitude)) {
			this.set(vector[entry.getLeft()]);
			vector[entry.getLeft()].set(vector[entry.getRight()]);
			vector[entry.getRight()].set(this);
		}

		Kernel kernel = new Kernel() {
			@Override
			public void run() {
				int id = getGlobalId();
				final int angleIndex = id << magnitude - id - 1;
				setCartesian(trigonometricTable.cos(angleIndex), trigonometricTable.sin(angleIndex));
				vector[id - (1 << id)].mux(vector[id].mul(DoubleCartesianComplex.super.doubleValue()));



			}
		};
		Range range = Range.create(vector.length);
		kernel.execute(range);

			/*final FunctionTables.Trigonometric trigonometricTable = FunctionTables.getTrigonometricTable(magnitude);
			for (int depth = 0; depth < magnitude; ++depth) {
				for (int offset = 0; offset < 1 << depth; ++offset) {
					final int angleIndex = offset << magnitude - depth - 1;
					this.setCartesian(trigonometricTable.cos(angleIndex), trigonometricTable.sin(angleIndex));

					for (int index = offset + (1 << depth); index < 1 << magnitude; index += 2 << depth) {
						vector[index - (1 << depth)].mux(vector[index].mul(this));
					}
				}
			}

*/

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
	static private void fold (final boolean inverse, final DoubleCartesianComplex[] spectrum) throws NullPointerException, IllegalArgumentException {
		if ((spectrum.length & 1) == 1) throw new IllegalArgumentException();

		final int halfLength = spectrum.length >> 1;
		final double norm = ScalarMath.B_ROOT / 2;
		final DoubleCartesianComplex left = new DoubleCartesianComplex();
		spectrum[halfLength].set(spectrum[1]);
		spectrum[1].set(left);

		final DoubleCartesianComplex right = new DoubleCartesianComplex();
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
	static private class RecursiveFourierTransformer extends Kernel {
		static private final long serialVersionUID = 1L;

		private final int magnitude;
		private final DoubleCartesianComplex[] vector;


		/**
		 * Creates a new instance.
		 * @param magnitude the value <tt>log<sub>2</sub>(N)</tt>
		 * @param vector an array of <tt>N = 2<sup>magnitude</sup></tt> complex numbers
		 */
		public RecursiveFourierTransformer (final int magnitude, final DoubleCartesianComplex[] vector) {
			super();
			this.vector = vector;
			this.magnitude = magnitude;
		}
		int id = getGlobalId();

		/**
		 * {@inheritDoc}
		 */

			@Override
			public void run() {

				final DoubleCartesianComplex unit = new DoubleCartesianComplex();
				if (this.magnitude < PARALLEL_MAGNITUDE_THRESHOLD) {
					unit.fft(this.magnitude, this.vector);
					return;
				}

				// prepare stage: divide vector into even and odd indexed parts
				final int half = 1 << this.magnitude - 1;
				final DoubleCartesianComplex[] even = new DoubleCartesianComplex[half], odd = new DoubleCartesianComplex[half];

				even[id] = this.vector[(id << 1) + 0];
				odd[id] = this.vector[(id << 1) + 1];

				// conquer stage: recombine partial results
				final FunctionTables.Trigonometric trigonometricTable = FunctionTables.getTrigonometricTable(this.magnitude);

				this.vector[id] = even[id];
				this.vector[id + half] = odd[id];
				unit.setCartesian(trigonometricTable.cos(id), trigonometricTable.sin(id));
				even[id].mux(odd[id].mul(unit));

			};

		};

}
