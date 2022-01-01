package Model;

public class RawAudioSamples implements IAudioData {
    private final double[] samples;

    public RawAudioSamples(short[] samples) {
        this.samples = new double[samples.length];
        for (int i = 0; i < samples.length; i++) this.samples[i] = samples[i];
    }

    @Override
    public double[] getData() {
        return samples;
    }
}
