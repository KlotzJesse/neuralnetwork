package de.jklotz.neuralnetwork.activation;

import de.jklotz.neuralnetwork.network.Neuron;

public class Identity implements ActivationFunction {

    @Override
    public float activate(Neuron neuron) {
        return neuron.getPreActivationValue();
    }

    @Override
    public float derivative(Neuron neuron) {
        return 1;
    }

}
