package de.jklotz.neuralnetwork.normalization;

public class MinMax implements Normalization {

    @Override
    public float[] normalize(float[] data) {
        float[] result = new float[data.length];
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        for (float datum : data) {
            min = Math.min(min, datum);
            max = Math.max(max, datum);
        }
        for (int i = 0; i < data.length; i++) {
            result[i] = (data[i] - min) / (max - min);
        }
        return result;
    }
}
