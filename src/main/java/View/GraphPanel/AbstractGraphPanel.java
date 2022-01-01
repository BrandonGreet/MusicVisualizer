package View.GraphPanel;

import Model.IAudioData;

import javax.swing.*;

public abstract class AbstractGraphPanel extends JPanel {
    protected final double MARGIN = 50.0;
    protected final double MAX = 22050.0;

    protected int height;
    protected int width;
    protected double xInterval;
    protected double yInterval;
    protected double[] values;
    protected double zeroHeight;

    protected void updateDimensions() {
        this.height = getHeight();
        this.width = getWidth();
        this.xInterval = getXInterval();
        this.yInterval = getYInterval();
        this.zeroHeight = getZeroHeight();
    }

    abstract protected double getXInterval();

    abstract protected double getYInterval();

    abstract protected double getZeroHeight();

    public void setValues(IAudioData data) {
        this.values = data.getData();
    }

    public enum GraphType {
        RawPCMDots,
        RawPCMLine,
        PCMFrequencySpectrum
    }
}
