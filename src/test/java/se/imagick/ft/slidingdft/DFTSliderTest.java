package se.imagick.ft.slidingdft;

import org.junit.Assert;
import org.junit.Test;

public class DFTSliderTest{

    private double[] comp1 = new double[]{1, 0.7071d, 0, -0.7071, -1, -0.7071, 0, 0.7071};
    private double[] comp2 = new double[]{1, 0, -1, 0, 1, 0, -1, 0};
    private double[] comp3 = new double[]{1.0, -0.7071, 0.0, 0.7071, -1.0, 0.7071, 0.0, -0.7071};
    private double[] comp4 = new double[]{1, -1, 1, -1, 1, -1, 1, -1};

    @Test
    public void testComp1(){
        DFTSlider slider = getSlderWithComponents(2, comp1);
        println(slider);
        verifyAmplitude(slider, 2, 1, 0, 0, 0);
    }

    @Test
    public void test124Comp(){
        DFTSlider slider = getSlderWithComponents(5, comp1, comp2, comp4);
        println(slider);
        verifyAmplitude(slider, 5, 1, 1, 0, 1);
    }

    @Test
    public void test1234Comp(){
        DFTSlider slider = getSlderWithComponents(8, comp1, comp2, comp3, comp4);
        println(slider);
        verifyAmplitude(slider, 8, 1, 1, 1, 1);
    }

    @Test
    public void test1234CompNegDc(){
        DFTSlider slider = getSlderWithComponents(-8, comp1, comp2, comp3, comp4);
        println(slider);
        verifyAmplitude(slider, -8, 1, 1, 1, 1);
    }

    @Test
    public void testJustNegDc(){
        DFTSlider slider = getSlderWithComponents(-8);
        println(slider);
        verifyAmplitude(slider, -8, 0, 0, 0, 0);
    }

    private DFTSlider getSlderWithComponents(double dc, double[]... componens){
        DFTSlider slider = new DFTSlider(4);
        double[] tot = new double[comp1.length];

        for(double[] comp : componens){
            addComponents(tot, comp);
        }

        slideAll(slider, tot, dc);

        return slider;
    }

    private void addComponents(double[] tot, double[] addition){
        for(int i = 0; i < tot.length; i++){
            tot[i] += addition[i];
        }
    }

    private void slideAll(DFTSlider slider, double[] tot, double dc){
        for(double value:tot){
            slider.slide(value + dc);
        }
    }

    private void println(DFTSlider slider){
        for(int i = 0; i < slider.getNoofFrequencies(); i++){
            double amp = round(slider.getAmplitude(i));
            double phase = round((slider.getPhase(i)* 360 / (2d * Math.PI)) % 360);
            System.out.println("[" + amp + ", " + phase + "]");
        }

        System.out.println("---------");
    }

    private double round(double value) {
        return (((double)((int) (value * 100d + 0.5d)))) / 100d;
    }

    private void verifyAmplitude(DFTSlider slider, double... values){
        for(int i = 0; i < values.length; i++) {
            Assert.assertEquals("Component no: " + i, values[i], slider.getAmplitude(i), 0.1);
        }
    }
}
