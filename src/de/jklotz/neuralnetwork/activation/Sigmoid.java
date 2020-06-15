package de.jklotz.neuralnetwork.activation;

import de.jklotz.neuralnetwork.network.Neuron;

public class Sigmoid implements ActivationFunction {

    @Override
    public double activate(Neuron neuron) {
        return 1.0 / (1.0 + Math.exp(-neuron.getPreActivationValue()));
    }

    @Override
    public double derivative(Neuron neuron) {
        return neuron.getValue() * (1.0 - neuron.getValue());
    }

    @Override
    public boolean isStochasticDerivative() {
        return true;
    }

}
