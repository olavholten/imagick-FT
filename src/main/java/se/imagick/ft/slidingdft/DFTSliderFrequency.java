package se.imagick.ft.slidingdft;

import se.imagick.ft.common.Complex;
import se.imagick.ft.common.FTUtils;
import se.imagick.ft.common.Polar;

/**
 * One single frequency. Frequency zero is the DC-component (average of all samples).<br>
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
public class DFTSliderFrequency{
    private final double turnDegrees;
    private final Complex complex;
    private final Polar polar;
    private final double multiplier;

    public DFTSliderFrequency(final double noofFreq, final double freqNo){
        this.turnDegrees = (Math.PI / noofFreq) * freqNo;
        this.complex = new Complex();
        this.polar = new Polar();
        this.multiplier = (freqNo == 0d || freqNo == noofFreq)?1d : 2d; // See DFT principles (First and last component).
    }

    public void slide(double in, double out){
        slide(in - out);
    }

    public void slide(double change){
        complex.addChangeToReal(change * multiplier);
        FTUtils.complex2Polar(complex, polar);
        polar.addPhase(turnDegrees);
        FTUtils.polar2Complex(polar, complex);
    }

    public double getAmplitude(){
        return polar.getMagnitude();
    }

    public double getPhase(){
        return polar.getPhase();
    }

    public double getReal(){
        return complex.getReal();
    }

    public double getImaginary(){
        return complex.getImaginary();
    }
}
