package Model;

import Controller.Utils;

import java.util.Arrays;

public class FrequencySpectrum implements IAudioData {
    private double[] frequencies;

    public FrequencySpectrum(short[] samples) {
        double[] temp = new double[512];
        for (int i = 0; i < samples.length; i++) temp[i] = samples[i];
        this.frequencies = Arrays.stream(Utils.FastFourierTransform(temp)).map(Math::abs).toArray();
    }

    @Override
    public double[] getData() {
        return frequencies;
    }
}
