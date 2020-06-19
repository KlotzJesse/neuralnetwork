package de.jklotz.neuralnetwork.activation.rectifier;


import de.jklotz.neuralnetwork.activation.ActivationFunction;
import de.jklotz.neuralnetwork.network.Neuron;

public class Rectifier implements ActivationFunction {

    protected double leakiness = 0.0;

    @Override
    public double activate(Neuron neuron) {
        return neuron.getPreActivationValue() < 0 ? (leakiness * neuron.getPreActivationValue()) : neuron.getPreActivationValue();
    }

    @Override
    public double derivative(Neuron neuron) {
        return neuron.getPreActivationValue() < 0 ? leakiness : 1.0;
    }
}
