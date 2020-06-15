package de.jklotz.neuralnetwork;

import de.jklotz.neuralnetwork.activation.ActivationFunction;
import de.jklotz.neuralnetwork.initializer.Initializer;
import de.jklotz.neuralnetwork.learning.BackPropagation;
import de.jklotz.neuralnetwork.learning.Strategy;
import de.jklotz.neuralnetwork.learning.StrategyBuilder;
import de.jklotz.neuralnetwork.network.Layer;
import de.jklotz.neuralnetwork.network.Neuron;
import de.jklotz.neuralnetwork.network.NeuronalNetwork;
import de.jklotz.neuralnetwork.network.NeuronalNetworkBuilder;
import de.jklotz.neuralnetwork.regularization.Dropout;
import de.jklotz.neuralnetwork.utils.Utils;
import de.jklotz.neuralnetwork.utils.loader.GrayImage;
import de.jklotz.neuralnetwork.utils.loader.MNISTImageLoadingService;
import de.jklotz.neuralnetwork.utils.loader.MnistImage;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class TrainMNIST {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        double learningRate = 0.2;
        double momentum = 0.5;
        double meanNetworkError = 0.0000005;

        int epochCount = 0;

        int numberOfInputNeurons = 28 * 28;
        int numberOfOutputNeurons = 10;
        int numberOfHiddenNeurons = (int) Math.round(Math.sqrt(numberOfInputNeurons * numberOfOutputNeurons));

        logger.info("Building Neural Network...");

        NeuronalNetwork neuralNetwork = new NeuronalNetworkBuilder()
                .layer(numberOfInputNeurons, ActivationFunction.IDENTITY)
                .layer(numberOfHiddenNeurons, ActivationFunction.SWISH_RECTIFIER)
                .layer(numberOfHiddenNeurons / 2, ActivationFunction.EXPONENTIAL_RECTIFIER)
                .layer(numberOfOutputNeurons, ActivationFunction.SOFTMAX)
                .initializer(Initializer.XAVIER_INITIALIZER)
                .build();

        logger.info("Layers: " + neuralNetwork.layers.size());

        int neurons = 0;
        int synapses = 0;

        for (Layer layer : neuralNetwork.layers) {
            neurons += layer.size();
            for (Neuron neuron : layer) {
                synapses += neuron.outputConnections.size();
            }
        }

        logger.info("Neurons: " + neurons);
        logger.info("Synapses: " + synapses);


        Strategy backPropagation = new StrategyBuilder(new BackPropagation())
                .learningRate(learningRate)
                .momentum(momentum)
                .regularization(new Dropout())
                .neuronalNetwork(neuralNetwork)
                .build();

        logger.info("");
        logger.info("Loading Train Images...");
        MNISTImageLoadingService dilsTrainData = new MNISTImageLoadingService(
                "./train/train-labels-idx1-ubyte.dat",
                "./train/train-images-idx3-ubyte.dat"
        );

        List<MnistImage> trainImages = null;

        try {
            trainImages = dilsTrainData.loadMNISTImages(new GrayImage.Factory());
            Collections.shuffle(trainImages);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("");
        logger.info("Start Learning...");

        int epochIterations = 0;
        double epochError = 0.0;
        double averageNetworkError;
        do {
            assert trainImages != null;
            Collections.shuffle(trainImages);
            for (MnistImage mnistImage : trainImages) {
                neuralNetwork.input(mnistImage.getData());

                int outputLayerSize = neuralNetwork.layers.get(neuralNetwork.layers.size() - 1).size();
                double[] out = new double[outputLayerSize];
                for (int i = 0; i < outputLayerSize; i++) {
                    out[i] = 0.0;
                }
                out[mnistImage.getLabel()] = 1.0;
                neuralNetwork.setAnticipatedOutput(out);
                backPropagation.learn();
                epochError += backPropagation.networkError();
                epochIterations++;
            }
            averageNetworkError = epochError / (double) epochIterations;
            logger.info("");
            logger.info("Finished Epoch-" + epochCount);
            logger.info("Average Network Error: " + averageNetworkError + " (" + ((int) epochError) + " / " + epochIterations + ")");

            try {
                logger.info("Saving...");
                Utils.serialize(neuralNetwork, "./result/NeuronalNetwork_E" + epochCount + ".dat");
            } catch (IOException e) {
                e.printStackTrace();
            }

            epochError = 0.0;
            epochIterations = 0;
            epochCount++;
        } while (averageNetworkError > meanNetworkError);
    }
}
