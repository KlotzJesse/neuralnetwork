package de.jklotz.neuralnetwork.regularization;

import de.jklotz.neuralnetwork.network.Neuron;
import de.jklotz.neuralnetwork.network.Synapse;

public class Dropout extends AbstractRegularization {

    public Dropout() {
        this(0.2);
    }

    public Dropout(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public double compute(Synapse synapse) {
        return 0.0;
    }

    @Override
    public double compute(Neuron neuron) {
        return Math.random() < lambda ? -1.0 : 1.0;
    }
}
