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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrainMNIST {

    private static final Logger logger = LogManager.getLogger(TrainMNIST.class);

    public static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);
    public static ExecutorCompletionService executorCompletionService = new ExecutorCompletionService(executor);

    public static List<MnistImage> trainImages = null;

    public static int epochCount = 0;

    public static NeuronalNetwork neuralNetwork;

    public static int count = 0;

    public static void main(String[] args) {
        float learningRate = 0.2f;
        float momentum = 0.9f;
        float meanNetworkError = 0.0000005f;


        int numberOfInputNeurons = 28 * 28;
        int numberOfOutputNeurons = 10;
        int numberOfHiddenNeurons = (int) Math.round(Math.sqrt(numberOfInputNeurons * numberOfOutputNeurons));

        logger.info("Building Neural Network...");

        neuralNetwork = new NeuronalNetworkBuilder()
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


        logger.info("");
        logger.info("Loading Train Images...");
        MNISTImageLoadingService dilsTrainData = new MNISTImageLoadingService(
                "./train/train-labels-idx1-ubyte.dat",
                "./train/train-images-idx3-ubyte.dat"
        );


        try {
            trainImages = dilsTrainData.loadMNISTImages(new GrayImage.Factory());
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("");
        logger.info("Start Learning...");


        float networkError = 1;
        do {
            assert trainImages != null;

            Map<Float, NeuronalNetwork> networks = new HashMap<>();

            for (int a = 0; a < 6; a++) {
                executorCompletionService.submit(() -> {

                    NeuronalNetwork network = Utils.deepClone(neuralNetwork);
                    float averageNetworkError;

                    Strategy backPropagation = new StrategyBuilder(new BackPropagation())
                            .learningRate(learningRate)
                            .momentum(momentum)
                            .regularization(new Dropout())
                            .neuronalNetwork(network)
                            .build();

                    float epochError = 0;
                    int epochIterations = 0;

                    for (MnistImage mnistImage : Utils.getNRandomElements(10000, trainImages)) {

                        network.input(mnistImage.getData());

                        int outputLayerSize = network.layers.get(network.layers.size() - 1).size();
                        float[] out = new float[outputLayerSize];
                        for (int i = 0; i < outputLayerSize; i++) {
                            out[i] = 0;
                        }
                        out[mnistImage.getLabel()] = 1.0f;
                        network.setAnticipatedOutput(out);
                        backPropagation.learn();
                        epochError += backPropagation.networkError();
                        epochIterations++;

                    }


                    averageNetworkError = epochError / (float) epochIterations;
                    logger.info("");

                    logger.info("Average Network Error: " + averageNetworkError + " (" + ((int) epochError) + " / " + epochIterations + ")");

                    networks.put(averageNetworkError, network);

                    count++;
                    return null;
                });
            }

            try {
                for (int i = 0; i < 6; i++) {
                    executorCompletionService.take();
                }
            } catch (InterruptedException ignored) {

            }

            logger.info("");
            logger.info("Finished Epoch-" + epochCount);

            Map.Entry<Float, NeuronalNetwork> entry = networks.entrySet().stream().min(Comparator.comparingDouble(Map.Entry::getKey)).get();

            logger.info("Best Network Erorr: " + entry.getKey());

            if (entry.getKey() > networkError) {
                logger.info("Remaining on OLD Network, because there's no improvement");
                logger.info("Network Error: " + networkError);
            } else {
                networkError = entry.getKey();
                neuralNetwork = entry.getValue();
            }

            /*try {
                logger.info("Saving...");
                Utils.serialize(neuralNetwork, "./result/NeuronalNetwork_E" + epochCount + ".dat");
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            epochCount++;
            count = 0;
        } while (networkError > meanNetworkError);
    }
}
