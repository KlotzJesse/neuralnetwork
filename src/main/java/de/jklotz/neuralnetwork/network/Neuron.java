package de.jklotz.neuralnetwork.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Neuron implements Serializable {

    public final Bias bias;
    public final List<Synapse> inputConnections;
    public final List<Synapse> outputConnections;
    private Layer layer;
    private double value;
    private double anticipatedValue;
    private double errorValue;
    private boolean fired;
    private boolean preComputed;
    private double preActivationValue;

    public Neuron() {
        this(1, null);
    }

    public Neuron(Layer layer) {
        this(1, layer);
    }

    public Neuron(double value, Layer layer) {
        this.inputConnections = new ArrayList<>();
        this.outputConnections = new ArrayList<>();
        this.value = value;
        this.layer = layer;
        this.bias = new Bias();
        this.fired = false;
        this.preComputed = false;
        this.errorValue = 0.0;
    }

    public Layer getLayer() {
        return this.layer;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getErrorValue() {
        return this.errorValue;
    }

    public void setErrorValue(double errorValue) {
        this.errorValue = errorValue;
    }

    public double getAnticipatedValue() {
        return this.anticipatedValue;
    }

    public void setAnticipatedValue(double anticipatedValue) {
        this.anticipatedValue = anticipatedValue;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
        this.preComputed = fired;
    }

    public double preCompute() {
        if (preComputed) {
            return preActivationValue;
        }

        double currentValue = 0.0;

        for (Synapse synapse : inputConnections) {
            currentValue += synapse.source.fire() * synapse.weight;
        }

        currentValue += bias.compute();
        preActivationValue = currentValue;
        preComputed = true;
        return preActivationValue;
    }

    public double fire() {
        int dataSize = inputConnections.size();
        if (fired || dataSize <= 0) {
            return value;
        }

        preCompute();
        value = layer.activationFunction.activate(this);
        fired = true;
        return value;
    }

    public double getPreActivationValue() {
        return preActivationValue;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "["
                + this.value
                + "]";
    }
}
