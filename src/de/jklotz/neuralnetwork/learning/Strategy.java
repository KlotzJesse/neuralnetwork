package de.jklotz.neuralnetwork.learning;


import de.jklotz.neuralnetwork.network.NeuronalNetwork;
import de.jklotz.neuralnetwork.regularization.Dropout;
import de.jklotz.neuralnetwork.regularization.Regularization;

public abstract class Strategy implements LearningStrategy {

    public NeuronalNetwork network;

    public double momentum;
    public double learningRate;

    public Regularization regularization = new Dropout();

}
