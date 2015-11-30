package se.imagick.ft.slidingdft;

public class DFTSliderZeroFrequency extends DFTSliderFrequency {

    private double dc = 0d;

    public DFTSliderZeroFrequency(final double noofFreq, final double freqNo){
        super(noofFreq, freqNo);
    }

    @Override
    public void slide(double change){
        dc += change;
    }

    @Override
    public double getAmplitude(){
        return dc;
    }

    @Override
    public double getReal(){
        return dc;
    }
}
