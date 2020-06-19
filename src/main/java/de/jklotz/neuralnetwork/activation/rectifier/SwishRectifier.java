package de.jklotz.neuralnetwork.activation.rectifier;


import de.jklotz.neuralnetwork.activation.ActivationFunction;
import de.jklotz.neuralnetwork.network.Neuron;

public class SwishRectifier extends Rectifier {


    @Override
    public float activate(Neuron neuron) {
        return neuron.getPreActivationValue() * ActivationFunction.SIGMOID.activate(neuron);
    }

    @Override
    public float derivative(Neuron neuron) {
        float z = SIGMOID.activate(neuron);
        return z * (1.0f + neuron.getPreActivationValue() * (1.0f - z));
    }
}
