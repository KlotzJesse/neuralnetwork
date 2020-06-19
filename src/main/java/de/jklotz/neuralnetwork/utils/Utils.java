package de.jklotz.neuralnetwork.utils;

import de.jklotz.neuralnetwork.network.NeuronalNetwork;

import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class Utils {

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

    public static NeuronalNetwork deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (NeuronalNetwork) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> getNRandomElements(int n, List<T> list) {
        List<T> subList = new ArrayList<>(n);
        int[] ids = generateUniformBitmap(n, list.size());
        for (int id : ids) {
            subList.add(list.get(id));
        }
        return subList;
    }

    // https://github.com/lemire/Code-used-on-Daniel-Lemire-s-blog/blob/master/2013/08/14/java/UniformDistinct.java
    private static int[] generateUniformBitmap(int num, int max) {
        if (num > max) {
            throw new Error("num > max");
        }
        int[] ans = new int[num];
        if (num == max) {
            for (int k = 0; k < num; ++k) {
                ans[k] = k;
            }
            return ans;
        }
        BitSet bs = new BitSet(max);
        int cardinality = 0;
        Random random = new Random();
        while (cardinality < num) {
            int v = random.nextInt(max);
            if (!bs.get(v)) {
                bs.set(v);
                cardinality += 1;
            }
        }
        int pos = 0;
        for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i + 1)) {
            ans[pos] = i;
            pos += 1;
        }
        return ans;
    }

}
