package de.jklotz.neuralnetwork.regularization;

import de.jklotz.neuralnetwork.network.Neuron;
import de.jklotz.neuralnetwork.network.Synapse;

public class Dropout extends AbstractRegularization {

    public Dropout() {
        this(0.2f);
    }

    public Dropout(float lambda) {
        this.lambda = lambda;
    }

    @Override
    public float compute(Synapse synapse) {
        return 0;
    }

    @Override
    public float compute(Neuron neuron) {
        return Math.random() < lambda ? -1.0f : 1.0f;
    }
}
