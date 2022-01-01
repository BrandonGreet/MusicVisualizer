package View.GraphPanel;

import Model.IAudioData;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class RawPCMAmplitudeGraph extends AbstractRawPCMGraph {
    private final List<AmplitudeRange> amplitudes;

    public RawPCMAmplitudeGraph() {
        super();
        setBackground(Color.black);
        this.amplitudes = new ArrayList<>();
        //TODO set a maximum size of amplitudes such that the graphic stretches from the left margin to the right margin.
    }

    @Override
    public void paintComponent(Graphics g) {
        updateDimensions();
        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.lightGray);
        for (AmplitudeRange r : amplitudes) {
            g2D.fill(new Rectangle2D.Double(r.x, zeroHeight - r.max * yInterval, xInterval, r.range * yInterval));
        }
    }

    @Override
    protected double getMax() {
        double max = 0.0;
        for (AmplitudeRange r : amplitudes) {
            if (r.max > max) max = r.max;
            if (Math.abs(r.min) >  max) max = Math.abs(r.min);
        }
        return max;
    }

    @Override
    protected double getXInterval() {
        if (amplitudes.size() * 5.0 > width - 2 * MARGIN) {
            return (width - 2 * MARGIN) / amplitudes.size();
        }
        return 5.0;
    }

    @Override
    public void setData(IAudioData data) {
        double max = Arrays.stream(data.getData()).max().getAsDouble();
        double min = Arrays.stream(data.getData()).min().getAsDouble();
        this.amplitudes.add(new AmplitudeRange(max, min));

        for (int i = 0; i < amplitudes.size(); i++) {
            AmplitudeRange r = amplitudes.get(amplitudes.size() - 1 - i);
            r.x = width - MARGIN - xInterval * i;
        }
    }

    private class AmplitudeRange {
        public final double max;
        public final double min;
        public final double range;

        public double x;

        public AmplitudeRange(double maxAmplitude, double minAmplitude) {
            this.max = maxAmplitude;
            this.min = minAmplitude;
            this.range = max - min;
        }
    }
}
