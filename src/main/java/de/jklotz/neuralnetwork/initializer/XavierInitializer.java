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


        float nAvg = (inputNeurons + outputNeurons) / 2.0f;
        float variance = 1.0f / nAvg;

        float standardDeviation = (float) Math.sqrt(variance);


        for (Layer layer : layers) {

            for (Neuron neuron : layer) {
                Bias bias = neuron.bias;
                if (bias != null) {
                    bias.value = randomBias();
                    bias.weight = xavierWeight(standardDeviation);
                }
                neuron.setValue(0);
                for (Synapse outputConnection : neuron.outputConnections) {
                    outputConnection.weight = xavierWeight(standardDeviation);
                    outputConnection.change = 0;
                }
            }
        }
    }

    private float xavierWeight(float standardDeviation) {
        return (float) (rnd.nextGaussian() * standardDeviation);
    }

    private float randomBias() {
        return rnd.nextBoolean() ? -1.0f : 1.0f;
    }
}

