package se.imagick.ft.fft;

import java.util.ArrayList;
import java.util.List;

/**
 * FFT-DIF (decimation in frequency).
 * Can and should be reused since there is a overhead for setting up decomp and sine/cosine values.
 * The implementation is thread safe.<br>
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
public class FftDif {

    private double size;
    private double[] cos;
    private double[] sin;
    private int[] decompArray;

    public FftDif(int size) {

        if (!((size & -size) == size)) {
            throw new IllegalArgumentException("Not a power of two value");
        }

        this.size = size;
        int halfSize = size / 2;

        // Pre-calculate all possible sine and cosine values.
        cos = new double[halfSize];
        sin = new double[halfSize];

        for (int i = 0; i < halfSize; i++) {
            cos[i] = Math.cos(2 * Math.PI * i / size);
            sin[i] = Math.sin(2 * Math.PI * i / size);
        }

        // Pre calculate for the reverse decomp of values.
        decompArray = preDecomp((int) this.size);
    }

    /**
     * Calculates the complex values.
     *
     * @param realValuesIn An array with purely real values (Eg audio data).
     * @return A list with two arrays. The first is the real values, the second the imaginary values.
     * The number of indexes can be computed as follows: noOfFrequencies / 2 + 1 (dc).
     * The frequencies are in frequency order starting with zero.
     */
    public double[][] forward(double[] realValuesIn) {

        if (realValuesIn.length != this.size) {
            throw new IllegalArgumentException("Wrong array length!");
        }

        double[] realValues = realValuesIn.clone();
        double[] imagValues = new double[realValues.length];


        double[][] complexArrays = fftInternal(realValues, imagValues);

        realValues = complexArrays[0];
        imagValues = complexArrays[1];

        int dftSize = (int) (size / 2 + 1);
        double[] re = new double[dftSize];
        double[] im = new double[dftSize];

        for (int i = 0; i < dftSize; i++) {
            re[i] = realValues[decompArray[i]] / this.size;
            im[i] = -imagValues[decompArray[i]] / this.size;
        }

        // Now process the negative frequencies.
        for (int i = 1; i < dftSize - 1; i++) {
            re[i] += realValues[decompArray[(int) (size - i)]] / this.size;
            im[i] += imagValues[decompArray[(int) (size - i)]] / this.size;
        }

        complexArrays[0] = re;
        complexArrays[1] = im;

        return complexArrays;
    }

    public double[] inverse(double[] realValuesIn, double[] imagValuesIn) {

        if (2 * (realValuesIn.length - 1) != this.size) {
            throw new IllegalArgumentException("Wrong array length!");
        }

        double[] realValues = new double[(int) size];
        double[] imagValues = new double[(int) size];

        System.arraycopy(realValuesIn, 0, realValues, 0, realValuesIn.length);
        System.arraycopy(imagValuesIn, 0, imagValues, 0, imagValuesIn.length);

        double[] realInverse = fftInternal(realValues, imagValues)[0];

        double[] re = new double[(int) size];

        for (int i = 0; i < size; i++) {
            re[i] = realInverse[decompArray[i]];
        }

        return re;
    }

    private double[][] fftInternal(double[] realValues, double[] imagValues) {

        int sinStep = 1;

        for (int butterflySize = (int) size; butterflySize > 1; butterflySize /= 2) {
            int butterflyHalfsize = butterflySize / 2;

            for (int butterflyIndexStart = 0; butterflyIndexStart < size; butterflyIndexStart += butterflySize) {

                for (int butterflyIndex = 0; butterflyIndex < butterflyHalfsize; butterflyIndex++) {

                    // Setup for this butterfly
                    int currIndex = butterflyIndexStart + butterflyIndex;
                    int currDoubleIndex = currIndex + butterflyHalfsize;

                    double realTemp = realValues[currIndex];
                    double imagTemp = imagValues[currIndex];

                    // Additions and subtractions, dividing odd and even frequencies and down sampling.
                    realValues[currIndex] += realValues[currDoubleIndex];
                    realValues[currDoubleIndex] = realTemp - realValues[currDoubleIndex];
                    imagValues[currIndex] += imagValues[currDoubleIndex];
                    imagValues[currDoubleIndex] = imagTemp - imagValues[currDoubleIndex];

                    // Multiply with a sine in form of several complex values.
                    realTemp = realValues[currDoubleIndex];
                    imagTemp = imagValues[currDoubleIndex];
                    double currCos = cos[butterflyIndex * sinStep];
                    double currSin = sin[butterflyIndex * sinStep];

                    // Butterfly complex multiplication with half a sine curve.
                    realValues[currDoubleIndex] = realTemp * currCos - imagTemp * currSin;
                    imagValues[currDoubleIndex] = realTemp * currSin + imagTemp * currCos;
                }
            }

            sinStep *= 2;
        }

        double[][] complexValues = new double[2][];
        complexValues[0] = realValues;
        complexValues[1] = imagValues;

        return complexValues;
    }

    /**
     * Calculates the order which the complex values will have after the fft calculation
     * to be able to re-order them to frequency order.
     *
     * It works as follows:
     * ABCD, Cut the sequence (ABCD) in two and place the right part in the bottom of the heap (CD).
     *
     * AB, Do it once more and put the right part in the bottom (BD)
     * CD
     *
     * A You now end up with a new sequence
     * C
     * B
     * D
     *
     * This is also basically what happens when calculating the FFT-DIF.
     */
    private int[] preDecomp(int noOfSamples) {
        List<Integer> input = new ArrayList<>();
        List<List<Integer>> wrapper = new ArrayList<>();
        wrapper.add(input);

        for (int i = 0; i < noOfSamples; i++) {
            input.add(i);
        }

        while (wrapper.get(0).size() > 1) {
            devideForDecomp(wrapper);
        }

        int[] decompArray = new int[(int) this.size];

        for (int i = 0; i < this.size; i++) {
            decompArray[i] = wrapper.get(i).get(0);
        }

        return decompArray;
    }

    private static void devideForDecomp(List<List<Integer>> inputLists) {
        int endIndex = inputLists.get(0).size();
        int middleIndex = endIndex / 2;

        List<List<Integer>> listsTop = new ArrayList<>();
        List<List<Integer>> listsBottom = new ArrayList<>();

        for (List<Integer> values : inputLists) {
            listsTop.add(values.subList(0, middleIndex));
            listsBottom.add(values.subList(middleIndex, endIndex));
        }

        inputLists.clear();
        inputLists.addAll(listsTop);
        inputLists.addAll(listsBottom);
    }
}
