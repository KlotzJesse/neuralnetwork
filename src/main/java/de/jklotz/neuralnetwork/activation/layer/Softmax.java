package de.jklotz.neuralnetwork.activation.layer;

import de.jklotz.neuralnetwork.network.Layer;
import de.jklotz.neuralnetwork.network.Neuron;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Softmax implements LayerActivationFunction {

    private float sum = 0;

    private Lock lock = new ReentrantLock();

    @Override
    public void initialize(Layer layer) {
        lock.lock();
        try {
            sum = 0;
            layer.forEach(neuron -> sum += Math.exp(neuron.getPreActivationValue()));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public float activate(Neuron neuron) {
        return (float) (Math.exp(neuron.getPreActivationValue()) / sum);
    }

    @Override
    public float derivative(Neuron neuron) {
        return 1.0f;
    }

    @Override
    public boolean isStochasticDerivative() {
        return true;
    }
}