package se.imagick.ft.slidingdft;

/**
 * A Java implementation of the DFT-Slider. It is used to re-calculate a FTT
 * for each sample added. The data is added one by one. All frequences have
 * zero amplitude and zero phase before sliding samples into the sliders.
 * The filter latancy in samples (and number of samples used for the calculation)
 * can be calculated by:
 * <p/>
 * [the number of frequency components] * 2.
 * <p/>
 * The DC component (frequency 0) is added automatically.
 * To use with multi channel samples, see DFTSliderFilter.
 * <p/>
 * ---------------------
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Olav Holten
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class DFTSlider{
    private final DFTSliderFrequency[] sliderFrequencies;
    private final double noofSamples;
    private double lastOutValue;

    public DFTSlider(int noofFrequencies){
        this.sliderFrequencies = new DFTSliderFrequency[noofFrequencies + 1]; // The +1 is dc
        this.noofSamples = noofFrequencies * 2d;

        for(int i = 0; i < noofFrequencies + 1; i++){
            sliderFrequencies[i] = new DFTSliderFrequency(noofFrequencies, i);
        }
    }

    public double slide(double value){
        double change = (value - lastOutValue) / noofSamples;
        double outValue = 0d;

        for(DFTSliderFrequency freq : sliderFrequencies){
            freq.slide(change);
            outValue += freq.getReal();
        }

        lastOutValue = outValue;
        return outValue;
    }

    public int getNoofFrequencies(){
        return sliderFrequencies.length;
    }

    public int getLatancyInSamples(){
        return (int)noofSamples;
    }

    public double getRealSum(){
        double outValue = 0d;

        for(DFTSliderFrequency freq : sliderFrequencies){
            outValue += freq.getReal();
        }

        return outValue;
    }

    public double getAmplitude(int componentNo){
        return sliderFrequencies[componentNo].getAmplitude();
    }

    public double getPhase(int componentNo){
        return sliderFrequencies[componentNo].getPhase();
    }

    public double getReal(int componentNo){
        return sliderFrequencies[componentNo].getReal();
    }

    public double getImaginary(int componentNo){
        return sliderFrequencies[componentNo].getImaginary();
    }
}
