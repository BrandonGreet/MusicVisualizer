package View.GraphPanel;

abstract class AbstractRawPCMGraph extends AbstractGraphPanel {
    @Override
    protected double getXInterval() {
        return (width - 2 * MARGIN) / (values.length - 1);
    }

    @Override
    protected double getYInterval() {
        return (height - 2 * MARGIN) / MAX;
    }

    @Override
    protected double getZeroHeight() {
        return height / 2.0;
    }
}
