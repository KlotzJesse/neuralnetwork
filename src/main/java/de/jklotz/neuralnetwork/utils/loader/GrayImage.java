package de.jklotz.neuralnetwork.utils.loader;

import java.util.function.BiFunction;

/*
FREMD CODE - KEINE EIGENLEISTUNG
Kein wichtiger Bestandteil; Lediglich laden der Bilddaten
*/
public class GrayImage extends MnistImage {

    public GrayImage(int label, byte[] data) {
        super(label, data);
    }

    @Override
    protected void convert(byte[] rawData) {
        for (int i = 0; i < rawData.length; i++) {
            int value = rawData[i] & 0xFF;
            value = Math.min(255, value);
            value = Math.max(0, value);
            data[i] = value / 255.0f;
        }
    }

    public static class Factory implements BiFunction<Integer, byte[], MnistImage> {
        @Override
        public MnistImage apply(Integer label, byte[] rawData) {
            return new GrayImage(label, rawData);
        }
    }
}
