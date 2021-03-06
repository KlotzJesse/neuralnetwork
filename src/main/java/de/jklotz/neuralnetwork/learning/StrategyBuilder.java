package de.jklotz.neuralnetwork.learning;

import de.jklotz.neuralnetwork.network.NeuronalNetwork;
import de.jklotz.neuralnetwork.regularization.Regularization;

public class StrategyBuilder extends Strategy {

    private final Strategy strategy;

    public StrategyBuilder(Strategy strategy) {
        this.strategy = strategy;
    }

    public StrategyBuilder neuronalNetwork(NeuronalNetwork network) {
        this.strategy.network = network;
        return this;
    }

    public StrategyBuilder learningRate(float learningRate) {
        this.strategy.learningRate = learningRate;
        return this;
    }

    public StrategyBuilder momentum(float momentum) {
        this.strategy.momentum = momentum;
        return this;
    }

    public StrategyBuilder regularization(Regularization regularization) {
        this.strategy.regularization = regularization;
        return this;
    }

    public Strategy build() {
        return this.strategy;
    }

    //Not implemented yet.
    public void learn() {

    }

    //Not implemented yet.
    public float networkError() {
        return 0;
    }
}
