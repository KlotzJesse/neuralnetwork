package de.jklotz.neuralnetwork.regularization;

public abstract class AbstractRegularization implements Regularization {

    protected float lambda = 1e-5f;

    @Override
    public float getLambda() {
        return lambda;
    }

    @Override
    public void setLambda(float lambda) {
        this.lambda = lambda;
    }
}

