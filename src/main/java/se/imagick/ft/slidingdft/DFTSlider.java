package se.imagick.ft.slidingdft;

import se.imagick.ft.common.Complex;
import se.imagick.ft.common.Polar;

/**
 * Interface for a Java implementation of the DFT-Slider. It is used to re-calculate
 * a FT for a moving window, one sample at a time. This is considerably more efficient
 * than re-calculating the current window with FFT. The data is added one by one.
 * All frequencies have zero amplitude and zero phase before sliding samples into the
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
public interface DFTSlider {

    /**
     * Slides a sample in last in the buffer and removed the first.
     *
     * @param value The value that is to be slid in to the buffer in the last position.
     * @return The current first sample value (not the sample value being pushed out).
     * This will be the same value as will be returned getRealSum();
     */
    double slide(double value);

    /**
     * Retrieves the number of frequency components (including the added dc component).
     * @return The number of frequencies that the the slider got initiated with plus one
     * (the 0 frequency, DC-component).
     */
    int getNoOfFrequencies();

    /**
     * Retrieves the number of samples in the buffer (getNoOfFrequencies() - 1) * 2)
     * @return The latency in samples.
     */
    int getLatencyInSamples();

    /**
     * Retrieves the sum of the real part of all the frequency components
     * (equals the current first sample in the buffer, about to be pushed out in the next slide).
     * If the frequencies have been altered since last slide, a recalculation is needed.
     * @param willRecalculate If set to true, a recalculation of all real parts is executed
     *                        (use only if any frequency components have been altered since the last slide).
     * @return The sum of the real parts of all the frequency components.
     */
    double getRealSum(boolean willRecalculate);

    /**
     * Retrieves the complex values for the specified frequency component (copy by value).
     * @return The Complex values for the specified frequency component.
     * !NB To decrease the need for garbage collection,
     * the instance returned might be statically used for the frequency component.
     * @param componentNo The frequency component.
     */
    Complex getComplex(int componentNo);

    /**
     * Copies the Complex values to the specified component.
     * All values (complex and polar) will be updated.
     * @param componentNo The frequency component.
     * @param complex The values to be copied to the frequency component.
     */
    void setComplex(int componentNo, Complex complex);

    /**
     * Retrieves the polar values for the specified frequency component (copy by value).
     * @return The polar values for the specified frequency component.
     * !NB To decrease the need for garbage collection,
     * the instance returned might be statically used for the frequency component.
     * @param componentNo The frequency component.
     */
    Polar getPolar(int componentNo);


    /**
     * Sets (copy by value) the Polar values to the specified component.
     * !NB To decrease the need for garbage collection,
     * the instance returned might be statically used for the frequency component.
     */
    void setPolar(int componentNo, Polar polar);
}
