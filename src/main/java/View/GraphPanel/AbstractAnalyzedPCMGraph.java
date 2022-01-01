package View.GraphPanel;

abstract class AbstractAnalyzedPCMGraph extends AbstractGraphPanel {
    @Override
    protected double getYInterval() {
        return 0.2;
    }

    @Override
    protected double getZeroHeight() {
        return height - MARGIN;
    }
}
