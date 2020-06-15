package de.jklotz.neuralnetwork.network;

import de.jklotz.neuralnetwork.activation.layer.LayerActivationFunction;
import de.jklotz.neuralnetwork.initializer.Initializer;
import de.jklotz.neuralnetwork.normalization.Normalization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
public class NeuronalNetwork implements Serializable {

    public final List<Layer> layers;
    public Initializer initializer;
    public Normalization normalization;

    public NeuronalNetwork() {
        this.layers = new ArrayList<>();
        this.initializer = Initializer.XAVIER_INITIALIZER;
        this.normalization = Normalization.MIN_MAX;
    }

    public final void connectLayers() {
        for (int i = 1; i < layers.size(); i++) {
            Layer previousLayer = layers.get(i - 1);
            Layer layer = layers.get(i);

            for (Neuron neuron : layer) {
                for (Neuron previousNeuron : previousLayer) {
                    Synapse connection = new Synapse(previousNeuron, neuron);
                    previousNeuron.outputConnections.add(connection);
                    neuron.inputConnections.add(connection);
                }
            }
        }

    }

    public double[] output() {
        Layer outputLayer = this.layers.get(layers.size() - 1);
        double[] result = new double[outputLayer.size()];
        for (int i = 0; i < outputLayer.size(); i++) {
            result[i] = outputLayer.get(i).getValue();
        }
        return result;
    }

    public double[] input() {
        Layer inputLayer = this.layers.get(0);
        double[] result = new double[inputLayer.size()];
        for (int i = 0; i < inputLayer.size(); i++) {
            result[i] = inputLayer.get(i).getValue();
        }
        return result;
    }

    public void input(double... inputValues) {
        Layer inputLayer = this.layers.get(0);
        if (inputValues.length != inputLayer.size()) {
            throw new IllegalArgumentException("Number of inputs does not match number of input neurons");
        }

        if (normalization != null) {
            inputValues = normalization.normalize(inputValues);
        }

        for (int i = 0; i < inputValues.length; i++) {
            inputLayer.get(i).setValue(inputValues[i]);

        }
    }

    private void resetNeuronFiredState() {
        for (Layer layer : layers) {
            for (Neuron neuron : layer) {
                neuron.setFired(false);
            }
        }
    }

    public void fireOutput() {
        Layer layer = this.layers.get(this.layers.size() - 1);

        layer.forEach(Neuron::preCompute);

        LayerActivationFunction act = (LayerActivationFunction) layer.activationFunction;
        act.initialize(layer);
        layer.forEach(Neuron::fire);


        resetNeuronFiredState();
    }

    public void setAnticipatedOutput(double... outputValues) {
        Layer outputLayer = this.layers.get(this.layers.size() - 1);

        for (int i = 0; i < outputLayer.size(); i++) {
            outputLayer.get(i).setAnticipatedValue(outputValues[i]);
        }
    }


}
