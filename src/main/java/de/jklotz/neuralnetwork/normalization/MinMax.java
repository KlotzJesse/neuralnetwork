package de.jklotz.neuralnetwork.normalization;

public class MinMax implements Normalization {

    @Override
    public double[] normalize(double[] data) {
        double[] result = new double[data.length];
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (double datum : data) {
            min = Math.min(min, datum);
            max = Math.max(max, datum);
        }
        for (int i = 0; i < data.length; i++) {
            result[i] = (data[i] - min) / (max - min);
        }
        return result;
    }
}
