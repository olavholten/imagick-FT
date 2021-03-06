package se.imagick.ft.slidingdft;

import se.imagick.ft.common.Complex;
import se.imagick.ft.common.Polar;

/**
 * A Java implementation of the DFT-Slider. It is used to re-calculate a FT
 * for a moving window, one sample at a time. This is conciderably more efficient
 * than re-calculating the current window with FFT. The data is added one by one.
 * All frequences have zero amplitude and zero phase before sliding samples into the
 * sliders. This however introduces a latancy. The filter latancy in samples
 * (and number of samples used for the calculation) can be calculated by:<br>
 * <br>
 * [the number of frequency components] * 2.<br>
 * <br>
 * The DC component (frequency 0) is added automatically.
 * To use with multi channel samples, see DFTSliderFilter, which wraps the needed number
 * of DFTSliders (EG 2 for a stereo sound signal).<br>
 * <br>
 * NB! Only positive frequencies are calculated.<br>
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
public class DFTSliderImpl implements DFTSlider {
    private final DFTSliderFrequency[] sliderFrequencies;
    private final double noofSamples;
    private double realSum;

    /**
     * Creates an instance of DFTSliderImpl that will use recycled Complex and Polar instances.
     * @param noofFrequencies The number of frequencies used. Frequency zero (dc) will be added.
     */
    public DFTSliderImpl(int noofFrequencies){
        this(noofFrequencies, true);
    }


    public DFTSliderImpl(int noofFrequencies, boolean isReusing){
        this.sliderFrequencies = new DFTSliderFrequency[noofFrequencies + 1]; // The +1 is dc
        this.noofSamples = noofFrequencies * 2d;

        for(int i = 0; i < noofFrequencies + 1; i++){
            sliderFrequencies[i] = new DFTSliderFrequency(noofFrequencies, i, isReusing);
        }
    }

    @Override
    public double slide(double value){
        double change = (value - realSum) / noofSamples;
        double realSum = 0d;

        for(DFTSliderFrequency freq : sliderFrequencies){
            freq.slide(change);
            realSum += freq.getComplex().getReal();
        }

        this.realSum = realSum;
        return realSum;
    }

    @Override
    public int getNoOfFrequencies(){
        return sliderFrequencies.length;
    }

    @Override
    public int getLatencyInSamples(){
        return (int)noofSamples;
    }

    @Override
    public double getRealSum(boolean willRecalculate){

        if(willRecalculate) {
            realSum = 0d;
            for(DFTSliderFrequency freq : sliderFrequencies){
                realSum += freq.getComplex().getReal();
            }
        }

        return realSum;
    }

    @Override
    public Complex getComplex(int componentNo) {
        return sliderFrequencies[componentNo].getComplex();
    }

    @Override
    public void setComplex(int componentNo, Complex complex) {
        sliderFrequencies[componentNo].setComplex(complex);
    }

    @Override
    public Polar getPolar(int componentNo) {
        return sliderFrequencies[componentNo].getPolar();
    }

    @Override
    public void setPolar(int componentNo, Polar polar) {
        sliderFrequencies[componentNo].setPolar(polar);
    }
}
