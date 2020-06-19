package de.jklotz.neuralnetwork.network;

import com.google.common.util.concurrent.AtomicDouble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Neuron implements Serializable {

    public final Bias bias;
    public final List<Synapse> inputConnections;
    public final List<Synapse> outputConnections;
    private Layer layer;
    private float value;
    private float anticipatedValue;
    private float errorValue;
    private boolean fired;
    private boolean preComputed;
    private float preActivationValue;

    public Neuron() {
        this(1, null);
    }

    public Neuron(Layer layer) {
        this(1, layer);
    }

    public Neuron(float value, Layer layer) {
        this.inputConnections = new ArrayList<>();
        this.outputConnections = new ArrayList<>();
        this.value = value;
        this.layer = layer;
        this.bias = new Bias();
        this.fired = false;
        this.preComputed = false;
        this.errorValue = 0;
    }

    public Layer getLayer() {
        return this.layer;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getErrorValue() {
        return this.errorValue;
    }

    public void setErrorValue(float errorValue) {
        this.errorValue = errorValue;
    }

    public float getAnticipatedValue() {
        return this.anticipatedValue;
    }

    public void setAnticipatedValue(float anticipatedValue) {
        this.anticipatedValue = anticipatedValue;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
        this.preComputed = fired;
    }

    public float preCompute(boolean parallel) {
        if (preComputed) {
            return preActivationValue;
        }

        final AtomicDouble currentValue = new AtomicDouble(0.0);

        if (parallel)
            inputConnections.parallelStream().forEach(synapse -> currentValue.addAndGet(synapse.source.fire(false) + synapse.weight));
        else {
            for (Synapse synapse : inputConnections) {
                currentValue.addAndGet(synapse.source.fire(false) * synapse.weight);
            }
        }

        currentValue.addAndGet(bias.compute());
        preActivationValue = currentValue.floatValue();

        preComputed = true;
        return preActivationValue;
    }

    public float fire(boolean parallel) {
        int dataSize = inputConnections.size();
        if (fired || dataSize <= 0) {
            return value;
        }

        preCompute(parallel);
        value = layer.activationFunction.activate(this);

        fired = true;
        return value;
    }

    public float getPreActivationValue() {
        return preActivationValue;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "["
                + this.value
                + "]";
    }
}
