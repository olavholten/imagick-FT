package se.imagick.ft.fft;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ft.common.Complex;
import se.imagick.ft.common.FTUtils;
import se.imagick.ft.common.Polar;

public class FftDifTest {

    @Test
    public void executeGivesCorrectOutputForOddFrequency() {

        double size = 8d;
        double freq = 1d;
        double amplitude = 2.3d;
        double phase = 1.5;

        double[] realValues = getSine(size, freq, amplitude, phase);

        FftDif fftDif = new FftDif((int) size);
        double[][] complexArrays = fftDif.execute(realValues);

        assertFrequencies(0, 0, 0, complexArrays);
        assertFrequencies(1, amplitude, phase, complexArrays);
        assertFrequencies(2, 0, 0, complexArrays);
        assertFrequencies(3, 0, 0, complexArrays);
        assertFrequencies(4, 0, 0, complexArrays);
    }
    @Test
    public void executeGivesCorrectOutputForEvenFrequency() {

        double size = 8d;
        double freq = 2d;
        double amplitude = 1.3d;
        double phase = 2.5;

        double[] realValues = getSine(size, freq, amplitude, phase);

        FftDif fftDif = new FftDif((int) size);
        double[][] complexArrays = fftDif.execute(realValues);

        assertFrequencies(0, 0, 0, complexArrays);
        assertFrequencies(1, 0, 0, complexArrays);
        assertFrequencies(2, amplitude, phase, complexArrays);
        assertFrequencies(3, 0, 0, complexArrays);
        assertFrequencies(4, 0, 0, complexArrays);
    }

    private void assertFrequencies(int freq, double amplitude, double phase, double[][] complexArrays) {
        Complex complex = new Complex(complexArrays[0][freq], complexArrays[1][freq]);
        Polar polar = new Polar();
        FTUtils.complex2Polar(complex, polar);

        Assert.assertEquals(amplitude, polar.getMagnitude(), 0.00001);

        // Strange phase values occurs at very low complex values.
        if (complex.getReal() > 0.00001 && complex.getImaginary() > 0.00001) {
            Assert.assertEquals(phase % Math.PI, polar.getPhase(), 0.00001);
        }
    }

    private double[] getSine(double size, double freq, double amplitude, double phase) {
        double[] realValues = new double[(int) size];

        for (double i = 0; i < size; i++) {
            double angle = (2d * Math.PI * (freq * i / size)) + phase;
            double value = Math.cos(angle) * amplitude;
            realValues[(int) i] = value;
        }
        return realValues;
    }
}