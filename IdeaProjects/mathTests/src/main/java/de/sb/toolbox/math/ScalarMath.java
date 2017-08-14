package de.sb.toolbox.math;

import de.sb.toolbox.Copyright;


/**
 * This facade adds additional mathematical operations for scalar arithmetics. Note that this class
 * is declared final because it is a facade, and therefore not supposed to be extended. Also note
 * that most operations will likely be inlined by any JVM.
 */
@Copyright(year=2013, holders="Sascha Baumeister")
public final class ScalarMath {
	static private final double INV_LOG2 = 1d / Math.log(2d);

	/**
	 * The binary square root, i.e. <tt>2<sup>&half;</sup></tt>.
	 */
	static public final double B_ROOT = 1.4142135623730951;

	/**
	 * <tt>&tau; = 2*&pi;</tt>. See the <a href="http://tauday.com/tau-manifesto">Tau-Manifesto</a>
	 * why TAU is the superior choice for the circle constant.
	 */
	static public final double TAU = 2 * Math.PI;

	/**
	 * The binary mask for single-precision floating-point sign access.
	 */
	static public final long MASK_FLOAT_SIGN = 0x80000000L;

	/**
	 * The binary mask for single-precision floating-point exponent access.
	 */
	static public final long MASK_FLOAT_EXPONENT = 0x7f800000L;

	/**
	 * The binary mask for single-precision floating-point mantissa access.
	 */
	static public final long MASK_FLOAT_MANTISSA = 0x007fffffL;

	/**
	 * The binary mask for double-precision floating-point sign access.
	 */
	static public final long MASK_DOUBLE_SIGN = 0x8000000000000000L;

	/**
	 * The binary mask for double-precision exponent access.
	 */
	static public final long MASK_DOUBLE_EXPONENT = 0x7ff0000000000000L;

	/**
	 * The binary mask for double-precision floating-point mantissa access.
	 */
	static public final long MASK_DOUBLE_MANTISSA = 0x000fffffffffffffL;


	/**
	 * Prevents external instantiation.
	 */
	private ScalarMath () {}



	//*******************//
	// common operations //
	//*******************//

	/**
	 * Returns the square of the given operand {@code x}.
	 * @param x the operand value
	 * @return the value <tt>x<sup>2</sup></tt>
	 */
	static public int sq (final int x) {
		return x * x;
	}


	/**
	 * Returns the square of the given operand {@code x}.
	 * @param x the operand value
	 * @return the value <tt>x<sup>2</sup></tt>
	 */
	static public long sq (final long x) {
		return x * x;
	}


	/**
	 * Returns the square of the given operand {@code x}.
	 * @param x the operand value
	 * @return the value <tt>x<sup>2</sup></tt>
	 */
	static public float sq (final float x) {
		return x * x;
	}


	/**
	 * Returns the square of the given operand {@code x}.
	 * @param x the operand value
	 * @return the value <tt>x<sup>2</sup></tt>
	 */
	static public double sq (final double x) {
		return x * x;
	}


	/**
	 * Returns the cube of the given operand {@code x}.
	 * @param x the operand value
	 * @return the value <tt>x<sup>3</sup></tt>
	 */
	static public int cb (final int x) {
		return x * x * x;
	}


	/**
	 * Returns the cube of the given operand {@code x}.
	 * @param x the operand value
	 * @return the value <tt>x<sup>3</sup></tt>
	 */
	static public long cb (final long x) {
		return x * x * x;
	}


	/**
	 * Returns the cube of the given operand {@code x}.
	 * @param x the operand value
	 * @return the value <tt>x<sup>3</sup></tt>
	 */
	static public float cb (final float x) {
		return x * x * x;
	}


	/**
	 * Returns the cube of the given operand {@code x}.
	 * @param x the operand value
	 * @return the value <tt>x<sup>3</sup></tt>
	 */
	static public double cb (final double x) {
		return x * x * x;
	}


	/**
	 * Returns the {@code Euclidean modulo} of the given dividend and divisor, based on the
	 * {@code truncated modulo} returned by the {@code %} operator.
	 * @param x the dividend value
	 * @param y the divisor value
	 * @return the value <tt>x mod<sub>e</sub> y</tt> within range {@code [0,|y|[}
	 * @throws ArithmeticException if the given divisor is zero
	 */
	static public int mod (final int x, final int y) throws ArithmeticException {
		return x >= 0 ? x % y : Math.abs(y) + x % y;
	}


	/**
	 * Returns the {@code Euclidean modulo} of the given dividend and divisor, based on the
	 * {@code truncated modulo} returned by the {@code %} operator.
	 * @param x the dividend value
	 * @param y the divisor value
	 * @return the value <tt>x mod<sub>e</sub> y</tt> within range {@code [0,|y|[}
	 * @throws ArithmeticException if the given divisor is zero
	 */
	static public long mod (final long x, final long y) throws ArithmeticException {
		final long mod = x % y;
		return mod >= 0 ? mod : Math.abs(y) + mod;
	}


	/**
	 * Returns the {@code Euclidean modulo} of the given dividend and divisor, based on the
	 * {@code truncated modulo} returned by the {@code %} operator.
	 * @param x the dividend value
	 * @param y the divisor value
	 * @return the value <tt>x mod<sub>e</sub> y</tt> within range {@code [0,|y|[}, or {@code NaN}
	 *         if the given divisor is zero
	 */
	static public float mod (final float x, final float y) {
		return x >= 0 ? x % y : Math.abs(y) + x % y;
	}


	/**
	 * Returns the {@code Euclidean modulo} of the given dividend and divisor, based on the
	 * {@code truncated modulo} returned by the {@code %} operator.
	 * @param x the dividend value
	 * @param y the divisor value
	 * @return the value <tt>x mod<sub>e</sub> y</tt> within range {@code [0,|y|[}, or {@code NaN}
	 *         if the given divisor is zero
	 */
	static public double mod (final double x, final double y) {
		return x >= 0 ? x % y : Math.abs(y) + x % y;
	}


	/**
	 * Returns the largest (closest to positive infinity) value that is less than or equal to the
	 * {@code binary logarithm} of the given operand and is equal to a mathematical integer.
	 * @param x the operand value
	 * @return the value <tt>floor(log<sub>2</sub>(x))</tt>, with {@code -1} representing
	 *         {@code negative infinity}
	 * @throws ArithmeticException if the given operand is strictly negative
	 */
	static public int floorLog2 (final int x) throws ArithmeticException {
		if (x < 0) throw new ArithmeticException();
		return (Integer.SIZE - 1) - Integer.numberOfLeadingZeros(x);
	}


	/**
	 * Returns the largest (closest to positive infinity) value that is less than or equal to the
	 * {@code binary logarithm} of the given operand and is equal to a mathematical integer.
	 * @param x the operand value
	 * @return the value <tt>floor(log<sub>2</sub>(x))</tt>, with {@code -1} representing
	 *         {@code negative infinity}
	 * @throws ArithmeticException if the given operand is strictly negative
	 */
	static public long floorLog2 (final long x) throws ArithmeticException {
		if (x < 0) throw new ArithmeticException();
		return (Long.SIZE - 1) - Long.numberOfLeadingZeros(x);
	}


	/**
	 * Returns the smallest (closest to negative infinity) value that is greater than or equal to
	 * the {@code binary logarithm} of the given operand and is equal to a mathematical integer.
	 * @param operand the operand
	 * @return the value <tt>ceil(log<sub>2</sub>(x))</tt>, with {@code -1} representing
	 *         {@code negative infinity}
	 * @throws ArithmeticException if the given operand is strictly negative
	 */
	static public int ceilLog2 (final int x) throws ArithmeticException {
		if (x < 0) throw new ArithmeticException();
		final int result = Integer.SIZE - Integer.numberOfLeadingZeros(x - 1);
		return result < Integer.SIZE ? result : -1;
	}


	/**
	 * Returns the smallest (closest to negative infinity) value that is greater than or equal to
	 * the {@code binary logarithm} of the given operand and is equal to a mathematical integer.
	 * @param operand the operand
	 * @return the value <tt>ceil(log<sub>2</sub>(x))</tt>, with {@code -1} representing
	 *         {@code negative infinity}
	 * @throws ArithmeticException if the given operand is strictly negative
	 */
	static public long ceilLog2 (final long x) throws ArithmeticException {
		if (x < 0) throw new ArithmeticException();
		final int result = Long.SIZE - Long.numberOfLeadingZeros(x - 1);
		return result < Long.SIZE ? result : -1;
	}


	/**
	 * Returns the {@code binary logarithm} of the given operand.
	 * @param operand the operand
	 * @return the value <tt>ceil(log<sub>2</sub>(x))</tt>, with {@code -1} representing
	 *         {@code negative infinity}
	 * @throws ArithmeticException if the given operand is strictly negative
	 */
	static public float log2 (final float x) throws ArithmeticException {
		return (float) log2((double) x);
	}


	/**
	 * Returns the {@code binary logarithm} of the given operand.
	 * @param operand the operand
	 * @return the value <tt>ceil(log<sub>2</sub>(x))</tt>, with {@code -1} representing
	 *         {@code negative infinity}
	 * @throws ArithmeticException if the given operand is strictly negative
	 */
	static public double log2 (final double x) throws ArithmeticException {
		return Math.log(x) * INV_LOG2; 
	}



	//********************//
	// integer operations //
	//********************//

	/**
	 * Returns the absolute value of the given operand {@code x}, for all values within range
	 * <tt>]-2<sup>31</sup>, +2<sup>31</sup>[</tt>. This operation computes the result in a
	 * branch-free fashion using three basic operations, in order to avoid expensive processor
	 * pipeline resets due to branch prediction failure:
	 * <ol>
	 * <li>use signed shift to calculate the value's sign, i.e. -1 if negative, else 0</li>
	 * <li>use XOR to calculate one's complement if negative</li>
	 * <li>use subtraction to calculate two's complement if negative</li>
	 * </ol>
	 * Note that the result will be incorrect for {@link Integer.MIN_VALUE} due to overflow!
	 * @param x the operand value
	 * @return the value <tt>|x| = x * signum(x)</tt>
	 * @see Math#abs(int)
	 * @see #signum(int)
	 */
	static public int abs (final int x) {
		final int sign = x >> (Integer.SIZE - 1);
		return (x ^ sign) - sign;
	}


	/**
	 * Returns the absolute value of the given operand {@code x}, for all values within range
	 * <tt>]-2<sup>63</sup>, +2<sup>63</sup>[</tt>. This operation computes the result in a
	 * branch-free fashion using three basic operations, in order to avoid expensive processor
	 * pipeline resets due to branch prediction failure:
	 * <ol>
	 * <li>use signed shift to calculate the value's sign, i.e. -1 if negative, else 0</li>
	 * <li>use XOR to calculate one's complement if negative</li>
	 * <li>use subtraction to calculate two's complement if negative</li>
	 * </ol>
	 * Note that the result will be incorrect for {@link Long.MIN_VALUE} due to overflow!
	 * @param x the operand value
	 * @return the value <tt>|x| = x * signum(x)</tt>
	 * @see Math#abs(long)
	 * @see #signum(long)
	 */
	static public long abs (final long x) {
		final long sign = x >> (Long.SIZE - 1);
		return (x ^ sign) - sign;
	}


	/**
	 * Returns the signum of the given operand {@code x}, for all values within range
	 * {@code ]-2^31, +2^31[}. This operation computes the result in a branch-free fashion using
	 * four basic operations, in order to avoid expensive processor pipeline resets due to branch
	 * prediction failure:
	 * <ol>
	 * <li>use subtraction to calculate the value's two's complement</li>
	 * <li>use signed shifts to calculate sign expansion for the value and it's two-complement</li>
	 * <li>use subtraction to calculate the difference between both sign expansions</li>
	 * </ol>
	 * Note that the result will be incorrect for {@link Integer.MIN_VALUE} due to overflow!
	 * @param x the operand value
	 * @return the value <tt>signum(x) = x / |x|</tt>
	 * @see Math#signum(float)
	 * @see #abs(int)
	 */
	static public int signum (final int x) {
		return (x >> (Integer.SIZE - 1)) - (-x >> (Integer.SIZE - 1));
	}


	/**
	 * Returns the signum of the given operand {@code x}, for all values within range
	 * {@code ]-2^63, +2^63[}. This operation computes the result in a branch-free fashion using
	 * four basic operations, in order to avoid expensive processor pipeline resets due to branch
	 * prediction failure:
	 * <ol>
	 * <li>use subtraction to calculate the value's two's complement</li>
	 * <li>use signed shifts to calculate sign expansion for the value and it's two-complement</li>
	 * <li>use subtraction to calculate the difference between both sign expansions</li>
	 * </ol>
	 * Note that the result will be incorrect for {@link Long.MIN_VALUE} due to overflow!
	 * @param x the operand value
	 * @return the value <tt>signum(x) = x / |x|</tt>
	 * @see Math#signum(double)
	 * @see #abs(long)
	 */
	static public long signum (final long x) {
		return (x >> (Long.SIZE - 1)) - (-x >> (Long.SIZE - 1));
	}


	/**
	 * Returns two raised to the power of the given exponent <tt>x mod<sub>e</sub> 32</tt>. Note
	 * that exponents 30 and 31 cause overflows, returning {@link Integer#MIN_VALUE} and zero
	 * respectively.
	 * @param x the exponent
	 * @return the value <tt>2<sup>x mod<sub>e</sub> 32</sup></tt>
	 */
	static public int exp2 (final int x) {
		return 1 << x;
	}


	/**
	 * Returns two raised to the power of the given exponent <tt>x mod<sub>e</sub> 64</tt>. Note
	 * that exponents 62 and 63 cause overflows, returning {@link Long#MIN_VALUE} and zero
	 * respectively.
	 * @param x the exponent
	 * @return the value <tt>2<sup>x mod<sub>e</sub> 64</sup></tt>
	 */
	static public long exp2 (final long x) {
		return 1L << x;
	}


	/**
	 * Returns the given factor multiplied with two raised to the power of the given exponent
	 * <tt>y mod<sub>e</sub> 32</tt>. Note that exponents 30 and 31 are guaranteed to cause
	 * intermediate overflows, except for zero factors.
	 * @param x the factor
	 * @param y the exponent
	 * @return the value <tt>x * 2<sup>y mod<sub>e</sub> 32</sup></tt>
	 */
	static public long mulExp2 (final int x, final int y) {
		return x << y;
	}


	/**
	 * Returns the given factor multiplied with two raised to the power of the given exponent
	 * <tt>y mod<sub>e</sub> 64</tt>. Note that exponents 62 and 63 are guaranteed to cause
	 * intermediate overflows, except for zero factors.
	 * @param x the factor
	 * @param y the exponent
	 * @return the value <tt>x * 2<sup>y mod<sub>e</sub> 64</sup></tt>
	 */
	static public long mulExp2 (final long x, final long y) {
		return x << y;
	}


	/**
	 * Returns the given factor divided by two raised to the power of the given exponent
	 * <tt>y mod<sub>e</sub> 32</tt>. Note that exponents 30 and 31 are guaranteed to cause
	 * intermediate underflows, except for zero factors.
	 * @param x the factor
	 * @param y the exponent
	 * @return the value <tt>x / 2<sup>y mod<sub>e</sub> 32</sup></tt>
	 */
	static public int divExp2 (final int x, final int y) {
		return x >> y;
	}


	/**
	 * Returns the given factor divided by two raised to the power of the given exponent
	 * <tt>y mod<sub>e</sub> 64</tt>. Note that exponents 62 and 63 are guaranteed to cause
	 * intermediate underflows, except for zero factors.
	 * @param x the factor
	 * @param y the exponent
	 * @return the value <tt>x / 2<sup>y mod<sub>e</sub> 64</sup></tt>
	 */
	static public long divExp2 (final long x, final long y) {
		return x >> y;
	}


	/**
	 * Returns the given factor modulo two raised to the power of the given exponent
	 * <tt>y mod<sub>e</sub> 32</tt>. Note that the {@code Euclidean
	 * modulo} is calculated, and that exponents 30 and 31 are guaranteed to cause intermediate
	 * underflows, except for zero factors.
	 * @param x the factor
	 * @param y the exponent
	 * @return the value <tt>x mod<sub>e</sub> 2<sup>y mod<sub>e</sub> 32</sup></tt>
	 */
	static public int modExp2 (final int x, final int y) {
		return x & ((1 << y) - 1);
	}


	/**
	 * Returns the given factor modulo two raised to the power of the given exponent
	 * <tt>y mod<sub>e</sub> 64</tt>. Note that the {@code Euclidean
	 * modulo} is calculated, and that exponents 62 and 63 are guaranteed to cause intermediate
	 * underflows, except for zero factors.
	 * @param x the factor
	 * @param y the exponent
	 * @return the value <tt>x mod<sub>e</sub> 2<sup>y mod<sub>e</sub> 64</sup></tt>
	 */
	static public long modExp2 (final long x, final long y) {
		return x & ((1L << y) - 1);
	}


	/**
	 * Returns the {@code greatest common divisor} (GCD) of the two given operands, and returns it.
	 * Note that this operation implements the fast binary GCD algorithm (Josef Stein, 1967).
	 * @param x the left operand
	 * @param y the right operand
	 * @return the greatest common divisor of both operands
	 */
	static public int gcd (int x, int y) {
		if (x < 0) x = -x;
		if (y < 0) y = -y;
		if (x == 0) return y;
		if (y == 0) return x;

		final int shift = Math.min(Integer.numberOfTrailingZeros(x), Integer.numberOfTrailingZeros(y));
		x >>= shift;
		y >>= shift;

		for (x >>= Integer.numberOfTrailingZeros(x); y != 0; y -= x) {
			y >>= Integer.numberOfTrailingZeros(y);
			if (x > y) {
				x ^= y;
				y ^= x;
				x ^= y;
			}
		}

		return x << shift;
	}


	/**
	 * Returns the {@code greatest common divisor} (GCD) of the two given operands, and returns it.
	 * Note that this operation implements the fast binary GCD algorithm (Josef Stein, 1967).
	 * @param x the left operand
	 * @param y the right operand
	 * @return the greatest common divisor of both operands
	 */
	static public long gcd (long x, long y) {
		if (x < 0) x = -x;
		if (y < 0) y = -y;
		if (x == 0) return y;
		if (y == 0) return x;

		final int shift = Math.min(Long.numberOfTrailingZeros(x), Long.numberOfTrailingZeros(y));
		x >>= shift;
		y >>= shift;

		for (x >>= Long.numberOfTrailingZeros(x); y != 0; y -= x) {
			y >>= Long.numberOfTrailingZeros(y);
			if (x > y) {
				x ^= y;
				y ^= x;
				x ^= y;
			}
		}

		return x << shift;
	}


	/**
	 * Calculates the {@code least common multiple} (LCM) of the two given operands, and returns it.
	 * Note that this operation divides by the GCD before multiplying, therefore avoiding
	 * intermediate over- or underflow.
	 * @param x the left operand
	 * @param y the right operand
	 * @return the least common multiple of both values
	 */
	static public int lcm (final int x, final int y) {
		final int gcd = gcd(x, y);
		return gcd == 0 ? 0 : Math.abs(x / gcd * y);
	}


	/**
	 * Calculates the {@code least common multiple} (LCM) of the two given operands, and returns it.
	 * Note that this operation divides by the GCD before multiplying, therefore avoiding
	 * intermediate over- or underflow.
	 * @param x the left operand
	 * @param y the right operand
	 * @return the least common multiple of both values
	 */
	static public long lcm (final long x, final long y) {
		final long gcd = gcd(x, y);
		return gcd == 0 ? 0 : Math.abs(x / gcd * y);
	}


	/**
	 * Returns the swap index associated with the given shuffle index {@code x} for a collection of
	 * <tt>2<sup>magnitude mod<sub>e</sub> 32</sup></tt> elements. The resulting index pair can be
	 * used to swap the elements in a matching collection to achieve {@code perfect shuffle} order.
	 * Note that the result is undefined if {@code x} is outside it's range.
	 * @param x the shuffle index within range <tt>[0,2<sup>magnitude mod<sub>e</sub> 32</sup>[</tt>
	 * @param magnitude the magnitude
	 * @return the swap index
	 */
	static public int perfectShuffle (final int x, final int magnitude) {
		return Integer.reverse(x) >>> -magnitude;
	}


	/**
	 * Returns the swap index associated with the given shuffle index {@code x} for a collection of
	 * <tt>2<sup>magnitude mod<sub>e</sub> 64</sup></tt> elements. The resulting index pair can be
	 * used to swap the elements in a matching collection to achieve {@code perfect shuffle} order.
	 * Note that the result is undefined if {@code x} is outside it's range.
	 * @param x the shuffle index within range <tt>[0,2<sup>magnitude mod<sub>e</sub> 64</sup>[</tt>
	 * @param magnitude the magnitude
	 * @return the swap index
	 */
	static public long perfectShuffle (final long x, final int magnitude) {
		return Long.reverse(x) >>> -magnitude;
	}


	//******************************************//
	// floating-point complex number operations //
	//******************************************//

	/**
	 * Returns the {@code real} part <tt>&real;(z)</tt> of the given complex number
	 * <tt>z = x*e<sup>i&sdot;y</sup></tt>.
	 * @param x the absolute value <tt>|z|</tt>
	 * @param y the argument angle <tt>&phi;(z)</tt>
	 * @return the real value <tt>&real;(z) = x&sdot;cos(y)</tt>
	 */
	static public float re (final float x, final float y) {
		if (y == +0) return +x;
		if (y == (float) -Math.PI) return -x;
		if (y == (float) +Math.PI / 2 | y == (float) -Math.PI / 2) return 0;
		return x * (float) Math.cos(y);
	}


	/**
	 * Returns the {@code real} part <tt>&real;(z)</tt> of the given complex number
	 * <tt>z = x*e<sup>i&sdot;y</sup></tt>.
	 * @param x the absolute value <tt>|z|</tt>
	 * @param y the argument angle <tt>&phi;(z)</tt>
	 * @return the real value <tt>&real;(z) = x&sdot;cos(y)</tt>
	 */
	static public double re (final double x, final double y) {
		if (y == +0) return +x;
		if (y == -Math.PI) return -x;
		if (y == +Math.PI / 2 | y == -Math.PI / 2) return 0;
		return x * Math.cos(y);
	}


	/**
	 * Returns the {@code imaginary} part <tt>&image;(z)</tt> of the given complex number
	 * <tt>z = x*e<sup>i&sdot;y</sup></tt>.
	 * @param x the absolute value <tt>|z|</tt>
	 * @param y the argument angle <tt>&phi;(z)</tt>
	 * @return the imaginary value <tt>&image;(z) = x&sdot;sin(y)</tt>
	 */
	static public float im (final float x, final float y) {
		if (y == (float) +Math.PI / 2) return +x;
		if (y == (float) -Math.PI / 2) return -x;
		if (y == +0 | y == (float) -Math.PI) return 0;
		return x * (float) Math.sin(y);
	}


	/**
	 * Returns the {@code imaginary} part <tt>&image;(z)</tt> of the given complex number
	 * <tt>z = x*e<sup>i&sdot;y</sup></tt>.
	 * @param x the absolute value <tt>|z|</tt>
	 * @param y the argument angle <tt>&phi;(z)</tt>
	 * @return the imaginary value <tt>&image;(z) = x&sdot;sin(y)</tt>
	 */
	static public double im (final double x, final double y) {
		if (y == +Math.PI / 2) return +x;
		if (y == -Math.PI / 2) return -x;
		if (y == +0 | y == -Math.PI) return 0;
		return x * Math.sin(y);
	}


	/**
	 * Returns the <tt>absolute value |z|</tt> of the given complex number <tt>z = x+i&sdot;y</tt>.
	 * @param x the real part <tt>&real;(z)</tt>
	 * @param y the imaginary part <tt>&image;(z)</tt>
	 * @return the absolute value <tt>|z| = (&real;(z)<sup>2</sup> +
	 *         &image;(z)<sup>2</sup>)<sup>&half;</sup></tt>
	 */
	static public float abs (final float x, final float y) {
		if (x == 0) return Math.abs(y);
		if (y == 0) return Math.abs(x);
		return (float) Math.sqrt(x*x + y*y);
	}


	/**
	 * Returns the <tt>absolute value |z|</tt> of the given complex number <tt>z = x+i&sdot;y</tt>.
	 * @param x the real part <tt>&real;(z)</tt>
	 * @param y the imaginary part <tt>&image;(z)</tt>
	 * @return the absolute value <tt>|z| = (&real;(z)<sup>2</sup> +
	 *         &image;(z)<sup>2</sup>)<sup>&half;</sup></tt>
	 */
	static public double abs (final double x, final double y) {
		if (x == 0) return Math.abs(y);
		if (y == 0) return Math.abs(x);
		return Math.sqrt(x*x + y*y);
	}


	/**
	 * Returns the <tt>argument angle &phi;(z)</tt> of the given complex number
	 * <tt>z = x+i&sdot;y</tt>.
	 * @param x the real part <tt>&real;(z)</tt>
	 * @param y the imaginary part <tt>&image;(z)</tt>
	 * @return the argument angle <tt> &phi; = atan2(&image;(z),&real;(z))</tt>
	 */
	static public float arg (final float x, final float y) {
		if (y == 0) return (Float.floatToRawIntBits(x) & MASK_FLOAT_SIGN) == 0 ? (float) (TAU * +0 / 8) : (float) (TAU * -4 / 8);
		if (x == 0) return y > 0 ? (float) (TAU * +2 / 8) : (float) (TAU * -2 / 8);
		if (x == +y) return x > 0 ? (float) (TAU * +1 / 8) : (float) (TAU * -3 / 8);
		if (x == -y) return x > 0 ? (float) (TAU * -1 / 8) : (float) (TAU * +3 / 8);
		return (float) Math.atan2(y, x);
	}


	/**
	 * Returns the <tt>argument angle &phi;(z)</tt> of the given complex number {@code z = x+iy}.
	 * @param x the real part <tt>&real;(z)</tt>
	 * @param y the imaginary part <tt>&image;(z)</tt>
	 * @return the argument angle <tt> &phi; = atan2(&image;(z), &real;(z))</tt>
	 */
	static public double arg (final double x, final double y) {
		if (y == 0) return (Double.doubleToRawLongBits(x) & MASK_DOUBLE_SIGN) == 0 ? TAU * +0 / 8 : TAU * -4 / 8;
		if (x == 0) return y > 0 ? TAU * +2 / 8 : TAU * -2 / 8;
		if (x == +y) return x > 0 ? TAU * +1 / 8 : TAU * -3 / 8;
		if (x == -y) return x > 0 ? TAU * -1 / 8 : TAU * +3 / 8;
		return Math.atan2(y, x);
	}
}