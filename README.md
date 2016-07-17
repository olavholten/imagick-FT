# imagick-FT
Java Fourier Utils, Sliding DFT.
When analysing a moving window the Sliding DFT / DCT is much more efficient than
using FFT.

##Usage is simple:

<pre>
int noOfFrequencies = 16;
DFTSlider slider = new DFTSliderImpl(noOfFrequencies); // One component will be added for the dc.
slider.slide(1.0d); // Adding one sample value to the end of the buffer, and pushing out the first.

int frequencyComponentNo = 3;
Polar polar3 = slider.getPolar(frequencyComponentNo); // NB! See documentation about instance reuse.
Complex complex3 = slider.getComplex(frequencyComponentNo); // NB! See documentation about instance reuse.

double magnitude3 = polar3.getMagnitude(); 
double phase3 = polar3.getPhase();
double real3 = complex3.getReal();
double imag3 = complex3.getImaginary();

double realSum = slider.getRealSum(false);
</pre>

Just add the following dependency in your pom-file:

    <dependency>
        <groupId>se.imagick</groupId>
        <artifactId>ft</artifactId>
        <version>1.3</version>
    </dependency>

NB! Only positive frequencies are calculated.


## Licence:

The MIT License (MIT)
Copyright (c) 2015 Olav Holten

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
