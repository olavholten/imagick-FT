package se.imagick.ft.slidingfft;

/**
 * Entrypoint for calculating multi channel sliding FFT.
 * For details about single channel Sliding FFT see FFTSlider.
 *
 * ---------------------
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olav Holten
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class FFTSliderFilter{

    private final FFTSlider[] channelSliders;
    private final double noofChannels;

    /**
     * @param noofFrequencies How many frequencies the samples should
     * @param noofChannels The number of channels (2 for a stereo signal).
     */
    public FFTSliderFilter(int noofFrequencies, int noofChannels){
        this.noofChannels = (double)noofChannels;
        this.channelSliders = new FFTSlider[(int)noofChannels];

        for(int i = 0; i < channelSliders.length; i++){
            channelSliders[i] = new FFTSlider(noofFrequencies);
        }
    }

    public void slide(double[] sample){
        if(sample != null){
            for(int channel = 0; channel < noofChannels; channel++){
                sample[channel] = channelSliders[channel].slide(sample[channel]);
            }
        }
    }
}
