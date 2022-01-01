package View.GraphPanel;

import Model.FrequencySpectrum;
import Model.IAudioData;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class PCMFrequencySpectrumGraph extends AbstractAnalyzedPCMGraph {
    private final List<FrequencySpectrum> frequencySpectra;

    public PCMFrequencySpectrumGraph() {
        super();
        setBackground(Color.black);
        this.frequencySpectra = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
        updateDimensions();
        Graphics2D g2D = (Graphics2D) g;

        for (int i = 0; i < frequencySpectra.size(); i++) {
            double[] data = frequencySpectra.get(frequencySpectra.size() - 1 - i).getData();

            for (int j = 0; j < data.length; j++) {
                double x = MARGIN + j * xInterval;
                double y = zeroHeight - i * yInterval;

                /*if (data[j] < 0.005) {
                    g2D.setColor(Color.blue);
                } else if (data[j] < 0.01) {
                    g2D.setColor(Color.green);
                } else if (data[j] < 0.03) {
                    g2D.setColor(Color.yellow);
                } else if (data[j] <= 1.0) {
                    g2D.setColor(Color.white);
                } else {
                    g2D.setColor(Color.red);
                }*/
                //TODO: Accurately compute color
                g2D.setColor(new Color((int) data[j]));

                g2D.fill(new Rectangle2D.Double(x, y, xInterval, yInterval));
            }
        }
    }

    @Override
    public void setData(IAudioData data) {
        if (!(data instanceof FrequencySpectrum)) throw new IllegalArgumentException();
        this.frequencySpectra.add((FrequencySpectrum) data);
    }

    @Override
    protected double getMax() {
        return 0;
    }

    @Override
    protected double getXInterval() {
        return (width - 2 * MARGIN) / 512;
    }

    @Override
    protected double getYInterval() {
        if (frequencySpectra.size() > height - 2 * MARGIN) {
            return (height - 2 * MARGIN) / frequencySpectra.size();
        }
        return 1.0;
    }
}
