package de.jklotz.neuralnetwork.activation.rectifier;


import de.jklotz.neuralnetwork.activation.ActivationFunction;
import de.jklotz.neuralnetwork.network.Neuron;

public class Rectifier implements ActivationFunction {

    protected float leakiness = 0;

    @Override
    public float activate(Neuron neuron) {
        return neuron.getPreActivationValue() < 0 ? (leakiness * neuron.getPreActivationValue()) : neuron.getPreActivationValue();
    }

    @Override
    public float derivative(Neuron neuron) {
        return neuron.getPreActivationValue() < 0 ? leakiness : 1.0f;
    }
}
