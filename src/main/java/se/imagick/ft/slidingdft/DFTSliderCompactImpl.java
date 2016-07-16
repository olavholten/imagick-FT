package se.imagick.ft.slidingdft;

/**
 * This is a compact version of the slider, using only one class.
 * Slightly, slightly faster but harder to understand.
 * Only positive frequencies are calculated.<br>
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
public class DFTSliderCompactImpl implements DFTSlider{

    private final double[] real;
    private final double[] imag;
    private final double[] magni;
    private final double[] phase;
    private final double[] multi;
    private final double[] turn;
    private double realSum;
    private final double noofSamples;
    private final double noofComplex;

    public DFTSliderCompactImpl(int noofFrequencies){

        double turnBase = Math.PI * 2d / noofFrequencies;
        this.realSum = 0d;
        this.noofSamples = noofFrequencies * 2d;
        this.noofComplex = noofFrequencies + 1d; // +1 = dc, see DFT-principles.
        this.real = new double[(int)this.noofComplex];
        this.imag = new double[(int)this.noofComplex];
        this.magni = new double[(int)this.noofComplex];
        this.phase = new double[(int)this.noofComplex];
        this.multi = new double[(int)this.noofComplex];
        this.turn = new double[(int)this.noofComplex];

        for(int i = 0; i < this.noofComplex; i++){
            turn[i] = i * turnBase / 2d;
            multi[i] = (i == 0 || i == noofComplex - 1)?1:2; // See DFT-principles for first and last frequency.
        }
    }

    public double slide(double inValue){
        double newVal = (inValue - this.realSum) / this.noofSamples;
        this.realSum = 0d;

        for(int i = 0; i < noofComplex; i++){
            real[i] += newVal * multi[i];
            double realVal = real[i];
            double imagVal = imag[i];
            double mag = Math.sqrt(realVal * realVal + imagVal * imagVal);
            double phs = Math.atan2(imagVal, realVal) + turn[i];
            magni[i] = mag;
            phase[i] = phs;
            real[i] = Math.cos(phs) * mag;
            imag[i] = Math.sin(phs) * mag;
            this.realSum += real[i];
        }

        return realSum;
    }

    @Override
    public int getNoOfFrequencies() {
        return (int)this.noofComplex;
    }

    @Override
    public int getLatencyInSamples() {
        return (int)this.noofSamples;
    }

    @Override
    public double getAmplitude(int componentNo){
        return  magni[componentNo];
    }

    @Override
    public double getPhase(int componentNo){
        return phase[componentNo];
    }

    @Override
    public double getReal(int componentNo){
        return real[componentNo];
    }

    @Override
    public double getImaginary(int componentNo){
        return imag[componentNo];
    }

    @Override
    public double getRealSum() {
        return realSum;
    }
}
