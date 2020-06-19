package de.jklotz.neuralnetwork.normalization;

public class Gaussian implements Normalization {

    @Override
    public double[] normalize(double[] data) {
        double[] result = new double[data.length];
        double sum = 0.0;
        for (double datum : data) {
            sum += datum;
        }
        double mean = sum / (double) data.length;
        sum = 0.0;
        double delta;
        for (double datum : data) {
            delta = datum - mean;
            sum += (delta * delta);
        }
        double standardDeviation = Math.sqrt(sum / (double) data.length);
        for (int i = 0; i < data.length; i++) {
            result[i] = (data[i] - mean) / standardDeviation;
        }
        return result;
    }
}
