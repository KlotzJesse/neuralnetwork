package de.jklotz.neuralnetwork.utils;

import de.jklotz.neuralnetwork.network.NeuronalNetwork;

import java.io.*;
import java.util.Random;

public class Utils {

    private static final Random RND = new Random();

    public static void serialize(NeuronalNetwork neuralNetwork, String outputFile) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {
            oos.writeObject(neuralNetwork);
            oos.flush();
        }
    }

    public static NeuronalNetwork deserialize(String inputFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inputFile))) {
            return (NeuronalNetwork) ois.readObject();
        }
    }
}
