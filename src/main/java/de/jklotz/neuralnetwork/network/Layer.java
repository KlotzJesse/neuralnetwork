package de.jklotz.neuralnetwork.network;

import de.jklotz.neuralnetwork.activation.ActivationFunction;

import java.io.Serializable;
import java.util.ArrayList;

public class Layer extends ArrayList<Neuron> implements Serializable {

    public final ActivationFunction activationFunction;

    public Layer(int neurons, ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;

        for (int i = 0; i < neurons; i++) {
            this.add(new Neuron(this));
        }
    }


}
