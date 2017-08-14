package de.sb.toolbox.math;

import java.util.function.UnaryOperator;
import de.sb.toolbox.Copyright;
import de.sb.toolbox.math.Complex.MutableDoublePrecision;
import de.sb.toolbox.math.Complex.MutableSinglePrecision;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


/**
 * This facade provides additional non-polymorphic operations for mutable
 * complex arguments, both for single and double precision.
 */
@Copyright(year=2013, holders="Sascha Baumeister")
public final class ComplexMath {
	
	/**
	 * Prevents external instantiation.
	 */
	private ComplexMath () {}


	//************************//
	// exponential operations //
	//************************//

	/**
	 * Returns <tt>z<sup>1/n</sup></tt>, i.e. the principal branch solution for the finitely
	 * multi-valued {@code integer} root of a {@code complex} operand {@code z}, using de Moivre's
	 * formula. The multi-valued nature of this operation implies a discontinuity, which is located
	 * at negative real values of the operand {@code z}, except for <tt>n=&plusmn;1</tt> where this
	 * operation is single-valued, and {@code n=0} where no valid branches exist (results in
	 * {@code NaN}).<br />
	 * <br />
	 * The principal branch solution is the one whose argument (positive or negative) is closest to
	 * zero; it's real part is guaranteed to be positive for {@code |n|>1}. The solutions for the
	 * other branches can be obtained by multiplying the principal branch solution with
	 * <tt>e<sup>i&sdot;&tau;&sdot;k/n</sup></i></tt>, using branch index <tt>k&isin;&#x2124;</tt>
	 * within range <tt>[0,|n|[</tt>. When combining this operation with it's inverse, as in
	 * <tt>(z<sup>1/n</sup>)<sup>n</sup> = (z<sup>n</sup>)<sup>1/n</sup> = z</tt>, use<br />
	 * <tt>k = (floor(|z|&sdot;n/&tau;+&half;)%|n|</tt>, adding {@code |n|} if {@code k<0}.<br />
	 * <br />
	 * Note that fractional exponentiation may be performed by first using this operation to
	 * calculate the fraction's divisor root of {@code z} (including choice of the proper branch),
	 * and subsequently raising the result to the power of the fraction's dividend using
	 * {@link #pow(T, long)}.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @param n the integer root
	 * @return the principal value <tt>z<sup>1/n</sup> = </tt>
	 *         <tt>|z|<sup>1/n</sup>&sdot;e<sup>i&sdot;&phi;(z)/n</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T root (final T z, long n) throws NullPointerException {
		if (n == (byte) n) {
			switch ((byte) n) {
				case -3:
					return z.clone().cbrt().inv();
				case -2:
					return z.clone().sqrt().inv();
				case +2:
					return z.clone().sqrt();
				case +3:
					return z.clone().cbrt();
				default:
					break;
			}
		}
		return pow(z, 1f / n);
	}


	/**
	 * Returns <tt>z<sup>1/n</sup></tt>, i.e. the principal branch solution for the finitely
	 * multi-valued {@code integer} root of a {@code complex} operand {@code z}, using de Moivre's
	 * formula. The multi-valued nature of this operation implies a discontinuity, which is located
	 * at negative real values of the operand {@code z}, except for <tt>n=&plusmn;1</tt> where this
	 * operation is single-valued, and {@code n=0} where no valid branches exist (results in
	 * {@code NaN}).<br />
	 * <br />
	 * The principal branch solution is the one whose argument (positive or negative) is closest to
	 * zero; it's real part is guaranteed to be positive for {@code |n|>1}. The solutions for the
	 * other branches can be obtained by multiplying the principal branch solution with
	 * <tt>e<sup>i&sdot;&tau;&sdot;k/n</sup></i></tt>, using branch index <tt>k&isin;&#x2124;</tt>
	 * within range <tt>[0,|n|[</tt>. When combining this operation with it's inverse, as in
	 * <tt>(z<sup>1/n</sup>)<sup>n</sup> = (z<sup>n</sup>)<sup>1/n</sup> = z</tt>, use<br />
	 * <tt>k = (floor(|z|&sdot;n/&tau;+&half;)%|n|</tt>, adding {@code |n|} if {@code k<0}.<br />
	 * <br />
	 * Note that fractional exponentiation may be performed by first using this operation to
	 * calculate the fraction's divisor root of {@code z} (including choice of the proper branch),
	 * and subsequently raising the result to the power of the fraction's dividend using
	 * {@link #pow(T, long)}.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @param n the integer root
	 * @return the principal value <tt>z<sup>1/n</sup> = </tt>
	 *         <tt>|z|<sup>1/n</sup>&sdot;e<sup>i&sdot;&phi;(z)/n</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T root (final T z, long n) throws NullPointerException {
		if (n == (byte) n) {
			switch ((byte) n) {
				case -3:
					return z.clone().cbrt().inv();
				case -2:
					return z.clone().sqrt().inv();
				case +2:
					return z.clone().sqrt();
				case +3:
					return z.clone().cbrt();
				default:
					break;
			}
		}
		return pow(z, 1d / n);
	}


	/**
	 * Returns <tt>z<sup>n</sup></tt>, i.e. a {@code complex} base raised to the power of an
	 * {@code integer} exponent, using de Moivre's formula. The special case <tt>z<sup>0</sup></tt>
	 * calculates to {@code 1} in all cases, even for zero, infinite and {@code NaN} values of
	 * {@code z}, as defined in {@link Math#pow}.<br />
	 * <br />
	 * Note that this operation is finitely cyclic, with
	 * <tt>(z&sdot;e<sup>i&sdot;&tau;&sdot;k/n</sup>)<sup>n</sup> = z<sup>n</sup></tt> for every
	 * integer {@code k} within range {@code [0,|n|[}. This cyclic behavior causes the root
	 * operation (as the inverse of this operation) to become multi-valued in <tt>&#x2102;</tt>,
	 * similarly to the sine function's cyclic behavior causing the arc sine function to become
	 * multi-valued in <tt>&#x211D;</tt>.<br />
	 * <br />
	 * Also note that fractional exponentiation may be performed by first using
	 * {@link #root(T, long)} to calculate the fraction's divisor root of {@code z} (including
	 * choice of the proper branch), and subsequently using this operation to raise the result to
	 * the power of the fraction's dividend.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex base
	 * @param n the integer exponent
	 * @return the value <tt>z<sup>n</sup> = </tt>
	 *         <tt>|z|<sup>n</sup>&sdot;e<sup>i&sdot;&phi;(z)&sdot;n</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T pow (final T z, final long n) throws NullPointerException {
		if (n == (byte) n) {
			switch ((byte) n) {
				case -3:
					return z.clone().cb().inv();
				case -2:
					return z.clone().sq().inv();
				case +2:
					return z.clone().sq();
				case +3:
					return z.clone().cb();
				default:
					break;
			}
		}
		return pow(z, (float) n);
	}


	/**
	 * Returns <tt>z<sup>n</sup></tt>, i.e. a {@code complex} base raised to the power of an
	 * {@code integer} exponent, using de Moivre's formula. The special case <tt>z<sup>0</sup></tt>
	 * calculates to {@code 1} in all cases, even for zero, infinite and {@code NaN} values of
	 * {@code z}, as defined in {@link Math#pow}.<br />
	 * <br />
	 * Note that this operation is finitely cyclic, with
	 * <tt>(z&sdot;e<sup>i&sdot;&tau;&sdot;k/n</sup>)<sup>n</sup> = z<sup>n</sup></tt> for every
	 * integer {@code k} within range {@code [0,|n|[}. This cyclic behavior causes the root
	 * operation (as the inverse of this operation) to become multi-valued in <tt>&#x2102;</tt>,
	 * similarly to the sine function's cyclic behavior causing the arc sine function to become
	 * multi-valued in <tt>&#x211D;</tt>.<br />
	 * <br />
	 * Also note that fractional exponentiation may be performed by first using
	 * {@link #root(T, long)} to calculate the fraction's divisor root of {@code z} (including
	 * choice of the proper branch), and subsequently using this operation to raise the result to
	 * the power of the fraction's dividend.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex base
	 * @param n the integer exponent
	 * @return the value <tt>z<sup>n</sup> = </tt>
	 *         <tt>|z|<sup>n</sup>&sdot;e<sup>i&sdot;&phi;(z)&sdot;n</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T pow (final T z, final long n) throws NullPointerException {
		if (n == (byte) n) {
			switch ((byte) n) {
				case -3:
					return z.clone().cb().inv();
				case -2:
					return z.clone().sq().inv();
				case +2:
					return z.clone().sq();
				case +3:
					return z.clone().cb();
				default:
					break;
			}
		}
		return pow(z, (double) n);
	}


	/**
	 * Returns <tt>z<sup>r</sup></tt>, i.e. a {@code complex} base raised to the power of a
	 * {@code real} exponent, using de Moivre's formula. The special case <tt>z<sup>0</sup></tt>
	 * calculates to {@code 1} in all cases, even for zero, infinite and {@code NaN} values of
	 * {@code z}, as defined in {@link Math#pow}.<br />
	 * <br />
	 * This operation can be both infinitely cyclic and infinitely multi-valued, depending on the
	 * value of the given exponent. Therefore, it is practically unsuitable for branch analysis, for
	 * which both {@link #root(T, long)} and {@link #pow(T, long)} should be preferred. Note that
	 * the multi-valued nature of this operation implies a discontinuity, which is located at
	 * negative real values of the operand {@code z}, except for <tt>r=&plusmn;1</tt> where this
	 * operation is single-valued, and <tt>r&rarr;&plusmn;&infin;</tt> where no valid branches
	 * exist (results in {@code NaN}).
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex base
	 * @param r the real exponent
	 * @return the value <tt>z<sup>n</sup> = </tt>
	 *         <tt>|z|<sup>r</sup>&sdot;e<sup>i&sdot;&phi;(z)&sdot;r</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T pow (final T z, final float r) throws NullPointerException {
		if (r == Double.NEGATIVE_INFINITY | r == Double.POSITIVE_INFINITY) return z.isZero()
			? z.clone().setCartesian(Float.NaN, Float.NaN)
			: z.clone().setCartesian(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		if (r == -1) return z.clone().inv();
		if (r == 0) return z.clone().setCartesian(1, 0);
		if (r == -1) return z;
		return z.clone().setPolar((float) Math.pow(z.abs(), r), z.arg() * r);
	}


	/**
	 * Returns <tt>z<sup>r</sup></tt>, i.e. a {@code complex} base raised to the power of a
	 * {@code real} exponent, using de Moivre's formula. The special case <tt>z<sup>0</sup></tt>
	 * calculates to {@code 1} in all cases, even for zero, infinite and {@code NaN} values of
	 * {@code z}, as defined in {@link Math#pow}.<br />
	 * <br />
	 * This operation can be both infinitely cyclic and infinitely multi-valued, depending on
	 * the value of the given exponent. Therefore, it is practically unsuitable for branch
	 * analysis, for which both {@link #root(T, long)} and {@link #pow(T, long)} should be
	 * preferred. Note that the multi-valued nature of this operation implies a discontinuity,
	 * which is located at negative real values of the operand {@code z}, except for
	 * <tt>r=&plusmn;1</tt> where this operation is single-valued, and
	 * <tt>r&rarr;&plusmn;&infin;</tt> where no valid branches exist (results in {@code NaN}).
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex base
	 * @param r the real exponent
	 * @return the value <tt>z<sup>n</sup> = </tt>
	 *         <tt>|z|<sup>r</sup>&sdot;e<sup>i&sdot;&phi;(z)&sdot;r</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T pow (final T z, final double r) throws NullPointerException {
		if (r == Double.NEGATIVE_INFINITY | r == Double.POSITIVE_INFINITY) return z.isZero()
			? z.clone().setCartesian(Double.NaN, Double.NaN)
			: z.clone().setCartesian(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		if (r == -1) return z.clone().inv();
		if (r == 0) return z.clone().setCartesian(1, 0);
		if (r == +1) return z;
		return z.clone().setPolar(Math.pow(z.abs(), r), z.arg() * r);
	}


	/**
	 * Returns {@code log(z)}, i.e. the principal branch solution for the infinitely multi-valued
	 * natural logarithm (base {@code e}) of a {@code complex} operand. The multi-valued nature
	 * implies a discontinuity, which is located at negative real values of the operand {@code z}
	 * .</br />
	 * <br />
	 * The principal branch solution is the one whose imaginary part is guaranteed to be within
	 * range <tt>[-&pi;,+&pi;[</tt>. The solutions for the other branches may be obtained by adding
	 * <tt>i&sdot;&tau;&sdot;k</tt> (branch Index {@code k} &isin; &#x2124;) to the principal branch
	 * solution. Regardless of the branch chosen, the real part of the result is guaranteed to be
	 * positive.<br />
	 * <br />
	 * When combining this operation with it's inverse, as in <tt>log(exp(z)) = exp(log(z)) =
	 * z</tt>, choose branch <tt>k = floor(&image;(z)/&tau; + &half;)</tt> for correct branch
	 * alignment.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the principal value <tt>log(z) = log|z|+i&sdot;&phi;(z)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T log (final T z) throws NullPointerException {
		return z.clone().setCartesian((float) Math.log(z.abs()), z.arg());
	}


	/**
	 * Returns {@code log(z)}, i.e. the principal branch solution for the infinitely multi-valued
	 * natural logarithm (base {@code e}) of a {@code complex} operand. The multi-valued nature
	 * implies a discontinuity, which is located at negative real values of the operand {@code z}
	 * .</br />
	 * <br />
	 * The principal branch solution is the one whose imaginary part is guaranteed to be within
	 * range <tt>[-&pi;,+&pi;[</tt>. The solutions for the other branches may be obtained by adding
	 * <tt>i&sdot;&tau;&sdot;k</tt> (branch Index {@code k} &isin; &#x2124;) to the principal branch
	 * solution. Regardless of the branch chosen, the real part of the result is guaranteed to be
	 * positive.<br />
	 * <br />
	 * When combining this operation with it's inverse, as in <tt>log(exp(z)) = exp(log(z)) =
	 * z</tt>, choose branch <tt>k = floor(&image;(z)/&tau; + &half;)</tt> for correct branch
	 * alignment.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the principal value <tt>log(z) = log|z|+i&sdot;&phi;(z)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T log (final T z) throws NullPointerException {
		return z.clone().setCartesian(Math.log(z.abs()), z.arg());
	}


	/**
	 * Returns <tt>log<sub>b</sub>(z)</tt>, i.e. the principal branch solution for the infinitely
	 * multi-valued {@code real} based logarithm of a {@code complex} operand. The multi-valued
	 * nature implies a discontinuity, which is located at negative real values of the operand
	 * {@code z}. The exact behavior of this operation depends on the nature of the base {@code b}:
	 * <ul>
	 * <li>if a positive base {@code b} equals the operand {@code z}, then the result is always one.
	 * </li>
	 * <li>if the base {@code b} is {@code 0}, then the result is zero.</li>
	 * <li>if the base {@code b} is {@code +1}, then the result is infinite and/or NaN.</li>
	 * <li>if the base {@code b} is Euler's number {@code e}, then see {@link #log(T)} for details.
	 * </li>
	 * <li>if the base {@code b} is any other positive number, then the principal branch solution is
	 * the one whose imaginary part is guaranteed to be within range
	 * <i>[-&pi;/log(b),+&pi;/log(b)[</i>. The solutions for the other branches may be obtained by
	 * adding <i>i&sdot;&tau;&sdot;k/log(b)</i> (branch index <tt>k &isin; &#x2124;</tt>) to the
	 * principal branch solution. Regardless of the branch chosen, the real part of the result is
	 * guaranteed to be positive.</li>
	 * <li>if the base {@code b} is {@code -1}, then the solutions for the other branches may be
	 * obtained by adding <tt>2&sdot;k</tt> (with branch index <tt>k &isin; &#x2124;</tt>) to the
	 * principal branch solution.</li>
	 * <li>if the base {@code b} is any other negative number, then the solutions for the other
	 * branches are also obtainable, but the branch correction becomes more complicated.</li>
	 * </ul>
	 * When combining this operation with it's inverse, as in <i>log(b, exp(b,z)) = exp(b, log(b,z))
	 * = z</i>, choose
	 * <ul>
	 * <li>if the base {@code b} is positive:
	 * <tt>k = floor(&half;+&image;(z)/&tau;&sdot;log(b))</tt></li>
	 * <li>if the base {@code b = -1}:
	 * <ul>
	 * <li><tt>k = floor(&half;(&real;(z)+1))</tt> if &real;(z) < -1</li>
	 * <li><tt>k = ceil(&half;(&real;(z)-1))</tt> if &real;(z) >= -1</li>
	 * </ul>
	 * </ul>
	 * @param <T> the declaration type of the complex argument and result
	 * @param b the real base
	 * @param z the complex operand
	 * @return the principal value <tt>log<sub>b</sub>(z) = log(z)/log(b) = </tt>
	 *         <tt>(log|z|+i&sdot;&phi;(z))/log(b)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T log (final float b, final T z) throws NullPointerException {
		if (b == (float) Math.E) return log(z);
		if (z.isReal() && z.re() == b) return z.clone().setCartesian(1, 0);

		final float logB = (float) Math.log(Math.abs(b));
		final T logZ = log(z);
		return b >= 0 ? logZ.div(logB) : logZ.div(z.clone().setCartesian(logB, (float) -Math.PI));
	}


	/**
	 * Returns <tt>log<sub>b</sub>(z)</tt>, i.e. the principal branch solution for the infinitely
	 * multi-valued {@code real} based logarithm of a {@code complex} operand. The multi-valued
	 * nature implies a discontinuity, which is located at negative real values of the operand
	 * {@code z}. The exact behavior of this operation depends on the nature of the base {@code b}:
	 * <ul>
	 * <li>if a positive base {@code b} equals the operand {@code z}, then the result is always one.
	 * </li>
	 * <li>if the base {@code b} is {@code 0}, then the result is zero.</li>
	 * <li>if the base {@code b} is {@code +1}, then the result is infinite and/or NaN.</li>
	 * <li>if the base {@code b} is Euler's number {@code e}, then see {@link #log(T)} for details.
	 * </li>
	 * <li>if the base {@code b} is any other positive number, then the principal branch solution is
	 * the one whose imaginary part is guaranteed to be within range
	 * <i>[-&pi;/log(b),+&pi;/log(b)[</i>. The solutions for the other branches may be obtained by
	 * adding <i>i&sdot;&tau;&sdot;k/log(b)</i> (branch index <tt>k &isin; &#x2124;</tt>) to the
	 * principal branch solution. Regardless of the branch chosen, the real part of the result is
	 * guaranteed to be positive.</li>
	 * <li>if the base {@code b} is {@code -1}, then the solutions for the other branches may be
	 * obtained by adding <tt>2&sdot;k</tt> (with branch index <tt>k &isin; &#x2124;</tt>) to the
	 * principal branch solution.</li>
	 * <li>if the base {@code b} is any other negative number, then the solutions for the other
	 * branches are also obtainable, but the branch correction becomes more complicated.</li>
	 * </ul>
	 * When combining this operation with it's inverse, as in <i>log(b, exp(b,z)) = exp(b, log(b,z))
	 * = z</i>, choose
	 * <ul>
	 * <li>if the base {@code b} is positive:
	 * <tt>k = floor(&half;+&image;(z)/&tau;&sdot;log(b))</tt></li>
	 * <li>if the base {@code b = -1}:
	 * <ul>
	 * <li><tt>k = floor(&half;(&real;(z)+1))</tt> if &real;(z) < -1</li>
	 * <li><tt>k = ceil(&half;(&real;(z)-1))</tt> if &real;(z) >= -1</li>
	 * </ul>
	 * </ul>
	 * @param <T> the declaration type of the complex argument and result
	 * @param b the real base
	 * @param z the complex operand
	 * @return the principal value <tt>log<sub>b</sub>(z) = log(z)/log(b) = </tt>
	 *         <tt>(log|z|+i&sdot;&phi;(z))/log(b)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T log (final double b, final T z) throws NullPointerException {
		if (b == Math.E) return log(z);
		if (z.isReal() && z.re() == b) return z.clone().setCartesian(1, 0);

		final double logB = Math.log(Math.abs(b));
		final T logZ = log(z);
		return b >= 0 ? logZ.div(logB) : logZ.div(z.clone().setCartesian(logB, -Math.PI));
	}


	/**
	 * Returns <tt>log<sub>z<sub>1</sub></sub>(z<sub>2</sub>)</tt>, i.e. the principal branch
	 * solution for the infinitely multi-valued {@code complex} based logarithm of a {@code complex}
	 * operand. The multi-valued nature implies a discontinuity, which is located at negative real
	 * values of the operand <tt>z<sub>2</sub></tt>. The exact behavior of this operation depends on
	 * the nature of the base <tt>z<sub>1</sub></tt>:
	 * <ul>
	 * <li>if the base z<sub>1</sub> equals the operand <tt>z<sub>2</sub></tt>, then the result is
	 * always one.</li>
	 * <li>if the base z<sub>1</sub> is Euler's number {@code e}, then see {@link #log(T)} for
	 * details.</li>
	 * <li>if the base z<sub>1</sub> is a {@code real} number, then see {@link #log(float, T)} for
	 * details.</li>
	 * <li>if the base z<sub>1</sub> is a {@code complex} number, the branching behavior of this
	 * operation is similar to {@link #log(float, T)} when passing negative bases. However, branch
	 * determination and correction become much more complicated.</li>
	 * </ul>
	 * @param <T> the declaration type of the complex arguments and result
	 * @param z1 the complex base
	 * @param z2 the complex operand
	 * @return the principal value <tt>log<sub>z<sub>1</sub></sub>(z<sub>2</sub>) =
	 *         log(z<sub>2</sub>)/log(z<sub>1</sub>) = 
	 *         (log|z<sub>2</sub>|+i&sdot;&phi;(z<sub>2</sub>)) /
	 *         (log|z<sub>1</sub>|+i&sdot;&phi;(z<sub>1</sub>))</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T log (final T z1, final T z2) throws NullPointerException {
		if (z1.isReal()) return log(z1.re(), z2);
		if (z1 == z2 || z1.equals(z2)) return z2.clone().setCartesian(1, 0);
		return log(z2).div(log(z1));
	}


	/**
	 * Returns <tt>log<sub>z<sub>1</sub></sub>(z<sub>2</sub>)</tt>, i.e. the principal branch
	 * solution for the infinitely multi-valued {@code complex} based logarithm of a {@code complex}
	 * operand. The multi-valued nature implies a discontinuity, which is located at negative real
	 * values of the operand <tt>z<sub>2</sub></tt>. The exact behavior of this operation depends on
	 * the nature of the base <tt>z<sub>1</sub></tt>:
	 * <ul>
	 * <li>if the base z<sub>1</sub> equals the operand <tt>z<sub>2</sub></tt>, then the result is
	 * always one.</li>
	 * <li>if the base z<sub>1</sub> is Euler's number {@code e}, then see {@link #log(T)} for
	 * details.</li>
	 * <li>if the base z<sub>1</sub> is a {@code real} number, then see {@link #log(double, T)} for
	 * details.</li>
	 * <li>if the base z<sub>1</sub> is a {@code complex} number, the branching behavior of this
	 * operation is similar to {@link #log(double, T)} when passing negative bases. However, branch
	 * determination and correction become much more complicated.</li>
	 * </ul>
	 * @param <T> the declaration type of the complex arguments and result
	 * @param z1 the complex base
	 * @param z2 the complex operand
	 * @return the principal value <tt>log<sub>z<sub>1</sub></sub>(z<sub>2</sub>) =
	 *         log(z<sub>2</sub>)/log(z<sub>1</sub>) = 
	 *         (log|z<sub>2</sub>|+i&sdot;&phi;(z<sub>2</sub>)) /
	 *         (log|z<sub>1</sub>|+i&sdot;&phi;(z<sub>1</sub>))</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T log (final T z1, final T z2) throws NullPointerException {
		if (z1.isReal()) return log(z1.re(), z2);
		if (z1 == z2 || z1.equals(z2)) return z2.clone().setCartesian(1, 0);
		return log(z2).div(log(z1));
	}


	/**
	 * Returns <tt>e<sup>z</sup></tt>, i.e. Euler's number raised to the power of a {@code complex}
	 * operand.<br />
	 * <br />
	 * Note that this operation is infinitely cyclic, with
	 * <tt>e<sup>z+i&sdot;&tau;&sdot;k</sup> = e<sup>z</sup></tt> for any integer k. This behavior
	 * causes the logarithm (as the inverse of this operation) to become multi-valued in
	 * <tt>&#x2102;</tt>, similarly to the sine function's cyclic behavior causing the arc sine
	 * function to become multi-valued in <tt>&#x211D;</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex exponent
	 * @return the value <tt>e<sup>z</sup> = </tt>
	 *         <tt>e<sup>&real;(z)</sup>&sdot;e<sup>i&sdot;&image;(z)</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T exp (final T z) throws NullPointerException {
		return z.clone().setPolar((float) Math.exp(z.re()), z.im());
	}


	/**
	 * Returns <tt>e<sup>z</sup></tt>, i.e. Euler's number raised to the power of a {@code complex}
	 * operand.<br />
	 * <br />
	 * Note that this operation is infinitely cyclic, with
	 * <tt>e<sup>z+i&sdot;&tau;&sdot;k</sup> = e<sup>z</sup></tt> for any integer k. This behavior
	 * causes the logarithm (as the inverse of this operation) to become multi-valued in
	 * <tt>&#x2102;</tt>, similarly to the sine function's cyclic behavior causing the arc sine
	 * function to become multi-valued in <tt>&#x211D;</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex exponent
	 * @return the value <tt>e<sup>z</sup> = </tt>
	 *         <tt>e<sup>&real;(z)</sup>&sdot;e<sup>i&sdot;&image;(z)</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T exp (final T z) throws NullPointerException {
		return z.clone().setPolar(Math.exp(z.re()), z.im());
	}


	/**
	 * Returns <tt>b<sup>z</sup></tt>, i.e. a {@code real} base raised to the power of a
	 * {@code complex} exponent. The result of <tt>1<sup>z</sup></tt> and <tt>b<sup>0</sup></tt> is
	 * always one, by definition including the special case <tt>0<sup>0</sup></tt> . The result of
	 * <tt>0<sup>z</sup></tt> is zero unless {@code z} is zero.<br />
	 * <br />
	 * Note that this operation is infinitely cyclic except for special bases like zero, one or
	 * infinity, and special exponents like 0 or infinity. This behavior causes the logarithm (as
	 * the inverse of this operation) to become multi-valued in <tt>&#x2102;</tt>, similarly to the
	 * sine function's cyclic behavior causing the arc sine function to become multi-valued in
	 * <tt>&#x211D;</tt>. The exact cycle behavior depends on the nature of the base {@code b}:
	 * <ul>
	 * <li>if the given base {@code b} is strictly positive except {@code +1}, then the cycling is
	 * simply vertical, with<br />
	 * <i>exp(b, z + i&sdot;&tau;&sdot;k / log(b)) = exp(b, z)</i> for any integer {@code k}.</li>
	 * <li>if the given base is {@code b=-1}, then the cycling is simply horizontal, with <i>exp(b,
	 * z + 2&sdot;k) = exp(b, z)</i> for any integer {@code k}.</li>
	 * <li>if the given base {@code b} is strictly negative except {@code -1}, then the cycling is
	 * obliquely rotated from the vertical by an angle between ]0,&pi;[, depending on the absolute
	 * value of {@code b}.</li>
	 * </ul>
	 * @param <T> the declaration type of the complex argument and result
	 * @param b the (positive) real base
	 * @param z the complex exponent
	 * @return the value <tt>b<sup>z</sup> = </tt><tt>e<sup>log(b)&sdot;z</sup> = </tt>
	 *         <tt>e<sup>log(b)&sdot;&real;(z)</sup>&sdot;e<sup>i&sdot;log(b)&sdot;&image;(z)</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T exp (final float b, final T z) throws NullPointerException {
		final T clone = z.clone();

		if (b < 0) return exp(clone.setCartesian(b, 0), z);
		if (b == 0) return clone.setCartesian(z.isZero() ? 1 : 0, 0);
		if (b == 1) return clone.setCartesian(1, 0);
		if (b == (float) Math.E) return exp(z);

		final float logB = (float) Math.log(b);
		return clone.setPolar((float) Math.exp(logB * z.re()), logB * z.im());
	}


	/**
	 * Returns <tt>b<sup>z</sup></tt>, i.e. a {@code real} base raised to the power of a
	 * {@code complex} exponent. The result of <tt>1<sup>z</sup></tt> and <tt>b<sup>0</sup></tt> is
	 * always one, by definition including the special case <tt>0<sup>0</sup></tt> . The result of
	 * <tt>0<sup>z</sup></tt> is zero unless {@code z} is zero.<br />
	 * <br />
	 * Note that this operation is infinitely cyclic except for special bases like zero, one or
	 * infinity, and special exponents like 0 or infinity. This behavior causes the logarithm (as
	 * the inverse of this operation) to become multi-valued in <tt>&#x2102;</tt>, similarly to the
	 * sine function's cyclic behavior causing the arc sine function to become multi-valued in
	 * <tt>&#x211D;</tt>. The exact cycle behavior depends on the nature of the base {@code b}:
	 * <ul>
	 * <li>if the given base {@code b} is strictly positive except {@code +1}, then the cycling is
	 * simply vertical, with<br />
	 * <i>exp(b, z + i&sdot;&tau;&sdot;k / log(b)) = exp(b, z)</i> for any integer {@code k}.</li>
	 * <li>if the given base is {@code b=-1}, then the cycling is simply horizontal, with <i>exp(b,
	 * z + 2&sdot;k) = exp(b, z)</i> for any integer {@code k}.</li>
	 * <li>if the given base {@code b} is strictly negative except {@code -1}, then the cycling is
	 * obliquely rotated from the vertical by an angle between ]0,&pi;[, depending on the absolute
	 * value of {@code b}.</li>
	 * </ul>
	 * @param <T> the declaration type of the complex argument and result
	 * @param b the (positive) real base
	 * @param z the complex exponent
	 * @return the value <tt>b<sup>z</sup> = </tt><tt>e<sup>log(b)&sdot;z</sup> = </tt>
	 *         <tt>e<sup>log(b)&sdot;&real;(z)</sup>&sdot;e<sup>i&sdot;log(b)&sdot;&image;(z)</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T exp (final double b, final T z) throws NullPointerException {
		final T clone = z.clone();

		if (b < 0) return exp(clone.setCartesian(b, 0), z);
		if (b == 0) return clone.setCartesian(z.isZero() ? 1 : 0, 0);
		if (b == 1) return clone.setCartesian(1, 0);
		if (b == Math.E) return exp(z);

		final double logB = Math.log(b);
		return clone.setPolar(Math.exp(logB * z.re()), logB * z.im());
	}


	/**
	 * Returns <tt>z<sub>1</sub><sup>z<sub>2</sub></sup></tt>, i.e. the principal branch solution
	 * for a {@code complex} base raised to the power of a {@code complex} exponent, which is
	 * infinitely multi-valued in <tt>&#x2102;</tt>. The exact behavior of this operation depends on
	 * the nature of the operands:
	 * <ul>
	 * <li>If the base <tt>z<sub>1</sub></tt> is positive {@code real} number, then see
	 * {@link #exp(float, T)} for details.</li>
	 * <li>If the exponent <tt>z<sub>2</sub></tt> is an {@code integer} number, then see
	 * {@link #pow(T, int)} for details.</li>
	 * <li>If the exponent <tt>z<sub>2</sub></tt> is or approximates a unit fraction, then see
	 * {@link #root(T, int)} for details.</li>
	 * <li>If the exponent <tt>z<sub>2</sub></tt> is a {@code real} number, but neither an integer
	 * nor a unit fraction, then it is technically nevertheless a dyadic fraction in lowest terms
	 * because any IEEE floating point number is. Therefore, this operation behaves as if
	 * {@link #root(T, int)} and {@link #pow(T, int)} had been combined, but without the guarantee
	 * that the principal branch solution returned is the one whose argument (positive or negative)
	 * is closest to zero.</li>
	 * <li>Otherwise this operation displays a more delicate behavior: If the base is set constant
	 * and the exponent is varied, then this operation shows a cyclic pattern similarly to
	 * {@link #pow(T, int)}, but rotated by varying degrees. If the base is varied and the exponent
	 * set constant, then this operation displays a pattern similarly to {@link #root(T, int)}, but
	 * rotated by varying degrees resulting in cycle or spiral patterns. If z<sub>1</sub> = z
	 * <sub>2</sub> or z<sub>1</sub> = -z<sub>2</sub>, then this operation displays a cyclic pattern
	 * similarly to {@link #pow(T, int)}, but bended around the origin. If z<sub>1</sub> = z
	 * <sub>2</sub><sup>-1</sup>, then this operation displays a cyclic shamrock pattern.</li>
	 * </ul>
	 * @param <T> the declaration type of the complex arguments and result
	 * @param z1 the complex base
	 * @param z2 the complex exponent
	 * @return the value <tt>z<sub>1</sub><sup>z<sub>2</sub></sup></tt> = </tt>
	 *         <tt>e<sup>log(z<sub>1</sub>)&sdot;z<sub>2</sub></sup> = </tt>
	 *         <tt>e<sup>(log|z<sub>1</sub>|+i&sdot;&phi;(z<sub>1</sub>)) &sdot;
	 *         (&real;(z<sub>2</sub>)+i&sdot;&image;(z<sub>2</sub>))</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T exp (final T z1, final T z2) throws NullPointerException {
		if (z1.isReal()) {
			final float re = z1.re();
			if (re >= 0) return exp(re, z2);
		}

		if (z2.isReal()) {
			final float re = z2.re();
			if (re == (long) re) return pow(z1, (long) re);
			final float inv = 1 / re;
			if (inv == (long) inv) return root(z1, (long) inv);
		}

		return exp(log(z1).mul(z2));
	}


	/**
	 * Returns <tt>z<sub>1</sub><sup>z<sub>2</sub></sup></tt>, i.e. the principal branch solution
	 * for a {@code complex} base raised to the power of a {@code complex} exponent, which is
	 * infinitely multi-valued in <tt>&#x2102;</tt>. The exact behavior of this operation depends on
	 * the nature of the operands:
	 * <ul>
	 * <li>If the base <tt>z<sub>1</sub></tt> is positive {@code real} number, then see
	 * {@link #exp(double, T)} for details.</li>
	 * <li>If the exponent <tt>z<sub>2</sub></tt> is an {@code integer} number, then see
	 * {@link #pow(T, int)} for details.</li>
	 * <li>If the exponent <tt>z<sub>2</sub></tt> is or approximates a unit fraction, then see
	 * {@link #root(T, int)} for details.</li>
	 * <li>If the exponent <tt>z<sub>2</sub></tt> is a {@code real} number, but neither an integer
	 * nor a unit fraction, then it is technically nevertheless a dyadic fraction in lowest terms
	 * because any IEEE floating point number is. Therefore, this operation behaves as if
	 * {@link #root(T, int)} and {@link #pow(T, int)} had been combined, but without the guarantee
	 * that the principal branch solution returned is the one whose argument (positive or negative)
	 * is closest to zero.</li>
	 * <li>Otherwise this operation displays a more delicate behavior: If the base is set constant
	 * and the exponent is varied, then this operation shows a cyclic pattern similarly to
	 * {@link #pow(T, int)}, but rotated by varying degrees. If the base is varied and the exponent
	 * set constant, then this operation displays a pattern similarly to {@link #root(T, int)}, but
	 * rotated by varying degrees resulting in cycle or spiral patterns. If z<sub>1</sub> = z
	 * <sub>2</sub> or z<sub>1</sub> = -z<sub>2</sub>, then this operation displays a cyclic pattern
	 * similarly to {@link #pow(T, int)}, but bended around the origin. If z<sub>1</sub> = z
	 * <sub>2</sub><sup>-1</sup>, then this operation displays a cyclic shamrock pattern.</li>
	 * </ul>
	 * @param <T> the declaration type of the complex arguments and result
	 * @param z1 the complex base
	 * @param z2 the complex exponent
	 * @return the value <tt>z<sub>1</sub><sup>z<sub>2</sub></sup></tt> = </tt>
	 *         <tt>e<sup>log(z<sub>1</sub>)&sdot;z<sub>2</sub></sup> = </tt>
	 *         <tt>e<sup>(log|z<sub>1</sub>|+i&sdot;&phi;(z<sub>1</sub>)) &sdot;
	 *         (&real;(z<sub>2</sub>)+i&sdot;&image;(z<sub>2</sub>))</sup></tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T exp (final T z1, final T z2) throws NullPointerException {
		if (z1.isReal()) {
			final double re = z1.re();
			if (re >= 0) return exp(re, z2);
		}

		if (z2.isReal()) {
			final double re = z2.re();
			if (re == (long) re) return pow(z1, (long) re);
			final double inv = 1 / re;
			if (inv == (long) inv) return root(z1, (long) inv);
		}

		return exp(log(z1).mul(z2));
	}



	//**************************//
	// trigonometric operations //
	//**************************//

	/**
	 * Returns <tt>sin(z) = -i&sdot;&half;(e<sup>+iz</sup>-e<sup>-iz</sup>) = </tt>
	 * <tt>sin(&real;(z))&sdot;cosh(&image;(z)) + i&sdot;cos(&real;(z))&sdot;sinh(&image;(z))</tt>
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value
	 *         <tt>sin(&real;(z))&sdot;&half;(e<sup>+&image;(z)</sup>+e<sup>-&image;(z)</sup>) + </tt>
	 *         <tt>i&sdot;cos(&real;(z))&sdot;&half;(e<sup>+&image;(z)</sup>-e<sup>-&image;(z)</sup>)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T sin (final T z) throws NullPointerException {
		final float sin = (float) Math.sin(z.re()), cos = (float) Math.cos(z.re());
		final float pex = (float) Math.exp(z.im()), nex = 1 / pex;
		return z.clone().setCartesian(+.5f * (pex + nex) * sin, +.5f * (pex - nex) * cos);
	}


	/**
	 * Returns <tt>sin(z) = -i&sdot;&half;(e<sup>+iz</sup>-e<sup>-iz</sup>) = </tt>
	 * <tt>sin(&real;(z))&sdot;cosh(&image;(z)) + i&sdot;cos(&real;(z))&sdot;sinh(&image;(z))</tt>
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value
	 *         <tt>sin(&real;(z))&sdot;&half;(e<sup>+&image;(z)</sup>+e<sup>-&image;(z)</sup>) + </tt>
	 *         <tt>i&sdot;cos(&real;(z))&sdot;&half;(e<sup>+&image;(z)</sup>-e<sup>-&image;(z)</sup>)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T sin (final T z) throws NullPointerException {
//		return z.clone().setCartesian(+Math.sin(z.re()) * Math.cosh(z.im()), +Math.cos(z.re()) * Math.sinh(z.im()));
		final double sin = Math.sin(z.re()), cos = Math.cos(z.re());
		final double pex = Math.exp(z.im()), nex = 1 / pex;
		return z.clone().setCartesian(+.5d * (pex + nex) * sin, +.5d * (pex - nex) * cos);
	}


	/**
	 * Returns <tt>cos(z) = &half;(e<sup>+iz</sup>+e<sup>-iz</sup>) = </tt>
	 * <tt>cos(&real;(z))&sdot;cosh(&image;(z)) - i&sdot;sin(&real;(z))&sdot;sinh(&image;(z))</tt>
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value
	 *         <tt>cos(&real;(z))&sdot;&half;(e<sup>+&image;(z)</sup>+e<sup>-&image;(z)</sup>) + </tt>
	 *         <tt>i&sdot;sin(&real;(z))&sdot;&half;(e<sup>+&image;(z)</sup>-e<sup>-&image;(z)</sup>)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T cos (final T z) throws NullPointerException {
		final float sin = (float) Math.sin(z.re()), cos = (float) Math.cos(z.re());
		final float pex = (float) Math.exp(z.im()), nex = 1 / pex;
		return z.clone().setCartesian(+.5f * (pex + nex) * cos, -.5f * (pex - nex) * sin);
	}


	/**
	 * Returns <tt>cos(z) = &half;(e<sup>+iz</sup>+e<sup>-iz</sup>) = </tt>
	 * <tt>cos(&real;(z))&sdot;cosh(&image;(z)) - i&sdot;sin(&real;(z))&sdot;sinh(&image;(z))</tt>
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value
	 *         <tt>cos(&real;(z))&sdot;&half;(e<sup>+&image;(z)</sup>+e<sup>-&image;(z)</sup>) - </tt>
	 *         <tt>i&sdot;sin(&real;(z))&sdot;&half;(e<sup>+&image;(z)</sup>-e<sup>-&image;(z)</sup>)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T cos (final T z) throws NullPointerException {
		// return z.clone().setCartesian(+Math.cos(z.re()) * Math.cosh(z.im()),-Math.sin(z.re()) * Math.sinh(z.im()));
		final double sin = Math.sin(z.re()), cos = Math.cos(z.re());
		final double pex = Math.exp(z.im()), nex = 1 / pex;
		return z.clone().setCartesian(+.5d * (pex + nex) * cos, -.5d * (pex - nex) * sin);
	}


	/**
	 * Returns
	 * <tt>tan(z) = -i&sdot;(e<sup>+iz</sup>-e<sup>-iz</sup>)/(e<sup>+iz</sup>+e<sup>-iz</sup>)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <br />
	 *         <tt>(sin(&real;(z))&sdot;(e<sup>+&image;(z)</sup>+e<sup>-&image;(z)</sup>) + </tt>
	 *         <tt>cos(&real;(z))&sdot;(e<sup>+&image;(z)</sup>-e<sup>-&image;(z)</sup>)) / </tt><br />
	 *         <tt>(cos(&real;(z))&sdot;(e<sup>+&image;(z)</sup>+e<sup>-&image;(z)</sup>) - </tt>
	 *         <tt>sin(&real;(z))&sdot;(e<sup>+&image;(z)</sup>-e<sup>-&image;(z)</sup>))</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T tan (final T z) throws NullPointerException {
		final float sin = (float) Math.sin(z.re()), cos = (float) Math.cos(z.re());
		final float pex = (float) Math.exp(z.im()), nex = 1 / pex;
		final T dividend = z.clone().setCartesian(+sin * (pex + nex), +cos * (pex - nex));
		final T divisor  = z.clone().setCartesian(+cos * (pex + nex), -sin * (pex - nex));
		return dividend.div(divisor);
	}


	/**
	 * Returns
	 * <tt>tan(z) = -i&sdot;(e<sup>+iz</sup>-e<sup>-iz</sup>)/(e<sup>+iz</sup>+e<sup>-iz</sup>)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <br />
	 *         <tt>(sin(&real;(z))&sdot;(e<sup>+&image;(z)</sup>+e<sup>-&image;(z)</sup>) + </tt>
	 *         <tt>cos(&real;(z))&sdot;(e<sup>+&image;(z)</sup>-e<sup>-&image;(z)</sup>)) / </tt><br />
	 *         <tt>(cos(&real;(z))&sdot;(e<sup>+&image;(z)</sup>+e<sup>-&image;(z)</sup>) - </tt>
	 *         <tt>sin(&real;(z))&sdot;(e<sup>+&image;(z)</sup>-e<sup>-&image;(z)</sup>))</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T tan (final T z) throws NullPointerException {
		final double sin = Math.sin(z.re()), cos = Math.cos(z.re());
		final double pex = Math.exp(z.im()), nex = 1 / pex;
		final T dividend = z.clone().setCartesian(+sin * (pex + nex), +cos * (pex - nex));
		final T divisor  = z.clone().setCartesian(+cos * (pex + nex), -sin * (pex - nex));
		return dividend.div(divisor);
	}


	/**
	 * Returns the {@code arc sine} of {@code z}, i.e. <tt>asin(z) = </tt>
	 * <tt>-i&sdot;log(i&sdot;z &plusmn; (1-z<sup>2</sup>)<sup>&half;</sup>) = </tt><br />
	 * <tt>&half;&pi;-i&sdot;log(z &plusmn; (z<sup>2</sup>-1)<sup>&half;</sup>)) = &half;&pi;-acos(z)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>&half;&pi;-acos(z)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T asin (final T z) throws NullPointerException {
		return acos(z).neg().add((float) Math.PI / 2);
	}


	/**
	 * Returns the {@code arc sine} of {@code z}, i.e. <tt>asin(z) = </tt>
	 * <tt>-i&sdot;log(i&sdot;z &plusmn; (1-z<sup>2</sup>)<sup>&half;</sup>)) = </tt><br />
	 * <tt>&half;&pi;-i&sdot;log(z &plusmn; (z<sup>2</sup>-1)<sup>&half;</sup>)) = &half;&pi;-acos(z)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>&half;&pi;-acos(z)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T asin (final T z) throws NullPointerException {
		return acos(z).neg().add(Math.PI / 2);
	}


	/**
	 * Returns the {@code arc cosine} of {@code z}, i.e. <tt>acos(z) = </tt>
	 * <tt>-i&sdot;log(z &plusmn; (z<sup>2</sup>-1)<sup>&half;</sup>) = &half;&pi;-asin(z)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>-i&sdot;log(z &plusmn; (z<sup>2</sup>-1)<sup>&half;</sup>)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T acos (final T z) throws NullPointerException {
		final T sqrt = z.clone().sq().sub(1).sqrt();
		if (z.re() > 0 & z.im() < 0 | z.re() < 0 & z.im() > 0) sqrt.neg();
		return log(sqrt.add(z)).idiv();
	}


	/**
	 * Returns the {@code arc cosine} of {@code z}, i.e. <tt>acos(z) = </tt>
	 * <tt>-i&sdot;log(z &plusmn; (z<sup>2</sup>-1)<sup>&half;</sup>) = = &half;&pi;-asin(z)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>-i&sdot;log(z &plusmn; (z<sup>2</sup>-1)<sup>&half;</sup>)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T acos (final T z) throws NullPointerException {
		final T sqrt = z.clone().sq().sub(1).sqrt();
		if (z.re() > 0 & z.im() < 0 | z.re() < 0 & z.im() > 0) sqrt.neg();
		return log(sqrt.add(z)).idiv();
	}


	/**
	 * Returns the {@code arc tangent} of {@code z}, i.e.
	 * <tt>atan(z) = &half;i&sdot;log((1-iz) / 1+iz)).
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>&half;i&sdot;log((1-iz) / 1+iz))</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T atan (final T z) throws NullPointerException {
		return log(z.clone().idiv().add(1).div(z.clone().imul().add(1))).imul().mul(.5f);
	}


	/**
	 * Returns the {@code arc tangent} of {@code z}, i.e.
	 * <tt>atan(z) = &half;i&sdot;log((1-iz) / 1+iz)).
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>&half;i&sdot;log((1-iz) / 1+iz))</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T atan (final T z) throws NullPointerException {
		return log(z.clone().idiv().add(1).div(z.clone().imul().add(1))).imul().mul(.5d);
	}



	//***********************//
	// hyperbolic operations //
	//***********************//

	/**
	 * Returns the hyperbolic {@code sine} of {@code z}, i.e.
	 * <tt>sinh(z) = &half;(e<sup>z</sup>-e<sup>-z</sup>) = </tt>
	 * <tt>i&sdot;sin(-i&sdot;z) = -i&sdot;sin(i&sdot;z)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>-i&sdot;sin(i&sdot;z)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @see #sin(T)
	 */
	static public <T extends MutableSinglePrecision<T>> T sinh (final T z) throws NullPointerException {
		return sin(z.clone().imul()).idiv();
	}


	/**
	 * Returns the hyperbolic {@code sine} of {@code z}, i.e.
	 * <tt>sinh(z) = &half;(e<sup>z</sup>-e<sup>-z</sup>) = </tt>
	 * <tt>i&sdot;sin(-i&sdot;z) = -i&sdot;sin(i&sdot;z)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>-i&sdot;sin(i&sdot;z)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @see #sin(T)
	 */
	static public <T extends MutableDoublePrecision<T>> T sinh (final T z) throws NullPointerException {
		return sin(z.clone().imul()).idiv();
	}


	/**
	 * Returns the hyperbolic {@code cosine} of {@code z}, i.e.
	 * <tt>cosh(z) = &half;(e<sup>z</sup>+e<sup>-z</sup>) = cos(-i&sdot;z) = cos(i&sdot;z)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>cos(i&sdot;z)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @see #cos(T)
	 */
	static public <T extends MutableSinglePrecision<T>> T cosh (final T z) throws NullPointerException {
		return cos(z.clone().imul());
	}


	/**
	 * Returns the hyperbolic {@code cosine} of {@code z}, i.e.
	 * <tt>cosh(z) = &half;(e<sup>z</sup>+e<sup>-z</sup>) = cos(-i&sdot;z) = cos(i&sdot;z)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>cos(i&sdot;z)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @see #cos(T)
	 */
	static public <T extends MutableDoublePrecision<T>> T cosh (final T z) throws NullPointerException {
		return cos(z.clone().imul());
	}


	/**
	 * Returns the hyperbolic {@code tangent} of {@code z}, i.e.
	 * <tt>tanh(z) = -i&sdot;sin(i&sdot;z)/cos(i&sdot;z) = </tt><br />
	 * <tt>-i&sdot;tan(i&sdot;z)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>-i&sdot;tan(i&sdot;z)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @see #tan(T)
	 */
	static public <T extends MutableSinglePrecision<T>> T tanh (final T z) throws NullPointerException {
		return tan(z.clone().imul()).idiv();
	}


	/**
	 * Returns the hyperbolic {@code tangent} of {@code z}, i.e.
	 * <tt>tanh(z) = -i&sdot;sin(i&sdot;z)/cos(i&sdot;z) = </tt><br />
	 * <tt>-i&sdot;tan(i&sdot;z)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>-i&sdot;tan(i&sdot;z)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @see #tan(T)
	 */
	static public <T extends MutableDoublePrecision<T>> T tanh (final T z) throws NullPointerException {
		return tan(z.clone().imul()).idiv();
	}


	/**
	 * Returns the hyperbolic {@code area sine} of {@code z}, i.e.
	 * <tt>asinh(z) = log(z &plusmn; (z<sup>2</sup>+1)<sup>&half;</sup>)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>log(z + (z<sup>2</sup>+1)<sup>&half;</sup>)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T asinh (final T z) throws NullPointerException {
		return log(z.clone().sq().add(1).sqrt().add(z));
	}


	/**
	 * Returns the hyperbolic {@code area sine} of {@code z}, i.e.
	 * <tt>asinh(z) = log(z &plusmn; (z<sup>2</sup>+1)<sup>&half;</sup>)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>log(z + (z<sup>2</sup>+1)<sup>&half;</sup>)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T asinh (final T z) throws NullPointerException {
		return log(z.clone().sq().add(1).sqrt().add(z));
	}


	/**
	 * Returns the hyperbolic {@code area cosine} of {@code z}, i.e.
	 * <tt>acosh(z) = log(z &plusmn; (z<sup>2</sup>-1)<sup>&half;</sup>) = </tt><br />
	 * <tt>log(z + (z+1)<sup>&half;</sup>&sdot;(z-1)<sup>&half;</sup>)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>log(z + (z+1)<sup>&half;</sup>&sdot;(z-1)<sup>&half;</sup>)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T acosh (final T z) throws NullPointerException {
		return log(z.clone().add(1).sqrt().mul(z.clone().sub(1).sqrt()).add(z));
	}


	/**
	 * Returns the hyperbolic {@code area cosine} of {@code z}, i.e.
	 * <tt>acosh(z) = log(z &plusmn; (z<sup>2</sup>-1)<sup>&half;</sup>) = </tt><br />
	 * <tt>log(z + (z+1)<sup>&half;</sup>&sdot;(z-1)<sup>&half;</sup>)</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>log(z + (z+1)<sup>&half;</sup>&sdot;(z-1)<sup>&half;</sup>)</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T acosh (final T z) throws NullPointerException {
		return log(z.clone().add(1).sqrt().mul(z.clone().sub(1).sqrt()).add(z));
	}


	/**
	 * Returns the hyperbolic {@code area tangent} of {@code z}, i.e.<br />
	 * <tt>atanh(z) = &half;log((1+z) / (1-z))</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>&half;log((1+z) / (1-z))</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableSinglePrecision<T>> T atanh (final T z) throws NullPointerException {
		return log(z.clone().add(1).div(z.clone().neg().add(1))).mul(.5f);
	}


	/**
	 * Returns the hyperbolic {@code area tangent} of {@code z}, i.e.<br />
	 * <tt>atanh(z) = &half;log((1+z) / (1-z))</tt>.
	 * @param <T> the declaration type of the complex argument and result
	 * @param z the complex operand
	 * @return the value <tt>&half;log((1+z) / (1-z))</tt>
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public <T extends MutableDoublePrecision<T>> T atanh (final T z) throws NullPointerException {
		return log(z.clone().add(1).div(z.clone().neg().add(1))).mul(.5d);
	}


	//*******************//
	// vector operations //
	//*******************//

	/**
	 * Returns a clone (deep copy) of the given complex vector.
	 * @param <T> the component type of the argument and result arrays
	 * @param vector the complex vector
	 * @return the clone
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws ArrayStoreException if the cloning of any vector element results in an incompatibe
	 *         vector component type
	 */
	static public <T extends Complex<T>> T[] clone (final T[] vector) throws NullPointerException, ArrayStoreException {
		final T[] clone = vector.clone();
		for (int index = 0; index < vector.length; ++index) {
			final T value = clone[index];
			if (value != null) clone[index] = value.clone();
		}
		return clone;
	}


	/**
	 * Returns an image created from plotting the given function as a color wheel over the specified
	 * complex value range.
	 * @param function the complex function used to map complex values
	 * @param left the minimum real part of any complex operand value within the plot
	 * @param low the minimum imaginary part of any complex operand value within the plot
	 * @param right the maximum real part of any complex operand value within the plot
	 * @param high the maximum imaginary part of any complex operand value within the plot
	 * @param magnification the number of pixels to be created per distance unit.
	 * @param saturation the saturation of each pixel within range [0.0, 1.0]
	 * @return the plotted image
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public WritableImage plotColorWheel (final UnaryOperator<MutableSinglePrecision<?>> function, final float left, final float low, final float right, final float high, float magnification, final float saturation) throws NullPointerException {
		if (low > high | left > right | magnification <= 0 | saturation < 0 | saturation > 1) throw new IllegalArgumentException();

		final float resolution = 1 / magnification;
		final int width = (int) Math.round((right - left) * magnification);
		final int height = (int) Math.round((high - low) * magnification);
		final WritableImage image = new WritableImage(width, height);

		final FloatCartesianComplex z1 = new FloatCartesianComplex();
		for (int row = 0; row < height; ++row) {
			for (int column = 0; column < width; ++column) {
				final float re = left + column * resolution;
				final float im = high - row * resolution;

				z1.setCartesian(re, im);
				final MutableSinglePrecision<?> z2 = function.apply(z1);
				if (z2.isNaN()) z2.setCartesian(0, 0);

				// mapping: the origin is black, −1 is red, 1 is cyan, and a point at infinity is white:
				final double hue = 180 + (180 / Math.PI) * z2.arg();
				final double brightness = 1 - Math.pow(.5d, z2.abs());

				final Color color = Color.hsb(hue, saturation, brightness);
				image.getPixelWriter().setColor(column, row, color);
			}
		}

		return image;
	}


	/**
	 * Returns an image created from plotting the given function as a color wheel over the specified
	 * complex value range.
	 * @param function the complex function used to map complex values
	 * @param left the minimum real part of any complex operand value within the plot
	 * @param low the minimum imaginary part of any complex operand value within the plot
	 * @param right the maximum real part of any complex operand value within the plot
	 * @param high the maximum imaginary part of any complex operand value within the plot
	 * @param magnification the number of pixels to be created per distance unit.
	 * @param saturation the saturation of each pixel within range [0.0, 1.0]
	 * @return the plotted image
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 */
	static public WritableImage plotColorWheel (final UnaryOperator<MutableDoublePrecision<?>> function, final double left, final double low, final double right, final double high, double magnification, final float saturation) throws NullPointerException {
		if (low > high | left > right | magnification <= 0 | saturation < 0 | saturation > 1) throw new IllegalArgumentException();

		final double resolution = 1 / magnification;
		final int width = (int) Math.round((right - left) * magnification);
		final int height = (int) Math.round((high - low) * magnification);
		final WritableImage image = new WritableImage(width, height);

		final DoubleCartesianComplex z1 = new DoubleCartesianComplex();
		for (int row = 0; row < height; ++row) {
			for (int column = 0; column < width; ++column) {
				final double re = left + column * resolution;
				final double im = high - row * resolution;

				z1.setCartesian(re, im);
				final MutableDoublePrecision<?> z2 = function.apply(z1);
				if (z2.isNaN()) z2.setCartesian(0, 0);

				// mapping: the origin is black, +1 is red, -1 is cyan, and a point at infinity is white:
				final double hue = (180 / Math.PI) * z2.arg();
				final double brightness = 1 - Math.pow(.5d, z2.abs());
				assert hue >= 0 & hue <= 1 & brightness >= 0 & brightness <= 1;

				final Color color = Color.hsb(hue, saturation, brightness);
				image.getPixelWriter().setColor(column, row, color);
			}
		}

		return image;
	}



//************************************************************
// TODO old bit-complex class implementation for verification:
//************************************************************
//
//	//***********************//
//	// Exponential functions //
//	//***********************//
//
//	/**
//	 * Returns {@code log(z)}, i.e. the principal branch solution for the infinitely multi-valued
//	 * natural logarithm (base {@code e}) of a {@code complex} operand. The multi-valued nature
//	 * implies a discontinuity, which is located at negative real values of the operand {@code z}
//	 * .</br />
//	 * <br />
//	 * The principal branch solution is the one whose imaginary part is guaranteed to be within
//	 * range<br />
//	 * <i>[-τ/2,+τ/2[</i>. The solutions for the other branches may be obtained by adding
//	 * <i>i&sdot;τ&sdot;k</i> (branch Index {@code k} &isin; &#x2124;) to the principal branch solution.
//	 * Regardless of the branch chosen, the real part of the result is guaranteed to be positive.
//	 * <br />
//	 * <br />
//	 * When combining this operation with it's inverse, as in <i>log(exp(z)) = exp(log(z)) = z</i>,
//	 * choose branch <i>k = floor(&image;(z)/τ + 1/2)</i> for correct branch alignment.
//	 * @param z the complex operand in {@code primitive-cartesian} representation
//	 * @return the principal value <tt>log(z) = log|z| + i&sdot;&#x305;&#x332;z</tt> in Cartesian
//	 *         64bit-representation
//	 * @see #log(float, long)
//	 */
//	static public long log (final long z) {
//		return cartesian((float) Math.log(abs(z)), arg(z));
//	}
//
//
//	/**
//	 * Returns <tt>log<sub>b</sub>(z)</tt>, i.e. the principal branch solution for the infinitely
//	 * multi-valued {@code real} based logarithm of a {@code complex} operand. The multi-valued
//	 * nature implies a discontinuity, which is located at negative real values of the operand
//	 * {@code z}. The exact behavior of this operation depends on the nature of the base {@code b}:
//	 * <ul>
//	 * <li>if the base {@code b} equals the operand {@code z}, then the result is always one.</li>
//	 * <li>if the base {@code b} is {@code 0}, then the result is zero.</li>
//	 * <li>if the base {@code b} is {@code +1}, then the result is infinite and/or NaN.</li>
//	 * <li>if the base {@code b} is Euler's number {@code e}, then see {@link #log(long)} for
//	 * details.</li>
//	 * <li>if the base {@code b} is any other positive number, then the principal branch solution is
//	 * the one whose imaginary part is guaranteed to be within range
//	 * <i>[-&pi;/log(b),+&pi;/log(b)[</i>. The solutions for the other branches may be obtained by
//	 * adding <i>i&sdot;&tau;&sdot;k/log(b)</i> (branch index <tt>k &isin; &#x2124;</tt>) to the
//	 * principal branch solution. Regardless of the branch chosen, the real part of the result is
//	 * guaranteed to be positive.</li>
//	 * <li>if the base {@code b} is {@code -1}, then the solutions for the other branches may be
//	 * obtained by adding <tt>2&sdot;k</tt> (with branch index <tt>k &isin; &#x2124;</tt>) to the
//	 * principal branch solution.</li>
//	 * <li>if the base {@code b} is any other negative number, then the solutions for the other
//	 * branches are also obtainable, but the branch correction becomes more complicated.</li>
//	 * </ul>
//	 * When combining this operation with it's inverse, as in <i>log(b, exp(b,z)) = exp(b, log(b,z))
//	 * = z</i>, choose
//	 * <ul>
//	 * <li>if the base {@code b} is positive: <i>k = floor(½ + &image;(z)/&tau;&sdot;log(b))</i>
//	 * </li>
//	 * <li>if the base {@code b = -1}:
//	 * <ul>
//	 * <li><i>k = floor(½(&real;(z)+1))</i> if &real;(z) < -1</li>
//	 * <li><i>k = ceil(½(&real;(z)-1))</i> if &real;(z) >= -1</li>
//	 * </ul>
//	 * </ul>
//	 * @param b the real base
//	 * @param z the complex operand in {@code primitive-cartesian} representation
//	 * @return the principal value <tt>log<sub>b</sub>(z) = log(z)/log(b) = </tt>
//	 *         <tt>(log|z| + i&sdot;&#x305;&#x332;z) / log(b)</tt> in {@code primitive-cartesian} representation
//	 * @see #log(long, long)
//	 */
//	static public long log (final float b, final long z) {
//		if (b == (float) Math.E) return log(z);
//		if (im(z) == 0 && re(z) == b) return ONE;
//
//		final long logZ = log(z);
//		final float logB = (float) Math.log(Math.abs(b));
//		return b >= 0
//			? div(logZ, logB)
//			: div(logZ, cartesian(logB, (float) -TAU / 2));
//	}
//
//
//	/**
//	 * Returns <tt>log<sub>z<sub>1</sub></sub>(z<sub>2</sub>)</tt>, i.e. the principal branch
//	 * solution for the infinitely multi-valued {@code complex} based logarithm of a {@code complex}
//	 * operand. The multi-valued nature implies a discontinuity, which is located at negative real
//	 * values of the operand <tt>z<sub>2</sub></tt>. The exact behavior of this operation depends on
//	 * the nature of the base <tt>z<sub>1</sub></tt>:
//	 * <ul>
//	 * <li>if the base z<sub>1</sub> equals the operand <tt>z<sub>2</sub></tt>, then the result is
//	 * always one.</li>
//	 * <li>if the base z<sub>1</sub> is Euler's number {@code e}, then see {@link #log(long)} for
//	 * details.</li>
//	 * <li>if the base z<sub>1</sub> is a {@code real} number, then see {@link #log(double, long)}
//	 * for details.</li>
//	 * <li>if the base z<sub>1</sub> is a {@code complex} number, the general behavior of this
//	 * operation is generally similar to {@link #log(double, long)} when passing negative bases.
//	 * However, branch determination and correction become much more complicated.</li>
//	 * </ul>
//	 * @param z1 the complex base in {@code primitive-cartesian} representation
//	 * @param z2 the complex operand in {@code primitive-cartesian} representation
//	 * @return the principal value <tt>log<sub>z<sub>1</sub></sub>(z<sub>2</sub>) =
//	 *         log(z<sub>2</sub>)/log(z<sub>1</sub>) = 
//	 *         (log|z<sub>2</sub>|+i&sdot;&phi;(z<sub>2</sub>)) /
//	 *         (log|z<sub>1</sub>|+i&sdot;&phi;(z<sub>1</sub>))</tt> in Cartesian
//	 *         64bit-representation
//	 */
//	static public long log (final long z1, final long z2) {
//		if (z1 == z2) return ONE;
//		if (isReal(z1)) return log(re(z1), z2);
//		return div(log(z2), log(z1));
//	}
//
//
//	/**
//	 * Returns <tt>e<sup>z</sup></tt>, i.e. Euler's number raised to the power of a {@code complex}
//	 * operand.<br />
//	 * <br />
//	 * Note that this operation is infinitely cyclic, with <i>e<sup>z + i&sdot;τ&sdot;k</sup> = e
//	 * <sup>z</sup></i> for any integer k. This behavior causes the logarithm (as the inverse of
//	 * this operation) to become multi-valued in <tt>&#x2102;</tt>, similarly to the sine function's
//	 * cyclic behavior causing the arc sine function to become multi-valued in <tt>&#x211D;</tt>.
//	 * @param z the complex exponent in {@code primitive-cartesian} representation
//	 * @return the value <tt>e<sup>z</sup> = e<sup>&real;(z)</sup> &sdot; e<sup>i&sdot;&image;(z)</sup></tt>
//	 *         in {@code primitive-cartesian} representation
//	 * @see #exp(float, long)
//	 */
//	static public long exp (final long z) {
//		return polar((float) Math.exp(re(z)), im(z));
//	}
//
//
//	/**
//	 * Returns <tt>b<sup>z</sup></tt>, i.e. a {@code real} base raised to the power of a
//	 * {@code complex} exponent. The result of <tt>1<sup>z</sup></tt> and <tt>b<sup>0</sup></tt> is
//	 * always one, by definition including the special case <tt>0<sup>0</sup></tt> . The result of
//	 * <tt>0<sup>z</sup></tt> is zero unless {@code z} is zero.<br />
//	 * <br />
//	 * Note that this operation is infinitely cyclic except for special bases like zero, one or
//	 * infinity, and special exponents like 0 or infinity. This behavior causes the logarithm (as
//	 * the inverse of this operation) to become multi-valued in <tt>&#x2102;</tt>, similarly to the
//	 * sine function's cyclic behavior causing the arc sine function to become multi-valued in
//	 * <tt>&#x211D;</tt>. The exact cycle behavior depends on the nature of the base {@code b}:
//	 * <ul>
//	 * <li>if the given base {@code b} is strictly positive except {@code +1}, then the cycling is
//	 * simply vertical, with<br />
//	 * <i>exp(b, z + i&sdot;τ&sdot;k / log(b)) = exp(b, z)</i> for any integer {@code k}.</li>
//	 * <li>if the given base is {@code b=-1}, then the cycling is simply horizontal, with <i>exp(b,
//	 * z + 2&sdot;k) = exp(b, z)</i> for any integer {@code k}.</li>
//	 * <li>if the given base {@code b} is strictly negative except {@code -1}, then the cycling is
//	 * obliquely rotated from the vertical by an angle between ]0,&pi;[, depending on the absolute
//	 * of {@code b}.</li>
//	 * </ul>
//	 * @param b the (positive) real base
//	 * @param z the complex exponent in {@code primitive-cartesian} representation
//	 * @return the value <tt>b<sup>z</sup> = </tt><tt>e<sup>log(b)&sdot;z</sup> = </tt>
//	 *         <tt>e<sup>log(b)&sdot;&real;(z)</sup>&sdot;e<sup>i&sdot;log(b)&sdot;&image;(z)</sup></tt>
//	 *         in {@code primitive-cartesian} representation
//	 */
//	static public long exp (final float b, final long z) {
//		if (b < 0) return exp(cartesian(b, 0), z);
//		if (b == 0) return isZero(z) ? ONE : ZERO;
//		if (b == 1) return ONE;
//		if (b == (float) Math.E) return exp(z);
//
//		final float log = (float) Math.log(b);
//		return polar((float) Math.exp(log * re(z)), log * im(z));
//	}
//
//
//	/**
//	 * Returns <tt>z<sub>1</sub><sup>z<sub>2</sub></sup></tt>, i.e. the principal branch solution
//	 * for a {@code complex} base raised to the power of a {@code complex} exponent, which is
//	 * infinitely multi-valued in <tt>&#x2102;</tt>. The exact behavior of this operation depends on
//	 * the nature of the operands:
//	 * <ul>
//	 * <li>If the base <tt>z<sub>1</sub></tt> is positive {@code real} number, then see
//	 * {@link #exp(double, long)} for details.</li>
//	 * <li>If the exponent <tt>z<sub>2</sub></tt> is an {@code integer} number, then see
//	 * {@link #pow(long, int)} for details.</li>
//	 * <li>If the exponent <tt>z<sub>2</sub></tt> is or approximates a unit fraction, then see
//	 * {@link #root(long, int)} for details.</li>
//	 * <li>If the exponent <tt>z<sub>2</sub></tt> is a {@code real} number, but neither an integer
//	 * nor a unit fraction, then it is technically nevertheless a dyadic fraction in lowest terms
//	 * because any IEEE floating point number is. Therefore, this operation behaves as if
//	 * {@link #root(long, int)} and {@link #pow(long, int)} had been combined, but without the
//	 * guarantee that the principal branch solution returned is the one whose argument (positive or
//	 * negative) is closest to zero.</li>
//	 * <li>Otherwise this operation displays a more delicate behavior: If the base is set constant
//	 * and the exponent is varied, then this operation shows a cyclic pattern similarly to
//	 * {@link #pow(long, int)}, but rotated by varying degrees. If the base is varied and the
//	 * exponent set constant, then this operation displays a pattern similarly to
//	 * {@link #root(long, int)}, but rotated by varying degrees resulting in cycle or spiral
//	 * patterns. If z<sub>1</sub> = z<sub>2</sub> or z<sub>1</sub> = -z<sub>2</sub>, then this
//	 * operation displays a cyclic pattern similarly to {@link #pow(long, int)}, but bended around
//	 * the origin. If z<sub>1</sub> = z<sub>2</sub><sup>-1</sup>, then this operation displays a
//	 * cyclic shamrock pattern.</li>
//	 * </ul>
//	 * @param z1 the complex base in {@code primitive-cartesian} representation
//	 * @param z2 the complex exponent in {@code primitive-cartesian} representation
//	 * @return the value <tt>z<sub>1</sub><sup>z<sub>2</sub></sup></tt> = e<sup>log(z<sub>1</sub>) &sdot;
//	 *         z<sub>2</sub></sup> = e<sup>(log|z<sub>1</sub>| + i&sdot;z&#x305;&#x332;<sub>1</sub>) &sdot;
//	 *         (&real;(z<sub>2</sub>) + i&sdot;&image;(z<sub>2</sub>))</sup> = <br />
//	 *         e<sup>log|z<sub>1</sub>|&sdot;&real;(z<sub>2</sub>) - z&#x305;&#x332;<sub>1</sub>
//	 *         &sdot;&image;(z<sub>2</sub>)</sup> &sdot; e<sup>i&sdot;(log|z<sub>1</sub>|&sdot;&image;(z<sub>2</sub>) +
//	 *         z&#x305;&#x332;<sub>1</sub>&sdot;&real;(z<sub>2</sub>))</sup></tt> in Cartesian
//	 *         64bit-representation
//	 */
//	static public long exp (final long z1, final long z2) {
//		final float x1 = re(z1);
//		if (isReal(z1) && x1 >= 0) return exp(x1, z2);
//
//		final float x2 = re(z2);
//		if (isReal(z2)) {
//			if (x2 == (int) x2) return pow(z1, (int) x2);
//			final float inv = 1 / x2;
//			if (inv == (int) inv) return root(z1, (int) inv);
//		}
//
//		final float  y1 = im(z1), y2 = im(z2);
//		final float logRe = (float) Math.log(ScalarMath.abs(x1, y1)), logIm = ScalarMath.arg(x1, y1);
//		return polar((float) Math.exp(mulRe(logRe, logIm, x2, y2)), (float) mulIm(logRe, logIm, x2, y2));
//	}
//
//
//	/**
//	 * Returns <tt>z<sup>1/n</sup></tt>, i.e. the principal branch solution for the finitely
//	 * multi-valued {@code integer} root of a {@code complex} operand {@code z}, using de
//	 * Moivre's formula. The multi-valued nature of this operation implies a discontinuity,
//	 * which is located at negative real values of the operand {@code z}, except for
//	 * <tt>n=&plusmn;1</tt> where this operation is single-valued, and {@code n=0} where no
//	 * valid branches exist.<br />
//	 * <br />
//	 * The principal branch solution is the one whose argument (positive or negative) is closest
//	 * to zero; it's real part is guaranteed to be positive for {@code |n|>1}. The solutions
//	 * for the other branches can be obtained by multiplying the principal branch solution with
//	 * <tt>e<sup>i&sdot;&tau;&sdot;k/n</sup></i></tt>, using branch index <tt>k&isin;&#x2124;</tt>
//	 * within range <tt>[0,|n|[</tt>. When combining this operation with it's inverse, as in
//	 * <tt>(z<sup>1/n</sup>)<sup>n</sup> = (z<sup>n</sup>)<sup>1/n</sup> = z</tt>, use<br />
//	 * <tt>k = (floor(|z|&sdot;n/&tau;+½)%|n|</tt>, adding {@code |n|} if {@code k<0}.<br />
//	 * <br />
//	 * Note that fractional exponentiation may be performed by first using this operation to
//	 * calculate the fraction's divisor root of {@code z} (including choice of the proper
//	 * branch), and subsequently raising the result to the power of the fraction's dividend
//	 * using {@link #pow(long, int)}.
//	 * @param z the complex operand in {@code primitive-cartesian} representation
//	 * @param n the integer root
//	 * @return the principal value z<sup>1/n</sup> = |z|<sup>1/n</sup> &sdot; e
//	 *         <sup>i&sdot;z&#x305;&#x332;/n</sup> in {@code primitive-cartesian} representation
//	 * @throws IllegalArgumentException if {@code k} is outside range [0, |n|[, which is always the
//	 *         case if {@code n} is zero
//	 * @see #pow(long, int)
//	 */
//	static public long root (final long z, int n) {
//		switch (n) {
//			case -2:
//				return invert(sqrt(z));
//			case +2:
//				return sqrt(z);
//			case -3:
//			case +3:
//			default:
//				return pow(z, 1 / (float) n);
//		}
//	}
//
//
//	/**
//	 * Returns <tt>z<sup>n</sup></tt>, i.e. a {@code complex} base raised to the power of an
//	 * {@code integer} exponent, using de Moivre's formula. The special case <tt>z<sup>0</sup></tt>
//	 * calculates to {@code 1} in all cases, even for zero, infinite and {@code NaN} values of
//	 * {@code z}, as defined in {@link Math#pow}.<br />
//	 * <br />
//	 * Note that this operation is finitely cyclic, with
//	 * <tt>(z&sdot;e<sup>i&sdot;&tau;&sdot;k/n</sup>)<sup>n</sup> = z<sup>n</sup></tt> for every
//	 * integer {@code k} within range {@code [0,|n|[}. This cyclic behavior causes the root
//	 * operation (as the inverse of this operation) to become multi-valued in <tt>&#x2102;</tt>,
//	 * similarly to the sine function's cyclic behavior causing the arc sine function to become
//	 * multi-valued in <tt>&#x211D;</tt>.<br />
//	 * <br />
//	 * Also note that fractional exponentiation may be performed by first using
//	 * {@link #root(long, int)} to calculate the fraction's divisor root of {@code z} (including
//	 * choice of the proper branch), and subsequently using this operation to raise the result to
//	 * the power of the fraction's dividend.
//	 * @param z the complex base in {@code primitive-cartesian} representation
//	 * @param n the integer exponent
//	 * @return the value the value <tt>z<sup>n</sup> = </tt>
//	 *         <tt>|z|<sup>n</sup>&sdot;e<sup>i&sdot;&phi;(z)&sdot;n</sup></tt> in Cartesian
//	 *         64bit-representation
//	 * @see #root(long, int)
//	 */
//	static public long pow (final long z, final int n) {
//		switch (n) {
//			case -2:
//				return invert(square(z));
//			case +2:
//				return square(z);
//			case -3:
//			case +3:
//			default:
//				return pow(z, (float) n);
//		}
//	}
//
//
//	/**
//	 * Returns <tt>z<sup>r</sup></tt>, i.e. a {@code complex} base raised to the power of a
//	 * {@code real} exponent, using de Moivre's formula. The special case <tt>z<sup>0</sup></tt>
//	 * calculates to {@code 1} in all cases, even for zero, infinite and {@code NaN} values of
//	 * {@code z}, as defined in {@link Math#pow}.<br />
//	 * <br />
//	 * This operation can be both infinitely cyclic and infinitely multi-valued, depending on the
//	 * value of the given exponent. Therefore, it is practically unsuitable for branch analysis, for
//	 * which both {@link #root(long, int)} and {@link #pow(long, int)} should be preferred. Note
//	 * that the multi-valued nature of this operation implies a discontinuity, which is located at
//	 * negative real values of the operand {@code z}, except for <tt>r=&plusmn;1</tt> where this
//	 * operation is single-valued, and <tt>r&rarr;&plusmn;&infin;</tt> where no valid branches
//	 * exist.
//	 * @param z the complex base in {@code primitive-cartesian} representation
//	 * @param r the real exponent
//	 * @return the value <tt>z<sup>n</sup> = </tt>
//	 *         <tt>|z|<sup>r</sup>&sdot;e<sup>i&sdot;&phi;(z)&sdot;r</sup></tt> in Cartesian
//	 *         64bit-representation
//	 * @throws IllegalArgumentException if the given exponent {@code r} is infinite
//	 * @see #root(long, int)
//	 * @see #pow(long, int)
//	 */
//	static long pow (final long z, final float r) throws IllegalArgumentException {
//		if (r == Float.NEGATIVE_INFINITY | r == Float.POSITIVE_INFINITY) throw new IllegalArgumentException();
//		if (r == -1) return invert(z);
//		if (r == 0) return ONE;
//		if (r == -1) return z;
//
//		final float x = re(z), y = im(z);
//		return polar((float) Math.pow(ScalarMath.abs(x, y), r), ScalarMath.arg(x, y) * r);
//	}
//
//
//
//	//*************************//
//	// Trigonometric functions //
//	//*************************//
//
//	/**
//	 * Returns <tt>sin(z) = i&sdot;&half;(e<sup>-iz</sup>-e<sup>+iz</sup>)</tt>.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value
//	 *         <tt>&half;(e<sup>+&image;(z)</sup>+e<sup>-&image;(z)</sup>)&sdot;sin(&real;(z)) + </tt>
//	 *         <tt>i&sdot;&half;(e<sup>+&image;(z)</sup>-e<sup>-&image;(z)</sup>)&sdot;cos(&real;(z))</tt>
//	 *         in {@code primitive-cartesian} representation
//	 */
//	static public long sin (final long operand) {
//		final double x = re(operand), y = im(operand);
//		final double sin = Math.sin(x), cos = Math.cos(x);
//		final double pex = Math.exp(y), nex = 1 / pex;
//		return cartesian((float) (0.5 * (pex + nex) * sin), (float) (0.5 * (pex - nex) * cos));
//	}
//
//
//	/**
//	 * Returns <tt>cos(z) = &half;(e<sup>-iz</sup>+e<sup>+iz</sup>)</tt>.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value
//	 *         <tt>&half;(e<sup>-&image;(z)</sup>+e<sup>+&image;(z)</sup>)&sdot;cos(&real;(z)) + </tt>
//	 *         <tt>i&sdot;&half;(e<sup>-&image;(z)</sup>-e<sup>+&image;(z)</sup>)&sdot;sin(&real;(z))</tt>
//	 *         in {@code primitive-cartesian} representation
//	 */
//	static public long cos (final long operand) {
//		final double x = re(operand), y = im(operand);
//		final double sin = Math.sin(x), cos = Math.cos(x);
//		final double pex = Math.exp(y), nex = 1 / pex;
//		return cartesian((float) (0.5 * (nex + pex) * cos), (float) (0.5 * (nex - pex) * sin));
//	}
//
//
//	/**
//	 * Returns
//	 * <tt>tan(z) = i&sdot;(e<sup>-iz</sup>-e<sup>+iz</sup>)/(e<sup>-iz</sup>+e<sup>+iz</sup>)</tt>.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value <tt>((e<sup>+&image;(z)</sup>+e<sup>-&image;(z)</sup>)&sdot;sin(&real;(z)) +
//	 *         (e<sup>+&image;(z)</sup>-e<sup>-&image;(z)</sup>)&sdot;cos(&real;(z))) /<br />
//	 *         ((e<sup>-&image;(z)</sup>+e<sup>+&image;(z)</sup>)&sdot;cos(&real;(z)) +
//	 *         (e<sup>-&image;(z)</sup>-e<sup>+&image;(z)</sup>)&sdot;sin(&real;(z)))</tt> in Cartesian
//	 *         64bit-representation
//	 */
//	static public long tan (final long operand) {
//		final double x = re(operand), y = im(operand);
//		final double sin = Math.sin(x), cos = Math.cos(x);
//		final double pex = Math.exp(y), nex = Math.exp(-y);
//		return div((pex + nex) * sin, (pex - nex) * cos, (nex + pex) * cos, (nex - pex) * sin);
//	}
//
//
//	/**
//	 * Returns the {@code arc sine} of {@code z}, i.e. {@code asin(z)}.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value <tt>-i&sdot;log(i&sdot;z + sqrt(1-z<sup>2</sup>))</tt> in Cartesian
//	 *         64bit-representation
//	 * @see #log(long)
//	 */
//	static public long asin (final long operand) {
//		return idiv(log(add(imul(operand), sqrt(sub(ONE, square(operand))))));
//	}
//
//
//	/**
//	 * Returns the {@code arc cosine} of {@code z}, i.e. {@code acos(z)}.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value <tt>-i&sdot;log(z + sqrt(z<sup>2</sup>-1))</tt> in Cartesian
//	 *         64bit-representation
//	 */
//	static public long acos (final long operand) {
//		return idiv(log(add(operand, sqrt(sub(square(operand), ONE)))));
//	}
//
//
//	/**
//	 * Returns the {@code arc tangent} of {@code z}, i.e. {@code atan(z)}.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value <tt>-&half;i&sdot;log((1+i&sdot;z) / (1-i&sdot;z))</tt>
//	 *         in {@code primitive-cartesian} representation
//	 */
//	static public long atan (long operand) {
//		operand = imul(operand);
//		return mul(imul(log(div(add(ONE, operand), sub(ONE, operand)))), -0.5f);
//	}
//
//
//	/**
//	 * Returns the hyperbolic {@code sine} of {@code z}, i.e.
//	 * <tt>sinh(z) = &half;(e<sup>z</sup>-e<sup>-z</sup>) = </tt>
//	 * <tt>-i&sdot;sin(i&sdot;z)</tt>.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value <tt>-i&sdot;sin(i&sdot;z)</tt> in {@code primitive-cartesian} representation
//	 * @see #sin(long)
//	 */
//	static public long sinh (final long operand) {
//		return imul(sin(idiv(operand)));
//	}
//
//
//	/**
//	 * Returns the hyperbolic {@code cosine} of {@code z}, i.e.
//	 * <tt>cosh(z) =&half;(e<sup>z</sup>+e<sup>-z</sup>) = cos(i&sdot;z)</tt>.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value <tt>cos(i&sdot;z)</tt> in {@code primitive-cartesian} representation
//	 * @see #cos(long)
//	 */
//	static public long cosh (final long operand) {
//		return cos(idiv(operand));
//	}
//
//
//	/**
//	 * Returns the hyperbolic {@code tangent} of {@code z}, i.e.
//	 * <tt>tanh(z) = sinh(z)/cosh(z) = </tt><br />
//	 * <tt>i&sdot;sin(-i&sdot;z)/cos(-i&sdot;z) = i&sdot;tan(-i&sdot;z)</tt>.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value <tt>i&sdot;tan(-i&sdot;z)</tt> in {@code primitive-cartesian} representation
//	 */
//	static public long tanh (final long operand) {
//		return imul(tan(idiv(operand)));
//	}
//
//
//	/**
//	 * Returns the hyperbolic inverse {@code sine} of {@code z}, i.e.<br />
//	 * <tt>asinh(z) = i&sdot;asin(-i&sdot;z)</tt>.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value <tt>i&sdot;asin(-i&sdot;z)</tt> in {@code primitive-cartesian} representation
//	 */
//	static public long asinh (final long operand) {
//		return imul(asin(idiv(operand)));
//	}
//
//
//	/**
//	 * Returns the hyperbolic {@code area cosine} of {@code z}, i.e.
//	 * <tt>acosh(z) = -i&sdot;acos(z)</tt>.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value <tt>-i&sdot;acos(z)</tt> in {@code primitive-cartesian} representation
//	 */
//	static public long acosh (final long operand) {
//		// might be -i&sdot;acos(z) for &image;(z) < 0 or |z| > 1!
//		return idiv(acos(operand));
//	}
//
//
//	/**
//	 * Returns the hyperbolic {@code area tangent} of {@code z}, i.e.<br />
//	 * <tt>atanh(z) = i&sdot;atan(-i&sdot;z)</tt>.
//	 * @param operand the complex operand in {@code primitive-cartesian} representation
//	 * @return the value <tt>i&sdot;atan(-i&sdot;z)</tt> in {@code primitive-cartesian} representation
//	 */
//	static public long atanh (final long operand) {
//		return imul(atan(idiv(operand)));
//	}


//********************************************************
// TODO old complex class implementation for verification:
//********************************************************
//	/**
//	 * Returns the natural logarithm of the receiver, i.e. log(z) = log(abs(z)) + i * arg(z).
//	 * @return the modified receiver
//	 */
//	public DoubleComplex log () {
//		final double abs = this.abs();
//		this.im = this.arg();
//		this.re = Math.log(abs);
//		return this;
//	}
//
//
//	/**
//	 * Returns the logarithm of the receiver to the given base, i.e. log(this) / log(base).
//	 * @param base the base of the logarithm function
//	 * @throws NullPointerException if the given argument is {@code null}
//	 * @return the modified receiver
//	 */
//	public DoubleComplex log (final DoubleComplex base) throws NullPointerException {
//		this.log();
//		return (base.re > 0 && base.im == 0) ? this.mul(1 / Math.log(base.re)) : this.div(base.clone().log());
//	}
//
//
//	/**
//	 * Returns Euler's number raised to the power of this.
//	 * @return the modified receiver
//	 */
//	public DoubleComplex exp () {
//		double exp = Math.exp(this.re);
//		this.re = exp * Math.cos(this.im);
//		this.im = exp * Math.sin(this.im);
//		return this;
//	}
//
//
//	/**
//	 * Returns the receiver raised to the power of the given exponent.
//	 * @param exponent the given exponent
//	 * @throws NullPointerException if the given argument is {@code null}
//	 * @return the modified receiver
//	 */
//	public DoubleComplex pow (final DoubleComplex exponent) throws NullPointerException {
//		return this.log().mul(exponent).exp();
//	}
//
//
//	/**
//	 * Returns the sine of the receiver, i.e. -0.5i * (e^iz - e^-iz). The implementation is
//	 * optimized for speed and accuracy, but note that for im <<>> 1 the result becomes non-exact
//	 * quickly because the lower exponent becomes insignificant to the higher one.
//	 * @return the modified receiver
//	 */
//	public DoubleComplex sin () {
//		final double pex = Math.exp(this.im);
//		final double nex = 1 / pex;
//		final double sin = Math.sin(this.re);
//		final double cos = Math.cos(this.re);
//		this.re = 0.5 * (pex + nex) * sin;
//		this.im = 0.5 * (pex - nex) * cos;
//		return this;
//	}
//
//
//	/**
//	 * Returns the arcus sine of the receiver, i.e. -/+i * log(iz + sqrt(1 - z^2)).
//	 * @return the modified receiver
//	 */
//	public DoubleComplex asin () {
//		final DoubleComplex clone = this.clone();
//		this.sq().neg().add(1).sqrt().add(clone.imul()).log().idiv();
//		return this;
//	}
//
//
//	/**
//	 * Returns the cosine of the receiver, i.e. 0.5 * (e^iz + e^-iz). The implementation is
//	 * optimized for speed and accuracy, but note that for im <<>> 1 the result becomes non-exact
//	 * quickly because the lower exponent becomes insignificant to the higher one.
//	 * @return the modified receiver
//	 */
//	public DoubleComplex cos () {
//		final double pex = Math.exp(this.im);
//		final double nex = 1 / pex;
//		final double sin = Math.sin(this.re);
//		final double cos = Math.cos(this.re);
//		this.re = 0.5 * (pex + nex) * cos;
//		this.im = -0.5 * (pex - nex) * sin;
//		return this;
//	}
//
//
//	/**
//	 * Returns the arcus cosine of the receiver, i.e. +/-i * log(z + i*sqrt(1 - z^2)). Note that the
//	 * result can be inverted due to the decision for one of the two possible square roots
//	 * calculated into the logarithm.
//	 * @return the arcus cosine
//	 */
//	public DoubleComplex acos () {
//		final DoubleComplex clone = this.clone();
//		return this.sq().add(-1).sqrt().add(clone).log().imul();
//	}
//
//
//	/**
//	 * Returns the tangens of the receiver, i.e. sin(z) / cos(z). The implementation is optimized
//	 * for speed and accuracy, but note that for im <<>> 1 the result becomes non-exact quickly
//	 * because the lower exponent becomes insignificant to the higher one.
//	 * @return the modified receiver
//	 */
//	public DoubleComplex tan () {
//		final double pex2 = Math.exp(this.im + this.im);
//		final double nex2 = 1 / pex2;
//		final double sin = Math.sin(this.re);
//		final double cos = Math.cos(this.re);
//
//		final double exp1 = pex2 + nex2;
//		final double exp2 = pex2 - nex2;
//		final double ang1 = 4 * sin * cos;
//		final double ang2 = 2 * (cos * cos - sin * sin);
//
//		this.re = 1 / (exp1 / ang1 + ang2 / ang1);
//		this.im = 1 / (exp1 / exp2 + ang2 / exp2);
//		return this;
//	}
//
//
//	/**
//	 * Returns the arcus tangent of the receiver, i.e. -0.5i * log((1 + iz) / (1 - iz)).
//	 * @return the arcus tangent
//	 */
//	public DoubleComplex atan () {
//		this.imul();
//		final DoubleComplex clone = this.clone();
//		return this.add(1).div(clone.mul(-1).add(1)).log().imul().mul(-0.5);
//	}
//
//
//	/**
//	 * Returns the hyperbolic sine of the receiver, i.e. 0.5 * (e^z - e^-z). Note that for re <<>> 1
//	 * the result becomes non-exact quickly because the lower exponent becomes insignificant to the
//	 * higher one.
//	 * @return the modified receiver
//	 */
//	public DoubleComplex sinh () {
//		final double pex = Math.exp(this.re);
//		final double nex = 1 / pex;
//		final double sin = Math.sin(this.im);
//		final double cos = Math.cos(this.im);
//		this.re = 0.5 * (pex - nex) * cos;
//		this.im = 0.5 * (pex + nex) * sin;
//		return this;
//	}
//
//
//	/**
//	 * Returns the area hyperbolic sine of the receiver, i.e. log(z + sqrt(z^2 + 1))
//	 * @return the modified receiver
//	 */
//	public DoubleComplex arsinh () {
//		final DoubleComplex clone = this.clone();
//		return this.sq().add(1).sqrt().add(clone).log();
//	}
//
//
//	/**
//	 * Returns the hyperbolic cosine of the receiver, i.e. 0.5 * (e^z + e^-z). Note that for re <<>>
//	 * 1 the result becomes non-exact quickly because the lower exponent becomes insignificant to
//	 * the higher one.
//	 * @return the modified receiver
//	 */
//	public DoubleComplex cosh () {
//		final double pex = Math.exp(this.re);
//		final double nex = 1 / pex;
//		final double sin = Math.sin(this.im);
//		final double cos = Math.cos(this.im);
//		this.re = 0.5 * (pex + nex) * cos;
//		this.im = 0.5 * (pex - nex) * sin;
//		return this;
//	}
//
//
//	/**
//	 * Returns the area hyperbolic cosine of the receiver, i.e. log(z + sqrt(z^2 - 1))
//	 * @return the modified receiver
//	 */
//	public DoubleComplex arcosh () {
//		final DoubleComplex clone = this.clone();
//		return this.sq().add(-1).sqrt().add(clone).log();
//	}
//
//
//	/**
//	 * Returns the hyperbolic tangent of the receiver, i.e. sinh(z) / cosh(z). Note that the
//	 * implementation is not broken down to atomic operations because this would further restrict
//	 * the value range when exponentials become exceedingly large.
//	 * @return the modified receiver
//	 */
//	public DoubleComplex tanh () {
//		final DoubleComplex clone = this.clone();
//		return this.sinh().div(clone.cosh());
//	}
//
//
//	/**
//	 * Returns the area hyperbolic tangent of the receiver, i.e. 0.5 * log((1 + z) / (1 - z))
//	 * @return the modified receiver
//	 */
//	public DoubleComplex artanh () {
//		final DoubleComplex clone = this.clone();
//		return this.add(1).div(clone.mul(-1).add(1)).log().mul(0.5);
//	}
}