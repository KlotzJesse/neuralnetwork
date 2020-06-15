package de.jklotz.neuralnetwork.regularization;

public abstract class AbstractRegularization implements Regularization {

    protected double lambda = 1e-5;

    @Override
    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public double getLambda() {
        return lambda;
    }
}

