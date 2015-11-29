package se.imagick.ft.common;

/**
 * Utility methods for Complex-Polar conversions.
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
public class FTUtils{
    public static void complex2Polar(final Complex complex, Polar polar){
        final double real = complex.getReal();
        final double imag = complex.getImaginary();
        polar.setMagnitude(Math.sqrt(real * real + imag * imag));
        polar.setPhase(Math.atan2(imag, real));
    }

    public static void polar2Complex(final Polar polar, final Complex complex){
        final double mag = polar.getMagnitude();
        final double phase = polar.getPhase();
        complex.setReal(Math.cos(phase) * mag);
        complex.setImaginary(Math.sin(phase) * mag);
    }
}
