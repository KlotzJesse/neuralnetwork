package de.jklotz.neuralnetwork.normalization;

import java.io.Serializable;

public interface Normalization extends Serializable {

    Gaussian GAUSSIAN = new Gaussian();
    MinMax MIN_MAX = new MinMax();

    double[] normalize(double[] data);
}