package de.jklotz.neuralnetwork.network;

import java.io.Serializable;

public class Synapse implements Serializable {

    public Neuron source;

    public Neuron target;

    public float weight;

    public float change;

    public Synapse(Neuron source, Neuron target, float weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Synapse(Neuron source, Neuron target) {
        this(source, target, (float) Math.random());
    }
}
