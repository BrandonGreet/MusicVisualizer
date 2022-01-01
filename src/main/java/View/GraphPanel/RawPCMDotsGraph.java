package View.GraphPanel;

import Model.IAudioData;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

public class RawPCMDotsGraph extends AbstractRawPCMGraph {
    private double[] amplitudes;

    @Override
    public void paintComponent(Graphics g) {
        updateDimensions();
        Graphics2D g2D = (Graphics2D) g;

        g2D.setPaint(Color.blue);
        for (int i = 0; i < amplitudes.length; i++) {
            double x = MARGIN + i * xInterval;
            double y = zeroHeight - yInterval * amplitudes[i];
            g2D.fill(new Ellipse2D.Double(x, y,4,4));
        }
    }

    @Override
    protected double getMax() {
        double max = 0.0;
        for (double d : amplitudes) {
            if (Math.abs(d) > max) max = Math.abs(d);
        }
        return max;
    }

    @Override
    protected double getXInterval() {
        return (width - 2 * MARGIN) / (amplitudes.length - 1);
    }

    @Override
    public void setData(IAudioData data) {
        this.amplitudes = data.getData();
    }
}
