package de.jklotz.neuralnetwork.normalization;

public class Gaussian implements Normalization {

    @Override
    public float[] normalize(float[] data) {
        float[] result = new float[data.length];
        float sum = 0;
        for (float datum : data) {
            sum += datum;
        }
        float mean = sum / (float) data.length;
        sum = 0;
        float delta;
        for (float datum : data) {
            delta = datum - mean;
            sum += (delta * delta);
        }
        float standardDeviation = (float) Math.sqrt(sum / (float) data.length);
        for (int i = 0; i < data.length; i++) {
            result[i] = (data[i] - mean) / standardDeviation;
        }
        return result;
    }
}
