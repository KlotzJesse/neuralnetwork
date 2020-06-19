package de.jklotz.neuralnetwork.activation.rectifier;

public class ParametricRectifier extends Rectifier {

    public ParametricRectifier() {
        this(0.01f);
    }

    public ParametricRectifier(float leakiness) {
        this.leakiness = leakiness;
    }

    public float getLeakiness() {
        return leakiness;
    }

    public void setLeakiness(float leakiness) {
        this.leakiness = leakiness;
    }
}
