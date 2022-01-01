package View.GraphPanel;

import Model.IAudioData;

import java.awt.*;
import java.awt.geom.Path2D;

public class RawPCMLineGraph extends AbstractRawPCMGraph {
    private double[] amplitudes;

    @Override
    public void paintComponent(Graphics g) {
        updateDimensions();
        Graphics2D g2D = (Graphics2D) g;

        g2D.setPaint(Color.magenta);
        Path2D line = new Path2D.Double();
        line.moveTo(MARGIN, zeroHeight - yInterval * amplitudes[0]);
        for (int i = 0; i < amplitudes.length; i++) {
            double x = MARGIN + i * xInterval;
            double y = zeroHeight - yInterval * amplitudes[i];
            line.lineTo(x, y);
        }
        g2D.setStroke(new BasicStroke(3));
        g2D.draw(line);
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
