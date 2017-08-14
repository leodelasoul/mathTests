package de.sb.toolbox.math;

import java.lang.reflect.Array;
import de.sb.toolbox.Copyright;
import de.sb.toolbox.math.Complex.MutableDoublePrecision;
import de.sb.toolbox.math.Complex.MutableSinglePrecision;
import de.sb.toolbox.util.ArraySupport;


/**
 * Recursive FFT operations (Danielson & Lanczos 1942). This implementation is comparatively
 * slow, but suitable for reference purposes as it's kept clean of dependencies.
 */
@Copyright(year=2012, holders="Sascha Baumeister")
public class FastFourierTransform {

    /**
     * This operation performs a {@code fast fourier transform} on the given vector. Only
     * vectors of power two length {@code N} with <tt>magnitude = log<sub>2</sub>(N)</tt> are
     * supported.
     * @param <T> the complex component type
     * @param inverse whether or not an {@code inverse} fourier transform shall be performed
     * @param vector an array of <tt>N = 2<sup>magnitude</sup></tt> complex numbers
     * @throws NullPointerException if the given vector is {@code null}
     * @throws IllegalArgumentException if the given vector has no power of two length
     */
    static public <T extends MutableSinglePrecision<T>> void transform (final boolean inverse, final T[] vector) throws NullPointerException, IllegalArgumentException {
        if (vector.length < 2) return;

        final int magnitude = Math.getExponent((double) vector.length);
        if (1 << magnitude != vector.length) throw new IllegalArgumentException();

        if (inverse) {
            for (int index = 0; index < vector.length; ++index) vector[index].conj();
        }

        transform(magnitude, vector);

        if (inverse) {
            final float norm = Math.scalb(1f, -magnitude);
            for (int index = 0; index < vector.length; ++index) vector[index].mul(norm).conj();
        }
    }


    /**
     * Recursive implementation of {@link #transform(boolean, MutableSinglePrecision[])}.
     * @param <T> the complex component type
     * @param magnitude the value <tt>log<sub>2</sub>(N)</tt>
     * @param vector an array of <tt>N = 2<sup>magnitude</sup></tt> complex numbers
     * @throws NullPointerException if the given vector is {@code null}
     * @throws ArrayIndexOutOfBoundsException if the given vector has no power of two length
     */
    static private <T extends MutableSinglePrecision<T>> void transform (final int magnitude, final T[] vector) throws NullPointerException, ArrayIndexOutOfBoundsException {
        assert magnitude > 0;

        if (magnitude == 1) {
            vector[0].mux(vector[1]);
            return;
        }

        // prepare stage: divide vector into even and odd indexed parts
        final int half = 1 << magnitude-1;
        @SuppressWarnings("unchecked")
        final T[] even = (T[]) Array.newInstance(vector.getClass().getComponentType(), half);
        @SuppressWarnings("unchecked")
        final T[] odd  = (T[]) Array.newInstance(vector.getClass().getComponentType(), half);
        for (int index = 0; index < half; ++index) {
            even[index] = vector[(index << 1) + 0];
            odd [index] = vector[(index << 1) + 1];
        }

        // divide stage: transform partial terms
        transform(magnitude - 1, even);
        transform(magnitude - 1, odd);

        // conquer stage: recombine partial results
        // note: use explicit polar unit conversion to preserve compatibility with table based float FFTs
        final T unit = vector[0].clone();
        for (int index = 0; index < half; ++index) {
            final double angle = 2 * Math.PI * index / vector.length;
            unit.setCartesian((float) Math.cos(angle), (float) Math.sin(angle));
            mux(even[index], odd[index].mul(unit));
            vector[index] = even[index];
            vector[index + half] = odd[index];
        }
    }


    /**
     * This operation performs a {@code fast fourier transform} on the given vector. Only
     * vectors of power two length {@code N} with <tt>magnitude = log<sub>2</sub>(N)</tt> are
     * supported.
     * @param <T> the complex component type
     * @param inverse whether or not an {@code inverse} fourier transform shall be performed
     * @param vector an array of <tt>N = 2<sup>magnitude</sup></tt> complex numbers
     * @throws NullPointerException if the given vector is {@code null}
     * @throws IllegalArgumentException if the given vector has no power of two length
     */
    static public <T extends MutableDoublePrecision<T>> void transform (final boolean inverse, final T[] vector) {
        if (vector.length < 2) return;

        final int magnitude = Math.getExponent((double) vector.length);
        if (1 << magnitude != vector.length) throw new IllegalArgumentException();

        if (inverse) {
            for (int index = 0; index < vector.length; ++index) vector[index].conj();
        }

        transform(magnitude, vector);

        final double norm = Math.scalb(1d, -magnitude);
        if (inverse) {
            for (int index = 0; index < vector.length; ++index) vector[index].mul(norm).conj();
        }
    }


    /**
     * Recursive implementation of {@link #transform(boolean, MutableDoublePrecision[])}.
     * @param <T> the complex component type
     * @param magnitude the value <tt>log<sub>2</sub>(N)</tt>
     * @param vector an array of <tt>N = 2<sup>magnitude</sup></tt> complex numbers
     * @throws NullPointerException if the given vector is {@code null}
     * @throws ArrayIndexOutOfBoundsException if the given vector has no power of two length
     */
    static private <T extends MutableDoublePrecision<T>> void transform (final int magnitude, final T[] vector) throws NullPointerException, ArrayIndexOutOfBoundsException {
        assert magnitude > 0;

        if (magnitude == 1) {
            vector[0].mux(vector[1]);
            return;
        }

        // prepare stage: divide vector into even and odd indexed parts
        final int half = 1 << magnitude-1;
        @SuppressWarnings("unchecked")
        final T[] even = (T[]) Array.newInstance(vector.getClass().getComponentType(), half);
        @SuppressWarnings("unchecked")
        final T[] odd  = (T[]) Array.newInstance(vector.getClass().getComponentType(), half);
        for (int index = 0; index < half; ++index) {
            even[index] = vector[(index << 1) + 0];
            odd [index] = vector[(index << 1) + 1];
        }

        // divide stage: transform partial terms
        transform(magnitude - 1, even);
        transform(magnitude - 1, odd);

        // conquer stage: recombine partial results
        // note: use explicit polar unit conversion to preserve compatibility with table based float FFTs
        final T unit = vector[0].clone();
        for (int index = 0; index < half; ++index) {
            final double angle = 2 * Math.PI * index / vector.length;
            unit.setCartesian(Math.cos(angle), Math.sin(angle));
            mux(even[index], odd[index].mul(unit));
            vector[index] = even[index];
            vector[index + half] = odd[index];
        }
    }


    /**
     * Adaper for {@link #transform(boolean, MutableSinglePrecision[])}.
     * @param values an array of <tt>N = 2*2<sup>magnitude</sup></tt> braided real and imaginary values
     * @throws NullPointerException if any of the given arguments is {@code null}
     * @throws IllegalArgumentException if the length of the given array is not a power of two
     */
    static public void transform (final boolean inverse, final float[] values) throws NullPointerException, IllegalArgumentException {
        if ((values.length & 1) != 0) throw new IllegalArgumentException();
        if (values.length < 4) return;

        final FloatCartesianComplex[] vector = new FloatCartesianComplex[values.length >> 1];
        for (int index = 0; index < values.length; index += 2) {
            vector[index >> 1] = new FloatCartesianComplex(values[index], values[index + 1]);
        }

        transform(inverse, vector);

        for (int index = 0; index < values.length; index += 2) {
            final FloatCartesianComplex complex = vector[index >> 1];
            values[index]     = complex.re();
            values[index + 1] = complex.im();
        }
    }


    /**
     * Adaper for {@link #transform(boolean, MutableDoublePrecision[])}.
     * @param values an array of <tt>N = 2*2<sup>magnitude</sup></tt> braided real and imaginary values
     * @throws NullPointerException if any of the given arguments is {@code null}
     * @throws IllegalArgumentException if the length of the given array is not a power of two
     */
    static public void transform (final boolean inverse, final double[] values) throws NullPointerException, IllegalArgumentException {
        if ((values.length & 1) != 0) throw new IllegalArgumentException();
        if (values.length < 4) return;

        final DoubleCartesianComplex[] vector = new DoubleCartesianComplex[values.length >> 1];
        for (int index = 0; index < values.length; index += 2) {
            vector[index >> 1] = new DoubleCartesianComplex(values[index], values[index + 1]);
        }

        transform(inverse, vector);

        for (int index = 0; index < values.length; index += 2) {
            final DoubleCartesianComplex complex = vector[index >> 1];
            values[index]     = complex.re();
            values[index + 1] = complex.im();
        }
    }


    /**
     * Adaper for {@link #transform(boolean, MutableSinglePrecision[])}.
     * @param real an array of <tt>N = 2<sup>magnitude</sup></tt> real values
     * @param imag an array of <tt>N = 2<sup>magnitude</sup></tt> imaginary values
     * @throws NullPointerException if any of the given arguments is {@code null}
     * @throws IllegalArgumentException if the length of the given arguments doesn't match or
     *         is not a power of two
     */
    static public void transform (final float[] real, final float[] imag) throws NullPointerException, IllegalArgumentException {
        final float[] values = new float[real.length + imag.length];
        ArraySupport.braid(real, imag, values);
        transform(false, values);
        ArraySupport.unbraid(values, real, imag);
    }


    /**
     * Adaper for {@link #transform(boolean, MutableDoublePrecision[])}.
     * @param real an array of <tt>N = 2<sup>magnitude</sup></tt> real values
     * @param imag an array of <tt>N = 2<sup>magnitude</sup></tt> imaginary values
     * @throws NullPointerException if any of the given arguments is {@code null}
     * @throws IllegalArgumentException if the length of the given arguments doesn't match or
     *         is not a power of two
     */
    static public void transform (final double[] real, final double[] imag) throws NullPointerException, IllegalArgumentException {
        final double[] values = new double[real.length + imag.length];
        ArraySupport.braid(real, imag, values);
        transform(false, values);
        ArraySupport.unbraid(values, real, imag);
    }


    /**
     * Assigns the values <tt>z<sub>1</sub>' = z<sub>1</sub>+z<sub>2</sub></tt> and
     * <tt>z<sub>2</sub>' = z<sub>1</sub>-z<sub>2</sub></tt> to the receiver
     * <tt>z<sub>1</sub></tt> and the given value <tt>z<sub>2</sub></tt> respectively.
     * @param <T> the complex argument type
     * @param left the complex operand <tt>z<sub>1</sub></tt>
     * @param right the complex operand <tt>z<sub>2</sub></tt>
     * @throws NullPointerException if any of the given arguments is {@code null}
     */
    static private <T extends MutableSinglePrecision<T>> void mux (final T left, final T right) throws NullPointerException {
        final float re1 = left.re(), im1 = left.im(), re2 = right.re(), im2 = right.im();
        left.setCartesian(re1 + re2, im1 + im2);
        right.setCartesian(re1 - re2, im1 - im2);
    }


    /**
     * Assigns the values <tt>z<sub>1</sub>' = z<sub>1</sub>+z<sub>2</sub></tt> and
     * <tt>z<sub>2</sub>' = z<sub>1</sub>-z<sub>2</sub></tt> to the receiver
     * <tt>z<sub>1</sub></tt> and the given value <tt>z<sub>2</sub></tt> respectively.
     * @param <T> the complex argument type
     * @param left the complex operand <tt>z<sub>1</sub></tt>
     * @param right the complex operand <tt>z<sub>2</sub></tt>
     * @throws NullPointerException if any of the given arguments is {@code null}
     */
    static private <T extends MutableDoublePrecision<T>> void mux (final T left, final T right) throws NullPointerException {
        final double re1 = left.re(), im1 = left.im(), re2 = right.re(), im2 = right.im();
        left.setCartesian(re1 + re2, im1 + im2);
        right.setCartesian(re1 - re2, im1 - im2);
    }
}