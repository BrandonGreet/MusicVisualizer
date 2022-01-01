package Controller;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class Utils {
    private static FastFourierTransformer FFT = new FastFourierTransformer(DftNormalization.STANDARD);

    public static double[] FastFourierTransform(double[] d) {
        double[] result = new double[d.length];
        Complex[] temp = FFT.transform(d, TransformType.FORWARD);
        for (int i = 0; i < temp.length; i++) result[i] = temp[i].getReal();
        return result;
    }
}
