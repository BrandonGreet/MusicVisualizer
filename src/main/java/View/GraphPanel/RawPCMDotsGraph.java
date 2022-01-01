package View.GraphPanel;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RawPCMDotsGraph extends AbstractRawPCMGraph {
    @Override
    public void paintComponent(Graphics g) {
        updateDimensions();
        Graphics2D g2D = (Graphics2D) g;

        g2D.setPaint(Color.blue);
        for (int i = 0; i < values.length; i++) {
            double x = MARGIN + i * xInterval;
            double y = zeroHeight - yInterval * values[i];
            g2D.fill(new Ellipse2D.Double(x, y,4,4));
        }
    }
}
