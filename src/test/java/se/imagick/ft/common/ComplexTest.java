package se.imagick.ft.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test of th FTUtils.
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
public class ComplexTest {

    @Test
    public void defaulConstructor(){
        Complex c1 = new Complex();
        Assert.assertEquals(0d, c1.getReal(), 0.01);
        Assert.assertEquals(0d, c1.getImaginary(), 0.01);
    }

    @Test
    public void constructorWithValues(){
        double real = 1d;
        double imag = 2d;
        Complex c1 = new Complex(real, imag);
        Assert.assertEquals(real, c1.getReal(), 0.01);
        Assert.assertEquals(imag, c1.getImaginary(), 0.01);
    }

    @Test
    public void constructorWithOtherInstance(){
        double real = 1d;
        double imag = 2d;
        Complex c1 = new Complex(real, imag);
        Complex c2 = new Complex(c1);
        Assert.assertEquals(real, c2.getReal(), 0.01);
        Assert.assertEquals(imag, c2.getImaginary(), 0.01);
    }

    @Test
    public void setters(){
        double real = 1d;
        double imag = 2d;
        Complex c1 = new Complex();
        c1.setReal(real);
        c1.setImaginary(imag);
        Assert.assertEquals(real, c1.getReal(), 0.01);
        Assert.assertEquals(imag, c1.getImaginary(), 0.01);
    }

    @Test
    public void copyFrom(){
        double real = 1d;
        double imag = 2d;
        Complex c1 = new Complex(real, imag);
        Complex c2 = new Complex();
        Assert.assertEquals(real, c1.getReal(), 0.01);
        Assert.assertEquals(imag, c1.getImaginary(), 0.01);
        Assert.assertEquals(0, c2.getReal(), 0.01);
        Assert.assertEquals(0, c2.getImaginary(), 0.01);
        c2.copyFrom(c1);
        Assert.assertEquals(real, c1.getReal(), 0.01);
        Assert.assertEquals(imag, c1.getImaginary(), 0.01);
        Assert.assertEquals(real, c2.getReal(), 0.01);
        Assert.assertEquals(imag, c2.getImaginary(), 0.01);
    }

    @Test
    public void copyTo(){
        double real = 1d;
        double imag = 2d;
        Complex c1 = new Complex(real, imag);
        Complex c2 = new Complex();
        Assert.assertEquals(real, c1.getReal(), 0.01);
        Assert.assertEquals(imag, c1.getImaginary(), 0.01);
        Assert.assertEquals(0, c2.getReal(), 0.01);
        Assert.assertEquals(0, c2.getImaginary(), 0.01);
        c1.copyTo(c2);
        Assert.assertEquals(real, c1.getReal(), 0.01);
        Assert.assertEquals(imag, c1.getImaginary(), 0.01);
        Assert.assertEquals(real, c2.getReal(), 0.01);
        Assert.assertEquals(imag, c2.getImaginary(), 0.01);
    }
}
