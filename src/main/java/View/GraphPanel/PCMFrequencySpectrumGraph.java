package View.GraphPanel;

import Model.FrequencySpectrum;
import Model.IAudioData;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class PCMFrequencySpectrumGraph extends AbstractAnalyzedPCMGraph {
    private List<FrequencySpectrum> frequencySpectra;
    //private List<Path2D> frequencySpectra;

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
            double[] data = frequencySpectra.get(i).getData();

            for (int j = 0; j < data.length; j++) {
                double x = MARGIN + j * xInterval;
                double y = zeroHeight - i * yInterval * 10;

                if (data[j] < 0.005) {
                    g2D.setColor(Color.blue);
                } else if (data[j] < 0.01) {
                    g2D.setColor(Color.green);
                } else if (data[j] < 0.03) {
                    g2D.setColor(Color.yellow);
                } else if (data[j] <= 1.0) {
                    g2D.setColor(Color.white);
                } else {
                    g2D.setColor(Color.red);
                }

                g2D.fill(new Ellipse2D.Double(x, y,1,1));
            }
        }
    }

    @Override
    public void setValues(IAudioData data) {
        if (!(data instanceof FrequencySpectrum)) throw new IllegalArgumentException();
        this.frequencySpectra.add((FrequencySpectrum) data);
        this.values = data.getData();
        /*Path2D spectrum = new Path2D.Double();
        spectrum.moveTo(MARGIN, zeroHeight);
        for (int i = 0; i < data.getData().length; i++) {
            spectrum.lineTo(MARGIN + i * xInterval, );
        }*/
    }
}
