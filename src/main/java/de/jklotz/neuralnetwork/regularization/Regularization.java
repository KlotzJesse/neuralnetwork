package de.jklotz.neuralnetwork.regularization;

import de.jklotz.neuralnetwork.network.Neuron;
import de.jklotz.neuralnetwork.network.Synapse;

import java.io.Serializable;

public interface Regularization extends Serializable {

    double compute(Synapse synapse);

    double compute(Neuron neuron);

    void setLambda(double lambda);

    double getLambda();
}
