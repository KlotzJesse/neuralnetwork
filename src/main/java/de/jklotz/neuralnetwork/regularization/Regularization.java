package de.jklotz.neuralnetwork.regularization;

import de.jklotz.neuralnetwork.network.Neuron;
import de.jklotz.neuralnetwork.network.Synapse;

import java.io.Serializable;

public interface Regularization extends Serializable {

    float compute(Synapse synapse);

    float compute(Neuron neuron);

    float getLambda();

    void setLambda(float lambda);
}
