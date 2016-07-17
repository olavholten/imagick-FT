package se.imagick.ft.common;

/**
 * Java implementation of a Polar value.<br>
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
public class Polar {
    private double magnitude;
    private double phase;

    public Polar() {
    }

    public Polar(Polar polar) {
        this(polar.getMagnitude(), polar.getPhase());
    }

    public Polar(final double magnitude, final double phase) {
        this.magnitude = magnitude;
        this.phase = phase;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(final double magnitude) {
        this.magnitude = magnitude;
    }

    public double getPhase() {
        return phase;
    }

    public void setPhase(final double phase) {
        this.phase = phase;
    }

    public void addPhase(final double phaseTurn) {
        this.phase += phaseTurn;
    }

    @Override
    public String toString() {
        return "[M:" + magnitude + "P:" + phase + "]";
    }

    public boolean equals(Polar that) {
        return (this.magnitude == that.magnitude) && (this.phase == that.phase);
    }

    public void copyTo(Polar polar) {
        polar.setMagnitude(magnitude);
        polar.setPhase(phase);
    }

    public void copyFrom(Polar polar) {
        this.magnitude = polar.getMagnitude();
        this.phase = polar.getPhase();
    }
}
