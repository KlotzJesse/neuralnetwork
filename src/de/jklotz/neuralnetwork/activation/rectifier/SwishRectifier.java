package de.jklotz.neuralnetwork.activation.rectifier;


import de.jklotz.neuralnetwork.activation.ActivationFunction;
import de.jklotz.neuralnetwork.network.Neuron;

public class SwishRectifier extends Rectifier {


    @Override
    public double activate(Neuron neuron) {
        return neuron.getPreActivationValue() * ActivationFunction.SIGMOID.activate(neuron);
    }

    @Override
    public double derivative(Neuron neuron) {
        double z = SIGMOID.activate(neuron);
        return z * (1.0 + neuron.getPreActivationValue() * (1.0 - z));
    }
}
