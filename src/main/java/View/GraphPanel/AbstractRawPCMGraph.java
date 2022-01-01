package View.GraphPanel;

abstract class AbstractRawPCMGraph extends AbstractGraphPanel {
    @Override
    protected double getYInterval() {
        return (height / 2.0 - MARGIN) / getMax();
    }

    @Override
    protected double getZeroHeight() {
        return height / 2.0;
    }
}
