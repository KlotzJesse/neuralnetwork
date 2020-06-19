package de.jklotz.neuralnetwork.network;

import java.io.Serializable;
import java.util.Random;

public class Bias implements Serializable {

    public float value;

    public float weight;

    private static final Random RND = new Random();

    public Bias() {
        this(1.0f);
    }

    public Bias(float value) {
        this(value, RND.nextFloat() * 2.0f - 1.0f);
    }

    public Bias(float value, float weight) {
        this.value = value;
        this.weight = weight;
    }

    public float compute() {
        return value * weight;
    }
}
