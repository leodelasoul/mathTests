package de.sb.toolbox.util;

import de.sb.toolbox.Copyright;

/**
 * This facade provides array operations for element swapping, braiding, and unbraiding.
 */
@Copyright(year=2017, holders="Sascha Baumeister")
public final class ArrayCopy {

	/**
	 * Prevents external instantiation.
	 */
	private ArrayCopy () {}


	/**
	 * Swaps the position of the given elements within the given vectors. Note that both the given
	 * vectors and the given indices may be the same.
	 * @param leftVector the left vector operand
	 * @param leftIndex the left index
	 * @param rightVector the right vector operand
	 * @param rightIndex the right index
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if any of the given indices is out of bounds
	 */
	static public void swap (final boolean[] leftVector, final int leftIndex, final boolean[] rightVector, final int rightIndex) throws NullPointerException, ArrayIndexOutOfBoundsException {
		final boolean value = leftVector[leftIndex];
		leftVector[leftIndex] = rightVector[rightIndex];
		rightVector[rightIndex] = value;
	}


	/**
	 * Swaps the position of the given elements within the given vectors. Note that both the given
	 * vectors and the given indices may be the same.
	 * @param leftVector the left vector operand
	 * @param leftIndex the left index
	 * @param rightVector the right vector operand
	 * @param rightIndex the right index
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if any of the given indices is out of bounds
	 */
	static public void swap (final char[] leftVector, final int leftIndex, final char[] rightVector, final int rightIndex) throws NullPointerException, ArrayIndexOutOfBoundsException {
		final char value = leftVector[leftIndex];
		leftVector[leftIndex] = rightVector[rightIndex];
		rightVector[rightIndex] = value;
	}


	/**
	 * Swaps the position of the given elements within the given vectors. Note that both the given
	 * vectors and the given indices may be the same.
	 * @param leftVector the left vector operand
	 * @param leftIndex the left index
	 * @param rightVector the right vector operand
	 * @param rightIndex the right index
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if any of the given indices is out of bounds
	 */
	static public void swap (final byte[] leftVector, final int leftIndex, final byte[] rightVector, final int rightIndex) throws NullPointerException, ArrayIndexOutOfBoundsException {
		final byte value = leftVector[leftIndex];
		leftVector[leftIndex] = rightVector[rightIndex];
		rightVector[rightIndex] = value;
	}


	/**
	 * Swaps the position of the given elements within the given vectors. Note that both the given
	 * vectors and the given indices may be the same.
	 * @param leftVector the left vector operand
	 * @param leftIndex the left index
	 * @param rightVector the right vector operand
	 * @param rightIndex the right index
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if any of the given indices is out of bounds
	 */
	static public void swap (final short[] leftVector, final int leftIndex, final short[] rightVector, final int rightIndex) throws NullPointerException, ArrayIndexOutOfBoundsException {
		final short value = leftVector[leftIndex];
		leftVector[leftIndex] = rightVector[rightIndex];
		rightVector[rightIndex] = value;
	}


	/**
	 * Swaps the position of the given elements within the given vectors. Note that both the given
	 * vectors and the given indices may be the same.
	 * @param leftVector the left vector operand
	 * @param leftIndex the left index
	 * @param rightVector the right vector operand
	 * @param rightIndex the right index
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if any of the given indices is out of bounds
	 */
	static public void swap (final int[] leftVector, final int leftIndex, final int[] rightVector, final int rightIndex) throws NullPointerException, ArrayIndexOutOfBoundsException {
		final int value = leftVector[leftIndex];
		leftVector[leftIndex] = rightVector[rightIndex];
		rightVector[rightIndex] = value;
	}


	/**
	 * Swaps the position of the given elements within the given vectors. Note that both the given
	 * vectors and the given indices may be the same.
	 * @param leftVector the left vector operand
	 * @param leftIndex the left index
	 * @param rightVector the right vector operand
	 * @param rightIndex the right index
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if any of the given indices is out of bounds
	 */
	static public void swap (final long[] leftVector, final int leftIndex, final long[] rightVector, final int rightIndex) throws NullPointerException, ArrayIndexOutOfBoundsException {
		final long value = leftVector[leftIndex];
		leftVector[leftIndex] = rightVector[rightIndex];
		rightVector[rightIndex] = value;
	}


	/**
	 * Swaps the position of the given elements within the given vectors. Note that both the given
	 * vectors and the given indices may be the same.
	 * @param leftVector the left vector operand
	 * @param leftIndex the left index
	 * @param rightVector the right vector operand
	 * @param rightIndex the right index
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if any of the given indices is out of bounds
	 */
	static public void swap (final float[] leftVector, final int leftIndex, final float[] rightVector, final int rightIndex) throws NullPointerException, ArrayIndexOutOfBoundsException {
		final float value = leftVector[leftIndex];
		leftVector[leftIndex] = rightVector[rightIndex];
		rightVector[rightIndex] = value;
	}


	/**
	 * Swaps the position of the given elements within the given vectors. Note that both the given
	 * vectors and the given indices may be the same.
	 * @param leftVector the left vector operand
	 * @param leftIndex the left index
	 * @param rightVector the right vector operand
	 * @param rightIndex the right index
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if any of the given indices is out of bounds
	 */
	static public void swap (final double[] leftVector, final int leftIndex, final double[] rightVector, final int rightIndex) throws NullPointerException, ArrayIndexOutOfBoundsException {
		final double value = leftVector[leftIndex];
		leftVector[leftIndex] = rightVector[rightIndex];
		rightVector[rightIndex] = value;
	}


	/**
	 * Swaps the position of the given elements within the given vectors. Note that both the given
	 * vectors and the given indices may be the same.
	 * @param leftVector the left vector operand
	 * @param leftIndex the left index
	 * @param rightVector the right vector operand
	 * @param rightIndex the right index
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws ArrayIndexOutOfBoundsException if any of the given indices is out of bounds
	 */
	static public <T> void swap (final T[] leftVector, final int leftIndex, final T[] rightVector, final int rightIndex) throws NullPointerException, ArrayIndexOutOfBoundsException {
		final T value = leftVector[leftIndex];
		leftVector[leftIndex] = rightVector[rightIndex];
		rightVector[rightIndex] = value;
	}


	/**
	 * {@code Braids} the elements of the given left and right operands by alternately copying their
	 * elements into the given braid vector in sequence.
	 * @param left the left vector operand, containing the {@code even} indexed elements for the
	 *        braided vector
	 * @param right the right vector operand, containing the {@code odd} indexed elements for the
	 *        braided vector
	 * @param braid the braided result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void braid (final boolean[] left, final boolean[] right, final boolean[] braid) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			braid[index] = left[index >> 1];
			braid[index + 1] = right[index >> 1];
		}
	}


	/**
	 * {@code Braids} the elements of the given left and right operands by alternately copying their
	 * elements into the given braid vector in sequence.
	 * @param left the left vector operand, containing the {@code even} indexed elements for the
	 *        braided vector
	 * @param right the right vector operand, containing the {@code odd} indexed elements for the
	 *        braided vector
	 * @param braid the braided result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void braid (final char[] left, final char[] right, final char[] braid) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			braid[index] = left[index >> 1];
			braid[index + 1] = right[index >> 1];
		}
	}


	/**
	 * {@code Braids} the elements of the given left and right operands by alternately copying their
	 * elements into the given braid vector in sequence.
	 * @param left the left vector operand, containing the {@code even} indexed elements for the
	 *        braided vector
	 * @param right the right vector operand, containing the {@code odd} indexed elements for the
	 *        braided vector
	 * @param braid the braided result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void braid (final byte[] left, final byte[] right, final byte[] braid) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			braid[index] = left[index >> 1];
			braid[index + 1] = right[index >> 1];
		}
	}


	/**
	 * {@code Braids} the elements of the given left and right operands by alternately copying their
	 * elements into the given braid vector in sequence.
	 * @param left the left vector operand, containing the {@code even} indexed elements for the
	 *        braided vector
	 * @param right the right vector operand, containing the {@code odd} indexed elements for the
	 *        braided vector
	 * @param braid the braided result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void braid (final short[] left, final short[] right, final short[] braid) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			braid[index] = left[index >> 1];
			braid[index + 1] = right[index >> 1];
		}
	}


	/**
	 * {@code Braids} the elements of the given left and right operands by alternately copying their
	 * elements into the given braid vector in sequence.
	 * @param left the left vector operand, containing the {@code even} indexed elements for the
	 *        braided vector
	 * @param right the right vector operand, containing the {@code odd} indexed elements for the
	 *        braided vector
	 * @param braid the braided result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void braid (final int[] left, final int[] right, final int[] braid) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			braid[index] = left[index >> 1];
			braid[index + 1] = right[index >> 1];
		}
	}


	/**
	 * {@code Braids} the elements of the given left and right operands by alternately copying their
	 * elements into the given braid vector in sequence.
	 * @param left the left vector operand, containing the {@code even} indexed elements for the
	 *        braided vector
	 * @param right the right vector operand, containing the {@code odd} indexed elements for the
	 *        braided vector
	 * @param braid the braided result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void braid (final long[] left, final long[] right, final long[] braid) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			braid[index] = left[index >> 1];
			braid[index + 1] = right[index >> 1];
		}
	}


	/**
	 * {@code Braids} the elements of the given left and right operands by alternately copying their
	 * elements into the given braid vector in sequence.
	 * @param left the left vector operand, containing the {@code even} indexed elements for the
	 *        braided vector
	 * @param right the right vector operand, containing the {@code odd} indexed elements for the
	 *        braided vector
	 * @param braid the braided result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void braid (final float[] left, final float[] right, final float[] braid) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			braid[index] = left[index >> 1];
			braid[index + 1] = right[index >> 1];
		}
	}


	/**
	 * {@code Braids} the elements of the given left and right operands by alternately copying their
	 * elements into the given braid vector in sequence.
	 * @param left the left vector operand, containing the {@code even} indexed elements for the
	 *        braided vector
	 * @param right the right vector operand, containing the {@code odd} indexed elements for the
	 *        braided vector
	 * @param braid the braided result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void braid (final double[] left, final double[] right, final double[] braid) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			braid[index] = left[index >> 1];
			braid[index + 1] = right[index >> 1];
		}
	}


	/**
	 * {@code Braids} the elements of the given left and right operands by alternately copying their
	 * elements into the given braid vector in sequence.
	 * @param left the left vector operand, containing the {@code even} indexed elements for the
	 *        braided vector
	 * @param right the right vector operand, containing the {@code odd} indexed elements for the
	 *        braided vector
	 * @param braid the braided result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public <T> void braid (final T[] left, final T[] right, final T[] braid) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			braid[index] = left[index >> 1];
			braid[index + 1] = right[index >> 1];
		}
	}


	/**
	 * {@code Unbraides} the elements of the given braided vector operand by copying it's even
	 * indexed elements into the given left result vector, and it's odd indexed elements into the
	 * given right result vector.
	 * @param braid the braided vector operand, with even indexed elements for the left vector, and
	 *        odd indexed elements for the right vector
	 * @param left the unbraided left result vector
	 * @param right the unbraided right result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void unbraid (final boolean[] braid, final boolean[] left, final boolean[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			left[index >> 1] = braid[index];
			right[index >> 1] = braid[index + 1];
		}
	}


	/**
	 * {@code Unbraides} the elements of the given braided vector operand by copying it's even
	 * indexed elements into the given left result vector, and it's odd indexed elements into the
	 * given right result vector.
	 * @param braid the braided vector operand, with even indexed elements for the left vector, and
	 *        odd indexed elements for the right vector
	 * @param left the unbraided left result vector
	 * @param right the unbraided right result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void unbraid (final char[] braid, final char[] left, final char[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			left[index >> 1] = braid[index];
			right[index >> 1] = braid[index + 1];
		}
	}


	/**
	 * {@code Unbraides} the elements of the given braided vector operand by copying it's even
	 * indexed elements into the given left result vector, and it's odd indexed elements into the
	 * given right result vector.
	 * @param braid the braided vector operand, with even indexed elements for the left vector, and
	 *        odd indexed elements for the right vector
	 * @param left the unbraided left result vector
	 * @param right the unbraided right result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void unbraid (final byte[] braid, final byte[] left, final byte[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			left[index >> 1] = braid[index];
			right[index >> 1] = braid[index + 1];
		}
	}


	/**
	 * {@code Unbraides} the elements of the given braided vector operand by copying it's even
	 * indexed elements into the given left result vector, and it's odd indexed elements into the
	 * given right result vector.
	 * @param braid the braided vector operand, with even indexed elements for the left vector, and
	 *        odd indexed elements for the right vector
	 * @param left the unbraided left result vector
	 * @param right the unbraided right result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void unbraid (final short[] braid, final short[] left, final short[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			left[index >> 1] = braid[index];
			right[index >> 1] = braid[index + 1];
		}
	}


	/**
	 * {@code Unbraides} the elements of the given braided vector operand by copying it's even
	 * indexed elements into the given left result vector, and it's odd indexed elements into the
	 * given right result vector.
	 * @param braid the braided vector operand, with even indexed elements for the left vector, and
	 *        odd indexed elements for the right vector
	 * @param left the unbraided left result vector
	 * @param right the unbraided right result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void unbraid (final int[] braid, final int[] left, final int[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			left[index >> 1] = braid[index];
			right[index >> 1] = braid[index + 1];
		}
	}


	/**
	 * {@code Unbraides} the elements of the given braided vector operand by copying it's even
	 * indexed elements into the given left result vector, and it's odd indexed elements into the
	 * given right result vector.
	 * @param braid the braided vector operand, with even indexed elements for the left vector, and
	 *        odd indexed elements for the right vector
	 * @param left the unbraided left result vector
	 * @param right the unbraided right result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void unbraid (final long[] braid, final long[] left, final long[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			left[index >> 1] = braid[index];
			right[index >> 1] = braid[index + 1];
		}
	}


	/**
	 * {@code Unbraides} the elements of the given braided vector operand by copying it's even
	 * indexed elements into the given left result vector, and it's odd indexed elements into the
	 * given right result vector.
	 * @param braid the braided vector operand, with even indexed elements for the left vector, and
	 *        odd indexed elements for the right vector
	 * @param left the unbraided left result vector
	 * @param right the unbraided right result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void unbraid (final float[] braid, final float[] left, final float[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			left[index >> 1] = braid[index];
			right[index >> 1] = braid[index + 1];
		}
	}


	/**
	 * {@code Unbraides} the elements of the given braided vector operand by copying it's even
	 * indexed elements into the given left result vector, and it's odd indexed elements into the
	 * given right result vector.
	 * @param braid the braided vector operand, with even indexed elements for the left vector, and
	 *        odd indexed elements for the right vector
	 * @param left the unbraided left result vector
	 * @param right the unbraided right result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public void unbraid (final double[] braid, final double[] left, final double[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			left[index >> 1] = braid[index];
			right[index >> 1] = braid[index + 1];
		}
	}


	/**
	 * {@code Unbraides} the elements of the given braided vector operand by copying it's even
	 * indexed elements into the given left result vector, and it's odd indexed elements into the
	 * given right result vector.
	 * @param braid the braided vector operand, with even indexed elements for the left vector, and
	 *        odd indexed elements for the right vector
	 * @param left the unbraided left result vector
	 * @param right the unbraided right result vector
	 * @throws NullPointerException if any of the given arguments is {@code null}
	 * @throws IllegalArgumentException if the given left and right vectors don't share the same
	 *         length, or if the given braid vector isn't double their size
	 */
	static public <T> void unbraid (final T[] braid, final T[] left, final T[] right) throws NullPointerException, IllegalArgumentException {
		if (left.length != right.length | left.length + right.length != braid.length) throw new IllegalArgumentException();
	
		for (int index = 0; index < braid.length; index += 2) {
			left[index >> 1] = braid[index];
			right[index >> 1] = braid[index + 1];
		}
	}
}