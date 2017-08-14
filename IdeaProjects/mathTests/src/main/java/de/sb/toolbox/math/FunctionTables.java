package de.sb.toolbox.math;

import de.sb.toolbox.Copyright;
import de.sb.toolbox.util.Iterators;


/**
 * This facade provides inner classes for function tables. Note that this class is declared final
 * because it is a facade, and therefore not supposed to be extended. Also note that most operations
 * will likely be inlined by any JVM.
 */
@Copyright(year=2013, holders="Sascha Baumeister")
public final class FunctionTables {
	@SuppressWarnings("unchecked")
	static private final Iterable<SwapEntry>[] PERFECT_SHUFFLE_CACHE = new Iterable[Integer.SIZE - 1];
	static private final Trigonometric[] TRIGONOMETRIC_CACHE = new Trigonometric[Integer.SIZE - 2];


	/**
	 * Prevents external instantiation.
	 */
	private FunctionTables () {}


	/**
	 * Returns a perfect shuffle table instance based on a cache of precalculated swap index pairs.
	 * Instances are used to achieve perfect shuffle order by swapping elements pairwise in a
	 * collection of size <tt>2<sup>magnitude</sup></tt>. This is often useful when optimizing
	 * algorithms by replacing binary recursion with loops. Note that the table instances are cached
	 * as well.
	 * @param magnitude the magnitude
	 * @return the (immutable and cached) perfect shuffle table
	 * @throws ArrayIndexOutOfBoundsException if the given magnitude is outside range
	 *         <tt>[1,31]</tt>
	 */
	static public Iterable<SwapEntry> getPerfectShuffleTable (final int magnitude) throws ArrayIndexOutOfBoundsException {
		synchronized (PERFECT_SHUFFLE_CACHE) {
			return PERFECT_SHUFFLE_CACHE[magnitude - 1] == null
				? PERFECT_SHUFFLE_CACHE[magnitude - 1] = newPerfectShuffleTable(magnitude)
				: PERFECT_SHUFFLE_CACHE[magnitude - 1];
		}
	}


	/**
	 * Creates a new instance based on a cache of precalculated swap indices which can be used to
	 * achieve perfect shuffle order in a collection of <tt>2<sup>magnitude</sup></tt> elements.
	 * @param magnitude the magnitude
	 * @throws IllegalArgumentException if the given magnitude is outside range <tt>[1,31]</tt>
	 */
	static private Iterable<SwapEntry> newPerfectShuffleTable (final int magnitude) {
		if (magnitude < 1 | magnitude > 31) throw new IllegalArgumentException();
		final int half = 1 << magnitude-1;

		final SwapEntry[] indices = new SwapEntry[half - (half >> (magnitude >> 1))];
		for (int left = 0, offset = 0, stop = 1 << magnitude; left < stop; ++left) {
			final int right = ScalarMath.perfectShuffle(left, magnitude);
			if (left < right) indices[offset++] = new SwapEntry(left, right);
		}
		return () -> Iterators.newIterator(indices);
	}



	/**
	 * Returns a trigonometric table instance based on a cache of precalculated sine values,
	 * representing the angle argument range [0,&tau;] with the discrete index range [0,
	 * <tt>2<sup>magnitude</sup>]</tt>. The operations {@code sin(int)}, {@code cos(int)} and
	 * {@code tan(int)} can be used for fast access to these precalculated function values. Note
	 * that the table instances are cached as well.
	 * @param magnitude the magnitude
	 * @return the (immutable and cached) trigonometric table
	 * @throws ArrayIndexOutOfBoundsException if the given magnitude is outside range
	 *         <tt>[1,30]</tt>
	 * @see Trigonometric#sin(int)
	 * @see Trigonometric#cos(int)
	 * @see Trigonometric#tan(int)
	 */
	static public Trigonometric getTrigonometricTable (final int magnitude) throws ArrayIndexOutOfBoundsException {
		synchronized (TRIGONOMETRIC_CACHE) {
			return TRIGONOMETRIC_CACHE[magnitude - 1] == null
				? TRIGONOMETRIC_CACHE[magnitude - 1] = new Trigonometric(magnitude)
				: TRIGONOMETRIC_CACHE[magnitude - 1];
		}
	}



	/**
	 * Instances model pairs of index referencing elements in a collection that should be swapped.
	 * The left index is guaranteed to be smaller than the associated right index.
	 */
	static public final class SwapEntry {
		private final int left, right;

		/**
		 * Creates a new instance using the given indices.
		 * @param left the left index
		 * @param right the right index
		 * @throws IllegalArgumentException if the left index is not smaller than the right index
		 */
		public SwapEntry (final int left, final int right) throws IllegalArgumentException {
			if (left >= right) throw new IllegalArgumentException();
			this.left = left;
			this.right = right;
		}

		/**
		 * {@inheritDoc}
		 */
		public int getLeft () {
			return this.left;
		}

		/**
		 * {@inheritDoc}
		 */
		public int getRight() {
			return this.right;
		}
	}



	/**
	 * Instances of this class represent immutable pre-calculated trigonometric sine and cosine
	 * function tables, each representing values for <tt>2<sup>magnitude</sup>+1</tt> discrete
	 * function arguments. Both the function values and the function tables are cached, trading
	 * memory for speed.
	 */
	static public final class Trigonometric {
		static private final double TAU = 2 * Math.PI;

		private final int magnitude;
		private final double[] sines;


		/**
		 * Creates a new instance based on a cache of <tt>2<sup>magnitude</sup>+1</tt> precalculated
		 * sine values, representing the angle argument range [0,&tau;/4]; function symmetry is used
		 * to map the remaining function argument range to the first quarter.
		 * @param magnitude the magnitude
		 * @throws IllegalArgumentException if the given magnitude is outside range <tt>[1,30]</tt>
		 */
		private Trigonometric (final int magnitude) throws IllegalArgumentException {
			if (magnitude < 1 | magnitude > 30) throw new IllegalArgumentException();
			this.magnitude = magnitude;
			this.sines = new double[magnitude == 1 ? 0 : (1 << magnitude-2) + 1];

			final double angleDelta = Math.scalb(TAU, -magnitude);
			for (int angleIndex = 0; angleIndex < this.sines.length; ++angleIndex) {
				this.sines[angleIndex] = Math.sin(angleIndex * angleDelta);
			}
		}


		/**
		 * Returns the magnitude.
		 * @return the magnitude
		 */
		public int magnitude () {
			return this.magnitude;
		}


		/**
		 * Returns the cached function value <tt>sin(&tau;&sdot;k/2<sup>magnitude</sup>)</tt>. Note
		 * that the given argument {@code k} is not limited to any argument range.
		 * @param angleIndex the angle index k
		 * @return the (cached) {@code sine} value
		 * @see Math#sin(double)
		 */
		public double sin (int angleIndex) {
			final int magnitude = this.magnitude;
			if (magnitude == 1) return 0;

			angleIndex = ScalarMath.modExp2(angleIndex, magnitude);
			final int halfTurn = 1 << magnitude-1;
			final double signum = angleIndex < halfTurn ? +1d : -1d;
			final int delta = angleIndex < halfTurn ? 0 : halfTurn;

			angleIndex -= delta;
			final int quarterTurn = 1 << magnitude-2;
			final double value = angleIndex < quarterTurn
				? +this.sines[angleIndex]
				: +this.sines[halfTurn - angleIndex];
			return signum * value;
		}


		/**
		 * Returns the cached function value <tt>cos(&tau;&sdot;k/2<sup>magnitude</sup>)</tt>. Note
		 * that the given argument {@code k} is not limited to any argument range.
		 * @param angleIndex the angle index k
		 * @return the (cached) {@code cosine} value
		 * @see Math#cos(double)
		 */
		public double cos (int angleIndex) {
			final int magnitude = this.magnitude;

			angleIndex = ScalarMath.modExp2(angleIndex, magnitude);
			final int halfTurn = 1 << magnitude-1;
			final double signum = angleIndex < halfTurn ? +1d : -1d;
			if (magnitude == 1) return signum;
			final int delta = angleIndex < halfTurn ? 0 : halfTurn;

			angleIndex -= delta;
			final int quarterTurn = 1 << magnitude-2;
			final double value = angleIndex < quarterTurn
				? +this.sines[quarterTurn - angleIndex] 
				: -this.sines[angleIndex - quarterTurn];
			return signum * value;
		}


		/**
		 * Returns the cached function value <tt>tan(&tau;&sdot;k/2<sup>magnitude</sup>)</tt>. Note
		 * that the given argument {@code k} is not limited to any argument range.
		 * @param angleIndex the angle index k
		 * @return the (cached) {@code tangens} value
		 * @see Math#tan(double)
		 */
		public double tan (int angleIndex) {
			final int magnitude = this.magnitude;
			if (magnitude == 1) return 0;

			angleIndex = ScalarMath.modExp2(angleIndex, magnitude);
			final int halfTurn = 1 << magnitude-1;
			final int delta = angleIndex < halfTurn ? 0 : halfTurn;

			angleIndex -= delta;
			final int quarterTurn = 1 << magnitude-2;
			return angleIndex < quarterTurn
				? +this.sines[angleIndex] / +this.sines[quarterTurn - angleIndex] 
				: -this.sines[angleIndex - quarterTurn] / +this.sines[halfTurn - angleIndex];
		}


		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString () {
			return this.getClass().getName() + "@" + this.magnitude;
		}
	}
}