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
public class FTUtilsTest{

    @Test
    public void testComplex2PolarRealReversal(){
        Complex c1 = new Complex(1d, 0);
        Complex c2 = new Complex();
        Polar p = new Polar();
        FTUtils.complex2Polar(c1, p);
        FTUtils.polar2Complex(p, c2);

        Assert.assertTrue(c1.equals(c2));

        c1 = new Complex(1d, 0.5d);

        FTUtils.complex2Polar(c1, p);
        FTUtils.polar2Complex(p, c2);

        Assert.assertTrue(c1.equals(c2));
    }

    @Test
    public void testPolar2ComplexReversal(){
        Polar p1 = new Polar(1d, 0d);
        Polar p2 = new Polar();
        Complex c = new Complex();
        FTUtils.polar2Complex(p1, c);
        FTUtils.complex2Polar(c, p2);

        Assert.assertTrue(p1.equals(p2));

        p1 = new Polar(1d, 0.5d);

        FTUtils.polar2Complex(p1, c);
        FTUtils.complex2Polar(c, p2);

        Assert.assertTrue(p1.equals(p2));
    }

    @Test
    public void degrees2Radians(){
        double degOrig = 45;
        double rad = FTUtils.degrees2Radians(degOrig);
        double degTransformed = FTUtils.radians2Degrees(rad);
        Assert.assertEquals(degOrig, degTransformed, 0.01);
    }

    @Test
    public void radians2Degrees(){
        double radOrig = Math.PI;
        double deg = FTUtils.radians2Degrees(radOrig);
        double radTransformed = FTUtils.degrees2Radians(deg);
        Assert.assertEquals(radOrig, radTransformed, 0.01);
    }

    @Test
    public void radians2DegreesTwoPi(){
        double radOrig = 2d * Math.PI;
        double deg = FTUtils.radians2Degrees(radOrig);
        Assert.assertEquals(deg, 360d, 0.01);
    }

    @Test
    public void degrees2Radians90Deg(){
        double degOrig = 90;
        double rad = FTUtils.degrees2Radians(degOrig);
        Assert.assertEquals(rad, Math.PI / 2d, 0.01);
    }

}
