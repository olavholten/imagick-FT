package se.imagick.ft.slidingdft;

import org.junit.Assert;
import org.junit.Test;

public abstract class DFTSliderTestParent {

    private double[] comp1 = new double[] {1, 0.7071d, 0, -0.7071, -1, -0.7071, 0, 0.7071};
    private double[] comp1PhasedPlus90 = new double[] {0, -0.7071, -1, -0.7071, 0, 0.7071, 1, 0.7071d};
    private double[] comp2 = new double[] {1, 0, -1, 0, 1, 0, -1, 0};
    private double[] comp2PhasedMinus90 = new double[] {0, 1, 0, -1, 0, 1, 0, -1};
    private double[] comp3 = new double[] {1.0, -0.7071, 0.0, 0.7071, -1.0, 0.7071, 0.0, -0.7071};
    private double[] comp4 = new double[] {1, -1, 1, -1, 1, -1, 1, -1};

    @Test
    public void testComp1() {
        DFTSlider slider = getSliderWithComponents(2, comp1);
        verifyAmplitude(slider, 2, 1, 0, 0, 0);
    }

    @Test
    public void test124Comp() {
        DFTSlider slider = getSliderWithComponents(5, comp1, comp2, comp4);
        verifyAmplitude(slider, 5, 1, 1, 0, 1);
    }

    @Test
    public void test1234Comp() {
        DFTSlider slider = getSliderWithComponents(8, comp1, comp2, comp3, comp4);
        verifyAmplitude(slider, 8, 1, 1, 1, 1);
    }

    @Test
    public void test1234CompNegDc() {
        DFTSlider slider = getSliderWithComponents(-8, comp1, comp2, comp3, comp4);
        verifyAmplitude(slider, 8, 1, 1, 1, 1);
        Assert.assertEquals(slider.getReal(0), -8, 0.1);
        Assert.assertEquals(slider.getImaginary(0), 0, 0.1);
        Assert.assertEquals(slider.getPhase(0) * 360 / (2 * Math.PI), 180, 0.1);
    }

    @Test
    public void test23() {
        DFTSlider slider = getSliderWithComponents(3, comp2, comp3);
        verifyAmplitude(slider, 3, 0, 1, 1, 0);
    }

    @Test
    public void testJustNegDc() {
        DFTSlider slider = getSliderWithComponents(-8);
        verifyAmplitude(slider, 8, 0, 0, 0, 0);
        Assert.assertEquals(slider.getReal(0), -8, 0.1);
        Assert.assertEquals(slider.getPhase(0) * 360 / (2 * Math.PI), 180, 0.1);
    }

    @Test
    public void test2Phased() {
        DFTSlider slider = getSliderWithComponents(4, comp1PhasedPlus90, comp2PhasedMinus90);
        verifyAmplitude(slider, 4, 1, 1, 0, 0);
    }

    @Test
    public void testLatency() {
        DFTSlider slider = getSliderWithComponents(4, comp1PhasedPlus90, comp2PhasedMinus90);
        Assert.assertEquals(8, slider.getLatencyInSamples());
    }

    @Test
    public void testNoOfFrequencies() {
        DFTSlider slider = getSliderWithComponents(4, comp1PhasedPlus90, comp2PhasedMinus90);
        Assert.assertEquals(5, slider.getNoOfFrequencies());
    }

    private DFTSlider getSliderWithComponents(double dc, double[]... components) {
        DFTSlider slider = new DFTSliderImpl(4);
        double[] tot = addRealComponents(components);
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
            Assert.assertEquals("Component no: " + i, values[i], slider.getAmplitude(i), 0.1);
        }
    }

    private double round(double value) {
        return (((double)((int)(value * 100d + ((value > 0)?0.5d : -0.5d)))) / 100d);
    }

    private double getPhaseInDegrees(DFTSliderCompactImpl slider, int i, double amp) {
        double degrees = (amp == 0)?0 : (((slider.getPhase(i) * 360) / (2d * Math.PI)) % 360);
        return (degrees > 180)?degrees - 360 : degrees;
    }

    private void println(DFTSliderCompactImpl slider) {
        for(int i = 0; i < slider.getNoOfFrequencies(); i++) {
            double amp = round(slider.getAmplitude(i));
            double phase = round(getPhaseInDegrees(slider, i, amp));
            System.out.println("[" + amp + ", " + phase + "]");
        }

        System.out.println("---------");
    }

    abstract DFTSlider getSliderImpl(int noOfFrequencies);
}
