package se.imagick.ft.common;

/**
 * Java implementation of a Complex value.<br>
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
public class Complex {
    private double real;
    private double imaginary;

    public Complex() {
    }

    public Complex(Complex complex) {
        this(complex.getReal(), complex.getImaginary());
    }

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public void setReal(final double real) {
        this.real = real;
    }

    public void addChangeToReal(final double change) {
        this.real += change;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void setImaginary(final double imaginary) {
        this.imaginary = imaginary;
    }

    public boolean equals(Complex that) {
        return (this.real == that.real) && (this.imaginary == that.imaginary);
    }

    public void copyTo(Complex complex) {
        complex.setReal(real);
        complex.setImaginary(imaginary);
    }

    public void copyFrom(Complex complex) {
        real = complex.getReal();
        imaginary = complex.getImaginary();
    }
}
