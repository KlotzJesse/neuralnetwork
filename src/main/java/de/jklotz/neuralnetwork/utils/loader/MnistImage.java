package de.jklotz.neuralnetwork.utils.loader;

/*
FREMD CODE - KEINE EIGENLEISTUNG
Kein wichtiger Bestandteil; Lediglich laden der Bilddaten
*/
public abstract class MnistImage {

    protected int label;

    protected float[] data;

    public MnistImage(int label, byte[] data) {
        this.label = label;
        this.data = new float[data.length];
        convert(data);
    }

    protected abstract void convert(byte[] rawData);

    public int getLabel() {
        return label;
    }

    public float[] getData() {
        return data;
    }
}
