package se.imagick.ft.slidingdft;

import org.junit.Assert;
import org.junit.Test;

public class DFTSliderFilterTest {

    @Test
    public void getSliderWithComponents(){

        DFTSliderFilter sliderFilter = new DFTSliderFilter(4, 2);
        double[] sample = new double[]{1,2};
        double[] emptySample = new double[2];

        sliderFilter.slide(sample);
        sliderFilter.slide(emptySample);
        sliderFilter.slide(emptySample);
        sliderFilter.slide(emptySample);
        sliderFilter.slide(emptySample);
        sliderFilter.slide(emptySample);
        sliderFilter.slide(emptySample);

        double[] outSample;
        outSample = sliderFilter.getRealSum();
        Assert.assertEquals(0, outSample[0], 0.001d);
        Assert.assertEquals(0, outSample[1], 0.001d);
        assertChannelSliders(sliderFilter);

        sliderFilter.slide(emptySample);
        outSample = sliderFilter.getRealSum();
        Assert.assertEquals(1, outSample[0], 0.001d);
        Assert.assertEquals(2, outSample[1], 0.001d);
        assertChannelSliders(sliderFilter);

        sliderFilter.slide(emptySample);
        outSample = sliderFilter.getRealSum();
        Assert.assertEquals(0, outSample[0], 0.001d);
        Assert.assertEquals(0, outSample[1], 0.001d);
        assertChannelSliders(sliderFilter);
    }

    private void assertChannelSliders(DFTSliderFilter filter){

        DFTSlider[] sliders = filter.getChannelSliders();

        for(int channelNo = 0; channelNo < sliders.length; channelNo++) {
            DFTSlider slider = sliders[channelNo];

            for(int compNo = 0; compNo < slider.getNoOfFrequencies(); compNo++) {
                Assert.assertEquals(filter.getAmplitude(channelNo, compNo), slider.getAmplitude(compNo), 0.0001);
                Assert.assertEquals(filter.getPhase(channelNo, compNo), slider.getPhase(compNo), 0.0001);
                Assert.assertEquals(filter.getReal(channelNo, compNo), slider.getReal(compNo), 0.0001);
                Assert.assertEquals(filter.getImaginary(channelNo, compNo), slider.getImaginary(compNo), 0.0001);
            }
        }
    }
}
