package de.jklotz.neuralnetwork.normalization;

import java.io.Serializable;

public interface Normalization extends Serializable {

    Gaussian GAUSSIAN = new Gaussian();
    MinMax MIN_MAX = new MinMax();

    float[] normalize(float[] data);
}