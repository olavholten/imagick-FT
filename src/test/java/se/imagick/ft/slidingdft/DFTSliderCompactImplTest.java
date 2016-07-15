package se.imagick.ft.slidingdft;

public class DFTSliderCompactImplTest extends DFTSliderTestParent {

    @Override
    DFTSlider getSliderImpl(int noOfFrequencies) {
        return new DFTSliderCompactImpl(noOfFrequencies);
    }
}
