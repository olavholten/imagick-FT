package se.imagick.ft.slidingdft;

public class DFTSliderImpllTest extends DFTSliderTestParent {

    @Override
    DFTSlider getSliderImpl(int noOfFrequencies) {
        return new DFTSliderImpl(noOfFrequencies);
    }
}
