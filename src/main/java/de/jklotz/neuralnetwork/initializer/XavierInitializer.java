package de.jklotz.neuralnetwork.initializer;

import de.jklotz.neuralnetwork.network.*;

import java.util.List;
import java.util.Random;

public class XavierInitializer implements Initializer {

    private Random rnd;

    public XavierInitializer() {
        this(System.currentTimeMillis());
    }

    public XavierInitializer(long seed) {
        rnd = new Random(seed);
    }

    @Override
    public void initialize(NeuronalNetwork network) {
        List<Layer> layers = network.layers;
        int inputNeurons = layers.get(0).size();
        int outputNeurons = layers.get(layers.size() - 1).size();


        double nAvg = (inputNeurons + outputNeurons) / 2.0;
        double variance = 1.0 / nAvg;

        double standardDeviation = Math.sqrt(variance);


        for (Layer layer : layers) {

            for (Neuron neuron : layer) {
                Bias bias = neuron.bias;
                if (bias != null) {
                    bias.value = randomBias();
                    bias.weight = xavierWeight(standardDeviation);
                }
                neuron.setValue(0.0);
                for (Synapse outputConnection : neuron.outputConnections) {
                    outputConnection.weight = xavierWeight(standardDeviation);
                    outputConnection.change = 0.0;
                }
            }
        }
    }

    private double xavierWeight(double standardDeviation) {
        return rnd.nextGaussian() * standardDeviation;
    }

    private double randomBias() {
        return rnd.nextBoolean() ? -1.0 : 1.0;
    }
}

