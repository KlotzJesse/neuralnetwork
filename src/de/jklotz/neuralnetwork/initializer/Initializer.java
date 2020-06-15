package de.jklotz.neuralnetwork.initializer;

import de.jklotz.neuralnetwork.network.NeuronalNetwork;

import java.io.Serializable;

public interface Initializer extends Serializable {

    XavierInitializer XAVIER_INITIALIZER = new XavierInitializer();

    void initialize(NeuronalNetwork network);

}
