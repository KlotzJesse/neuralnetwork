package de.jklotz.neuralnetwork.network;

import de.jklotz.neuralnetwork.activation.ActivationFunction;
import de.jklotz.neuralnetwork.initializer.Initializer;

public class NeuronalNetworkBuilder extends NeuronalNetwork {

    private NeuronalNetwork network;

    public NeuronalNetworkBuilder() {
        this.network = new NeuronalNetwork();
    }

    public NeuronalNetworkBuilder layer(int neurons, ActivationFunction activationFunction) {
        Layer layer = new Layer(neurons, activationFunction);
        this.network.layers.add(layer);

        return this;
    }

    public NeuronalNetworkBuilder layer(int neurons) {
        Layer layer = new Layer(neurons, null);
        this.network.layers.add(layer);

        return this;
    }

    public NeuronalNetworkBuilder initializer(Initializer initializer) {
        this.network.initializer = initializer;
        return this;
    }

    public NeuronalNetwork build() {
        this.network.connectLayers();
        this.network.initializer.initialize(this.network);

        return this.network;
    }

}
