package de.jklotz.neuralnetwork.learning;

import de.jklotz.neuralnetwork.activation.ActivationFunction;
import de.jklotz.neuralnetwork.network.Bias;
import de.jklotz.neuralnetwork.network.Layer;
import de.jklotz.neuralnetwork.network.Neuron;
import de.jklotz.neuralnetwork.network.Synapse;

public class BackPropagation extends Strategy {

    @Override
    public void learn() {
        network.fireOutput();

        for (Layer layer : network.layers) {
            for (Neuron neuron : layer) {
                neuron.setErrorValue(0.0);
                for (Synapse inputConnection : neuron.inputConnections) {
                    inputConnection.change = 0.0;
                }
            }
        }


        Layer outputLayer = network.layers.get(network.layers.size() - 1);
        for (int i = 0; i < outputLayer.size(); i++) {
            Neuron outputNeuron = outputLayer.get(i);

            double actualValue = outputNeuron.getValue();
            double expectedValue = outputNeuron.getAnticipatedValue() - actualValue;

            ActivationFunction activationFunction = outputLayer.activationFunction;
            if (activationFunction != null && activationFunction.isStochasticDerivative()) {
                outputNeuron.setErrorValue(expectedValue * 0.1);
                continue;
            }

            double b = actualValue * (1.0 - actualValue);
            outputNeuron.setErrorValue((expectedValue / b) * activationFunction.derivative(outputNeuron));
        }

        for (int i = network.layers.size() - 2; i >= 1; i--) {
            Layer layer = network.layers.get(i);
            for (Neuron neuron : layer) {
                double sum = 0.0;
                for (Synapse outputConnection : neuron.outputConnections) {
                    sum += outputConnection.target.getErrorValue() * outputConnection.weight;
                }
                neuron.setErrorValue(sum * neuron.getLayer().activationFunction.derivative(neuron));
            }
        }


        for (int i = network.layers.size() - 1; i >= 0; i--) {
            Layer layer = network.layers.get(i);

            for (Neuron neuron : layer) {
                if (regularization != null && regularization.compute(neuron) <= 0.0) {
                    continue;
                }
                for (Synapse outputConnection : neuron.outputConnections) {
                    double re = learningRate * (regularization == null ? 0.0 : regularization.compute(outputConnection));
                    double dw = learningRate * ((outputConnection.target.getErrorValue() + re) * neuron.getValue());
                    double dm = momentum * outputConnection.change;

                    outputConnection.weight += (dw + dm);
                    outputConnection.change = dw;
                }
                Bias bias = neuron.bias;
                bias.weight += learningRate * neuron.getErrorValue() * bias.value;
            }

        }


    }


    @Override
    public double networkError() {
        Layer outputLayer = network.layers.get(network.layers.size() - 1);
        double result = 0.0;
        for (Neuron neuron : outputLayer) {
            double neuronError = neuron.getValue() - neuron.getAnticipatedValue();
            result += (neuronError * neuronError);
        }
        result /= outputLayer.size();
        return result;
    }
}
