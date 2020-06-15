package de.jklotz.neuralnetwork.normalization;

public class MinMax implements Normalization {

    @Override
    public double[] normalize(double[] data) {
        double[] result = new double[data.length];
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < data.length; i++) {
            min = Math.min(min, data[i]);
            max = Math.max(max, data[i]);
        }
        for (int i = 0; i < data.length; i++) {
            result[i] = (data[i] - min) / (max - min);
        }
        return result;
    }
}
