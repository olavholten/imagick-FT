package se.imagick.ft.slidingdft;

import org.junit.Assert;
import org.junit.Test;

public class DFTSliderCompactTest{

    private double[] comp1 = new double[]{1, 0.7071d, 0, -0.7071, -1, -0.7071, 0, 0.7071};
    private double[] comp1PhasedPlus90 = new double[]{0, -0.7071, -1, -0.7071, 0, 0.7071, 1, 0.7071d};
    private double[] comp2 = new double[]{1, 0, -1, 0, 1, 0, -1, 0};
    private double[] comp2PhasedMinus90 = new double[]{0, 1, 0, -1, 0, 1, 0, -1};
    private double[] comp3 = new double[]{1.0, -0.7071, 0.0, 0.7071, -1.0, 0.7071, 0.0, -0.7071};
    private double[] comp4 = new double[]{1, -1, 1, -1, 1, -1, 1, -1};

    @Test
    public void testComp1(){
        DFTSliderCompact slider = getSliderWithComponents(2, comp1);
        println(slider);
        verifyAmplitude(slider, 2, 1, 0, 0, 0);
    }

//    @Test
    public void testCapacity(){
        DFTSliderCompact slider = getSliderWithComponents(2, comp1);
        long start = System.currentTimeMillis();
        double[] tot = addRealComponents(comp1, comp2, comp4);
        for(int i = 0; i < 44100 * 10 * 2;i++) {
            slideAll(slider, tot, 3d);
        }

        long stop = System.currentTimeMillis();

        System.out.println("************* Time: " + ((stop - start) / 1000d));
    }

    @Test
    public void test124Comp(){
        DFTSliderCompact slider = getSliderWithComponents(5, comp1, comp2, comp4);
        println(slider);
        verifyAmplitude(slider, 5, 1, 1, 0, 1);
    }

    @Test
    public void test1234Comp(){
        DFTSliderCompact slider = getSliderWithComponents(8, comp1, comp2, comp3, comp4);
        println(slider);
        verifyAmplitude(slider, 8, 1, 1, 1, 1);
    }

    @Test
    public void test1234CompNegDc(){
        DFTSliderCompact slider = getSliderWithComponents(-8, comp1, comp2, comp3, comp4);
        println(slider);
        verifyAmplitude(slider, 8, 1, 1, 1, 1);
        Assert.assertEquals(slider.getReal(0), -8, 0.1);
        Assert.assertEquals(slider.getImaginary(0), 0, 0.1);
        Assert.assertEquals(slider.getPhase(0) * 360 / (2 * Math.PI), 180, 0.1);
    }

    @Test
    public void test23(){
        DFTSliderCompact slider = getSliderWithComponents(3, comp2, comp3);
        println(slider);
        verifyAmplitude(slider, 3, 0, 1, 1, 0);
    }

    @Test
    public void testJustNegDc(){
        DFTSliderCompact slider = getSliderWithComponents(-8);
        println(slider);
        verifyAmplitude(slider, 8, 0, 0, 0, 0);
        Assert.assertEquals(slider.getReal(0), -8, 0.1);
        Assert.assertEquals(slider.getPhase(0) * 360 / (2 * Math.PI), 180, 0.1);
    }

    @Test
    public void test2Phased(){
        DFTSliderCompact slider = getSliderWithComponents(4, comp1PhasedPlus90, comp2PhasedMinus90);
        println(slider);
        verifyAmplitude(slider, 4, 1, 1, 0, 0);
    }

    private DFTSliderCompact getSliderWithComponents(double dc, double[]... components){
        DFTSliderCompact slider = new DFTSliderCompact(4);
        double[] tot = addRealComponents(components);
        slideAll(slider, tot, dc);

        return slider;
    }

    private double[] addRealComponents(double[]... components){
        double[] tot = new double[comp1.length];
        for(double[] comp : components){
            addComponents(tot, comp);
        }

        return tot;
    }

    private void addComponents(double[] tot, double[] addition){
        for(int i = 0; i < tot.length; i++){
            tot[i] += addition[i];
        }
    }

    private void slideAll(DFTSliderCompact slider, double[] tot, double dc){
        for(double value:tot){
            slider.slide(value + dc);
        }
    }

    private void println(DFTSliderCompact slider){
        for(int i = 0; i < slider.getNoofComplex(); i++){
            double amp = round(slider.getAmplitude(i));
            double phase = round(getPhaseInDegrees(slider, i, amp));
            System.out.println("[" + amp + ", " + phase + "]");
        }

        System.out.println("---------");
    }

    private double getPhaseInDegrees(DFTSliderCompact slider, int i, double amp){
        double degrees = (amp == 0)?0 : (((slider.getPhase(i) * 360) / (2d * Math.PI)) % 360);
        return (degrees > 180)?degrees -360 : degrees;
    }

    private double round(double value) {
        return (((double)((int) (value * 100d + ((value > 0)?0.5d:-0.5d)))) / 100d);
    }

    private void verifyAmplitude(DFTSliderCompact slider, double... values){
        for(int i = 0; i < values.length; i++) {
            Assert.assertEquals("Component no: " + i, values[i], slider.getAmplitude(i), 0.1);
        }
    }
}
