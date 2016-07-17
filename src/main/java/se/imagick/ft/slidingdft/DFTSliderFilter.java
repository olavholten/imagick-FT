package se.imagick.ft.slidingdft;

/**
 * Entry point for calculating multi channel sliding DFT.
 * For details about single channel Sliding DFT see DFTSliderImpl.<br>
 * <br>
 * ---------------------<br>
 * The MIT License (MIT)<br>
 * <br>
 * Copyright (c) 2015 Olav Holten<br>
 * <br>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:<br>
 * <br>
 * The above copyright notice and this permission notice shall be included in<br>
 * all copies or substantial portions of the Software.<br>
 * <br>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class DFTSliderFilter{

    private final DFTSlider[] channelSliders;

    private final double noofChannels;
    /**
     * @param noofFrequencies How many frequency components to use. One extra will added automatically for the
     *                        dc-component.
     * @param noofChannels The number of channels (2 for a stereo signal).
     */
    public DFTSliderFilter(int noofFrequencies, int noofChannels){
        this.noofChannels = (double)noofChannels;
        this.channelSliders = new DFTSlider[noofChannels];

        for(int i = 0; i < channelSliders.length; i++){
            channelSliders[i] = new DFTSliderImpl(noofFrequencies);
        }
    }

    /**
     * Slide in one sample.
     * @param sample One sample. One array element per channel, EG. sample[0] = left, sample[1] = right).
     */
    public void slide(double[] sample){
        if(sample != null){
            for(int channel = 0; channel < noofChannels; channel++){
                sample[channel] = channelSliders[channel].slide(sample[channel]);
            }
        }
    }

    /**
     * Retrieves the sliders (one for each channel).
     * @return The sliders for all channels.
     */
    public DFTSlider[] getChannelSliders() {
        return channelSliders;
    }

    /**
     * Retrieves the real sum for all frequencies for sample 0 in the buffer (one array element per channel).
     * @return The real sum for all frequencies per channel for sample 0 (EG sample[0] = left, sample[1] = right).
     */
    public double[] getRealSum() {
        double[] sample = new double[channelSliders.length];

        for(int channel = 0; channel < channelSliders.length; channel++) {
            sample[channel] = channelSliders[channel].getRealSum(false);
        }

        return sample;
    }

    public double getAmplitude(int channelNo, int componentNo){
        return channelSliders[channelNo].getPolar(componentNo).getMagnitude();
    }

    public double getPhase(int channelNo, int componentNo){

        return channelSliders[channelNo].getPolar(componentNo).getPhase();
    }

    public double getImaginary(int channelNo, int componentNo) {
        return channelSliders[channelNo].getComplex(componentNo).getImaginary();
    }

    public double getReal(int channelNo, int componentNo) {
        return channelSliders[channelNo].getComplex(componentNo).getReal();
    }
}
