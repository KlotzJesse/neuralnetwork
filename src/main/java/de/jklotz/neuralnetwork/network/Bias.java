package de.jklotz.neuralnetwork.network;

import java.io.Serializable;
import java.util.Random;

public class Bias implements Serializable {

    public double value;

    public double weight;

    private static final Random RND = new Random();

    public Bias() {
        this(1.0);
    }

    public Bias(double value) {
        this(value, RND.nextDouble() * 2.0 - 1.0);
    }

    public Bias(double value, double weight) {
        this.value = value;
        this.weight = weight;
    }

    public double compute() {
        return value * weight;
    }
}
