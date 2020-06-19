package de.jklotz.neuralnetwork.activation.layer;

import de.jklotz.neuralnetwork.activation.ActivationFunction;
import de.jklotz.neuralnetwork.network.Layer;

public interface LayerActivationFunction extends ActivationFunction {

    void initialize(Layer layer);

}
