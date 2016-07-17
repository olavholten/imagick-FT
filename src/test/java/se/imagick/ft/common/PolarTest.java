package se.imagick.ft.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test of the FTUtils.
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
public class PolarTest {

    @Test
    public void defaulConstructor(){
        Polar c1 = new Polar();
        Assert.assertEquals(0d, c1.getMagnitude(), 0.01);
        Assert.assertEquals(0d, c1.getPhase(), 0.01);
    }

    @Test
    public void constructorWithValues(){
        double magni = 1d;
        double phase = 2d;
        Polar c1 = new Polar(magni, phase);
        Assert.assertEquals(magni, c1.getMagnitude(), 0.01);
        Assert.assertEquals(phase, c1.getPhase(), 0.01);
    }

    @Test
    public void constructorWithOtherInstance(){
        double magni = 1d;
        double phase = 2d;
        Polar c1 = new Polar(magni, phase);
        Polar c2 = new Polar(c1);
        Assert.assertEquals(magni, c2.getMagnitude(), 0.01);
        Assert.assertEquals(phase, c2.getPhase(), 0.01);
    }

    @Test
    public void setters(){
        double magni = 1d;
        double phase = 2d;
        Polar c1 = new Polar();
        c1.setMagnitude(magni);
        c1.setPhase(phase);
        Assert.assertEquals(magni, c1.getMagnitude(), 0.01);
        Assert.assertEquals(phase, c1.getPhase(), 0.01);
    }

    @Test
    public void copyFrom(){
        double magni = 1d;
        double phase = 2d;
        Polar c1 = new Polar(magni, phase);
        Polar c2 = new Polar();
        Assert.assertEquals(magni, c1.getMagnitude(), 0.01);
        Assert.assertEquals(phase, c1.getPhase(), 0.01);
        Assert.assertEquals(0, c2.getMagnitude(), 0.01);
        Assert.assertEquals(0, c2.getPhase(), 0.01);
        c2.copyFrom(c1);
        Assert.assertEquals(magni, c1.getMagnitude(), 0.01);
        Assert.assertEquals(phase, c1.getPhase(), 0.01);
        Assert.assertEquals(magni, c2.getMagnitude(), 0.01);
        Assert.assertEquals(phase, c2.getPhase(), 0.01);
    }

    @Test
    public void copyTo(){
        double magni = 1d;
        double phase = 2d;
        Polar c1 = new Polar(magni, phase);
        Polar c2 = new Polar();
        Assert.assertEquals(magni, c1.getMagnitude(), 0.01);
        Assert.assertEquals(phase, c1.getPhase(), 0.01);
        Assert.assertEquals(0, c2.getMagnitude(), 0.01);
        Assert.assertEquals(0, c2.getPhase(), 0.01);
        c1.copyTo(c2);
        Assert.assertEquals(magni, c1.getMagnitude(), 0.01);
        Assert.assertEquals(phase, c1.getPhase(), 0.01);
        Assert.assertEquals(magni, c2.getMagnitude(), 0.01);
        Assert.assertEquals(phase, c2.getPhase(), 0.01);
    }
}
