package de.jklotz.neuralnetwork.activation;

import de.jklotz.neuralnetwork.network.Neuron;

public class Sigmoid implements ActivationFunction {

    @Override
    public float activate(Neuron neuron) {
        return (float) (1.0f / (1.0f + Math.exp(-neuron.getPreActivationValue())));
    }

    @Override
    public float derivative(Neuron neuron) {
        return neuron.getValue() * (1.0f - neuron.getValue());
    }

    @Override
    public boolean isStochasticDerivative() {
        return true;
    }

}
