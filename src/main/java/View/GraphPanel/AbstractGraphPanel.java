package View.GraphPanel;

import Model.IAudioData;

import javax.swing.*;

public abstract class AbstractGraphPanel extends JPanel {
    protected final double MARGIN = 50.0;

    protected int height;
    protected double max;
    protected int width;
    protected double xInterval;
    protected double yInterval;
    protected double zeroHeight;

    protected void updateDimensions() {
        this.height = getHeight();
        this.max = getMax();
        this.width = getWidth();
        this.xInterval = getXInterval();
        this.yInterval = getYInterval();
        this.zeroHeight = getZeroHeight();
    }

    protected abstract double getMax();

    protected abstract double getXInterval();

    protected abstract double getYInterval();

    protected abstract double getZeroHeight();

    public abstract void setData(IAudioData data);
}
