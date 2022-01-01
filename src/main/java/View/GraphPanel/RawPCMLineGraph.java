package View.GraphPanel;

import java.awt.*;
import java.awt.geom.Path2D;

public class RawPCMLineGraph extends AbstractRawPCMGraph {
    @Override
    public void paintComponent(Graphics g) {
        updateDimensions();
        Graphics2D g2D = (Graphics2D) g;

        g2D.setPaint(Color.blue);
        Path2D line = new Path2D.Double();
        line.moveTo(MARGIN, zeroHeight - yInterval * values[0]);
        for (int i = 0; i < values.length; i++) {
            double x = MARGIN + i * xInterval;
            double y = zeroHeight - yInterval * values[i];
            line.lineTo(x, y);
        }
        g2D.draw(line);
    }
}
