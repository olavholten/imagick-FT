package se.imagick.ft.slidingdft;

import org.junit.Assert;
import org.junit.Test;
import se.imagick.ft.common.Complex;
import se.imagick.ft.common.Polar;


/**
 * Parent of the DFTSlider implementation test classes, making sure all implementations are API-tested the same way.
 *
 * ---------------------
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olav Holten
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public abstract class DFTSliderTestParent {

    private double[] comp1 = new double[] {1, 0.7071d, 0, -0.7071, -1, -0.7071, 0, 0.7071};
    private double[] comp1PhasedPlus90 = new double[] {0, -0.7071, -1, -0.7071, 0, 0.7071, 1, 0.7071d};
    private double[] comp2 = new double[] {1, 0, -1, 0, 1, 0, -1, 0};
    private double[] comp2PhasedMinus90 = new double[] {0, 1, 0, -1, 0, 1, 0, -1};
    private double[] comp3 = new double[] {1.0, -0.7071, 0.0, 0.7071, -1.0, 0.7071, 0.0, -0.7071};
    private double[] comp4 = new double[] {1, -1, 1, -1, 1, -1, 1, -1};
    private double[] zeroSamples = new double[] {0, 0, 0, 0, 0, 0, 0, 0};

    @Test
    public void testComp1() {
        int dc = 2;
        DFTSlider slider = getSliderWithComponents(dc, comp1);
        verifyAmplitude(slider, dc, 1, 0, 0, 0);
        verifyRealSum(slider, dc, comp1);
    }

    @Test
    public void test124Comp() {
        int dc = 5;
        double[][] comps = {comp1, comp2, comp4};
        DFTSlider slider = getSliderWithComponents(dc, comps);
        verifyAmplitude(slider, dc, 1, 1, 0, 1);
        verifyRealSum(slider, dc, comps);
    }

    @Test
    public void test1234Comp() {
        int dc = 8;
        double[][] comps = {comp1, comp2, comp3, comp4};
        DFTSlider slider = getSliderWithComponents(dc, comps);
        verifyAmplitude(slider, dc, 1, 1, 1, 1);
        verifyRealSum(slider, dc, comps);
    }

    @Test
    public void test1234CompNegDc() {
        double[][] comps = {comp1, comp2, comp3, comp4};
        int dc = -8;
        DFTSlider slider = getSliderWithComponents(dc, comps);
        verifyAmplitude(slider, 8, 1, 1, 1, 1);
        verifyRealSum(slider, dc, comps);
        Assert.assertEquals(slider.getComplex(0).getReal(), dc, 0.1);
        Assert.assertEquals(slider.getComplex(0).getImaginary(), 0, 0.1);
        Assert.assertEquals(slider.getPolar(0).getPhase() * 360 / (2 * Math.PI), 180, 0.1);
    }

    @Test
    public void test23() {
        int dc = 3;
        double[][] comps = {comp2, comp3};
        DFTSlider slider = getSliderWithComponents(dc, comps);
        verifyAmplitude(slider, dc, 0, 1, 1, 0);
        verifyRealSum(slider, dc, comps);
    }

    @Test
    public void testJustNegDc() {
        DFTSlider slider = getSliderWithComponents(-8, zeroSamples);
        verifyAmplitude(slider, 8, 0, 0, 0, 0);
        Assert.assertEquals(slider.getComplex(0).getReal(), -8, 0.1);
        Assert.assertEquals(slider.getPolar(0).getPhase() * 360 / (2 * Math.PI), 180, 0.1);
    }

    @Test
    public void test2Phased() {
        int dc = 4;
        double[][] comps = {comp1PhasedPlus90, comp2PhasedMinus90};
        DFTSlider slider = getSliderWithComponents(dc, comps);
        verifyAmplitude(slider, dc, 1, 1, 0, 0);
        verifyRealSum(slider, dc, comps);
    }

    @Test
    public void testLatency() {
        DFTSlider slider = getSliderWithComponents(4, comp1PhasedPlus90, comp2PhasedMinus90);
        Assert.assertEquals(comp1PhasedPlus90.length, slider.getLatencyInSamples());
    }

    @Test
    public void testNoOfFrequencies() {
        DFTSlider slider = getSliderWithComponents(4, comp1PhasedPlus90, comp2PhasedMinus90);
        Assert.assertEquals(((int)(comp1PhasedPlus90.length / 2d + 1)), slider.getNoOfFrequencies());
    }

    @Test
    public void testRecalculation() {
        int dc1 = 1;
        int dc2 = 2;
        DFTSlider slider = getSliderWithComponents(dc1, comp1);
        verifyAmplitude(slider, dc1, 1, 0, 0, 0);
        verifyRealSum(slider, dc1, comp1);

        Complex complex = slider.getComplex(0);
        complex.setReal(dc2);
        slider.setComplex(0, complex);
        verifyRealSum(slider, dc2, true, comp1);
    }

    @Test
    public void gettersAndSettersComplex() {
        int dc1 = 1;
        DFTSlider slider = getSliderWithComponents(dc1, comp1);

        Polar polar = new Polar(slider.getPolar(3));
        Complex complex = new Complex(1001, 2002);
        slider.setComplex(3, complex);
        Complex complexFromSlider = slider.getComplex(3);
        Polar polarFromSlider = slider.getPolar(3);
        Assert.assertTrue(complex.equals(complexFromSlider));
        Assert.assertNotEquals(polar, polarFromSlider);
    }

    @Test
    public void gettersAndSettersPolar() {
        int dc1 = 1;
        DFTSlider slider = getSliderWithComponents(dc1, comp1);

        Complex complex = new Complex(slider.getComplex(3));
        Polar polar = new Polar(1001, 2002);
        slider.setPolar(3, polar);
        Polar polarFromSlider = slider.getPolar(3);
        Complex complexFromSlider = slider.getComplex(3);
        Assert.assertTrue(polar.equals(polarFromSlider));
        Assert.assertNotEquals(complex, complexFromSlider);
    }

    public void nothing() {
        int noOfFrequencies = 16;
        DFTSlider slider = new DFTSliderImpl(noOfFrequencies); // One component will be added for the dc.
        slider.slide(1.0d); // Adding one sample value to the end of the buffer, and pushing out the first.

        int frequencyComponentNo = 3;
        Polar polar3 = slider.getPolar(frequencyComponentNo);
        Complex complex3 = slider.getComplex(frequencyComponentNo);

        double magnitude3 = polar3.getMagnitude();
        double phase3 = polar3.getPhase();
        double real3 = complex3.getReal();
        double imag3 = complex3.getImaginary();

        double realSum = slider.getRealSum(false);
    }



    private DFTSlider getSliderWithComponents(double dc, double[]... samplesSeries) {
        DFTSlider slider = getSliderImpl(samplesSeries[0].length / 2);
        double[] tot = addRealComponents(samplesSeries);
        slideAll(slider, tot, dc);

        return slider;
    }

    private double[] addRealComponents(double[]... components) {
        double[] tot = new double[comp1.length];
        for(double[] comp : components) {
            addComponents(tot, comp);
        }

        return tot;
    }

    private void addComponents(double[] tot, double[] addition) {
        for(int i = 0; i < tot.length; i++) {
            tot[i] += addition[i];
        }
    }

    private void slideAll(DFTSlider slider, double[] samples, double dc) {
        for(double sample : samples) {
            slider.slide(sample + dc);
        }
    }

    private void verifyAmplitude(DFTSlider slider, double... values) {
        for(int i = 0; i < values.length; i++) {
            Assert.assertEquals("Component no: " + i, values[i], slider.getPolar(i).getMagnitude(), 0.1);
        }
    }

    private void verifyRealSum(DFTSlider slider, double dc, double[]... components) {
        verifyRealSum(slider, dc, false, components);
    }

    private void verifyRealSum(DFTSlider slider, double dc, boolean willRecalculate,  double[]... components) {
        double sum = dc;

        for(double[] component : components) {
            sum += component[0];
        }

        Assert.assertEquals(slider.getRealSum(willRecalculate), sum, 0.1d);
    }

    private double round(double value) {
        return (((double)((int)(value * 100d + ((value > 0)?0.5d : -0.5d)))) / 100d);
    }

    private double getPhaseInDegrees(DFTSliderCompactImpl slider, int i, double amp) {
        double degrees = (amp == 0)?0 : (((slider.getPolar(i).getPhase() * 360) / (2d * Math.PI)) % 360);
        return (degrees > 180)?degrees - 360 : degrees;
    }

    private void println(DFTSliderCompactImpl slider) {
        for(int i = 0; i < slider.getNoOfFrequencies(); i++) {
            double amp = round(slider.getPolar(i).getMagnitude());
            double phase = round(getPhaseInDegrees(slider, i, amp));
            System.out.println("[" + amp + ", " + phase + "]");
        }

        System.out.println("---------");
    }

    abstract DFTSlider getSliderImpl(int noOfFrequencies);
}
