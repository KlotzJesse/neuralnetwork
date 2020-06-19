package de.jklotz.neuralnetwork.activation.rectifier;

public class ParametricRectifier extends Rectifier {

    public ParametricRectifier() {
        this(0.01);
    }

    public ParametricRectifier(double leakiness) {
        this.leakiness = leakiness;
    }

    public void setLeakiness(double leakiness) {
        this.leakiness = leakiness;
    }

    public double getLeakiness() {
        return leakiness;
    }
}
