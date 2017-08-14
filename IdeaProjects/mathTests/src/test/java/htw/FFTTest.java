package htw;


import de.sb.toolbox.math.ComplexMath;

import de.sb.toolbox.math.DoubleCartesianComplex;
import de.sb.toolbox.math.FastFourierTransform;
import de.sb.toolbox.util.ArraySupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ThreadLocalRandom;


class FFTTest  {


    static private final byte MAGNITUDE = 18;
    static private final double DOUBLE_PRECISION = 1E-11f;


@Test
void testingWhetherGPUIsUsed(){
    final double[] values = randomDoubleVector(MAGNITUDE + 1);
    final DoubleCartesianComplex[] vector = new DoubleCartesianComplex[values.length >> 1];
    for (int index = 0; index < vector.length; ++index) {
        vector[index] = new DoubleCartesianComplex(values[index << 1], values[(index << 1) + 1]);
    }

    DoubleCartesianComplex myMath = new DoubleCartesianComplex();
    for (final boolean inverse : new boolean[] { false, true }) {
        final DoubleCartesianComplex[] fft1 = ComplexMath.clone(vector);
        final DoubleCartesianComplex[] fft2 = ComplexMath.clone(vector);
        final DoubleCartesianComplex unit = new DoubleCartesianComplex();

        FastFourierTransform.transform(inverse, fft1);
        unit.fft(inverse, false, fft2);
        for (int index = 0; index < vector.length; ++index) {
            assertEquals(fft1[index].re(), fft2[index].re(), DOUBLE_PRECISION);
            assertEquals(fft1[index].im(), fft2[index].im(), DOUBLE_PRECISION);
        }
    }
}

    static private double[] randomDoubleVector (final int magnitude) {
        final double[] vector = new double[1 << magnitude];
        for (int index = 0; index < vector.length; ++index) {
            vector[index] = 2 * ThreadLocalRandom.current().nextDouble() - 1;
        }
        return vector;
    }



}
