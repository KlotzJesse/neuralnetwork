package de.jklotz.neuralnetwork.activation.rectifier;

import de.jklotz.neuralnetwork.network.Neuron;

public class ExponentialRectifier extends ParametricRectifier {

    public ExponentialRectifier() {
        this(0.01f);
    }

    public ExponentialRectifier(float leakiness) {
        this.leakiness = leakiness;
    }

    @Override
    public float activate(Neuron neuron) {
        return neuron.getPreActivationValue() < 0 ? (float) (leakiness * (Math.exp(neuron.getPreActivationValue()) - 1.0f)) : neuron.getPreActivationValue();
    }

    @Override
    public float derivative(Neuron neuron) {
        return neuron.getPreActivationValue() < 0 ? (neuron.getValue() + leakiness) : 1.0f;
    }
}
