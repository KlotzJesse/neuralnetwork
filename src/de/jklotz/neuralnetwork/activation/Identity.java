package de.jklotz.neuralnetwork.activation;

import de.jklotz.neuralnetwork.network.Neuron;

public class Identity implements ActivationFunction {

    @Override
    public double activate(Neuron neuron) {
        return neuron.getPreActivationValue();
    }

    @Override
    public double derivative(Neuron neuron) {
        return 1;
    }

}
