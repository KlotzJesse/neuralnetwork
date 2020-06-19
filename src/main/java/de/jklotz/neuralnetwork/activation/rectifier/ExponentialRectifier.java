package de.jklotz.neuralnetwork.activation.rectifier;

import de.jklotz.neuralnetwork.network.Neuron;

public class ExponentialRectifier extends ParametricRectifier {

    public ExponentialRectifier() {
        this(0.01);
    }

    public ExponentialRectifier(double leakiness) {
        this.leakiness = leakiness;
    }

    @Override
    public double activate(Neuron neuron) {
        return neuron.getPreActivationValue() < 0 ? (leakiness * (Math.exp(neuron.getPreActivationValue()) - 1.0)) : neuron.getPreActivationValue();
    }

    @Override
    public double derivative(Neuron neuron) {
        return neuron.getPreActivationValue() < 0 ? (neuron.getValue() + leakiness) : 1.0;
    }
}
