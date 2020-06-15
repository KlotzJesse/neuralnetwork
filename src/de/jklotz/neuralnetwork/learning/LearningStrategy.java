package de.jklotz.neuralnetwork.learning;

public interface LearningStrategy {

    Strategy BACK_PROPAGATION = new BackPropagation();

    void learn();

    double networkError();

}
