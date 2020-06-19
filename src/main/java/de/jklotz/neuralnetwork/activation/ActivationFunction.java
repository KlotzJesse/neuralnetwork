package de.jklotz.neuralnetwork.activation;

import de.jklotz.neuralnetwork.activation.layer.Softmax;
import de.jklotz.neuralnetwork.activation.rectifier.ExponentialRectifier;
import de.jklotz.neuralnetwork.activation.rectifier.SwishRectifier;
import de.jklotz.neuralnetwork.network.Neuron;

import java.io.Serializable;

public interface ActivationFunction extends Serializable {

    Sigmoid SIGMOID = new Sigmoid();
    Identity IDENTITY = new Identity();
    SwishRectifier SWISH_RECTIFIER = new SwishRectifier();
    ExponentialRectifier EXPONENTIAL_RECTIFIER = new ExponentialRectifier();
    Softmax SOFTMAX = new Softmax();


    float activate(Neuron neuron);

    float derivative(Neuron neuron);

    default boolean isStochasticDerivative() {
        return false;
    }

}
