

<p align="center"><img src="docs/images/logo/neuralnetwork-logo-color-trim.png?raw=true" alt="neuralnetwork logo"></p>

<h3 align="center">
	neuralnetwork 
</h3>

<p align="center">
  A simple, unfinished java implementation of a rudimentary artificial intelligence framework, trained using the MNIST data set for the classification of numbers.
</p>

## What is Artifical Intelligence

> For the present purpose the artificial intelligence problem is taken to be that of making a machine behave in ways that would be called intelligent if a human were so behaving.
>
> -- John McCarthy, 1955, known as the father of AI <br>
> &nbsp;&nbsp;&nbsp; firstly coined the term "artificial intelligence"  (aka [A PROPOSAL FOR THE DARTMOUTH SUMMER RESEARCH PROJECT ON ARTIFICIAL INTELLIGENCE](http://www-formal.stanford.edu/jmc/history/dartmouth/dartmouth.html))

![CircleCI](https://img.shields.io/circleci/build/github/KlotzJesse/neuralnetwork?style=flat-square)
![Docker Image Size (latest by date)](https://img.shields.io/docker/image-size/jesseklotz2306/neuralnetwork?style=flat-square)
![Libraries.io dependency status for GitHub repo](https://img.shields.io/librariesio/github/KlotzJesse/neuralnetwork?style=flat-square)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/KlotzJesse/neuralnetwork?style=flat-square)
![GitHub repo size](https://img.shields.io/github/repo-size/KlotzJesse/neuralnetwork?style=flat-square)
![GitHub](https://img.shields.io/github/license/KlotzJesse/neuralnetwork?style=flat-square)

## Run MNIST-Training example network

### Prerequisities


In order to run this container you'll need docker installed.

* [Windows](https://docs.docker.com/windows/started)
* [OS X](https://docs.docker.com/mac/started/)
* [Linux](https://docs.docker.com/linux/started/)

### Install

Pull `jesseklotz2306/neuralnetwork` from the Docker repository:

    docker pull jesseklotz2306/neuralnetwork

### Run

Run the image:

    docker run jesseklotz2306/neuralnetwork

## Usage

### Setup

Two hidden layer configuration (add as many as you want). Using the Swish activation function and an exponential rectifier as activation function. Outputs a probability distribution in relation to the plausibility of the input value.

Input Neurons: 720, 
Hidden Layer 1: 28, 
Hidden Layer 2: 14, 
Output Neurons: 10

Every neuron is by default densely connected to each other (synapses) and initialized using the Xavier Initializer.

```java
NeuronalNetwork neuralNetwork = new NeuronalNetworkBuilder()
                .layer(720, ActivationFunction.IDENTITY)
                .layer(28, ActivationFunction.SWISH_RECTIFIER)
                .layer(14, ActivationFunction.EXPONENTIAL_RECTIFIER)
                .layer(10, ActivationFunction.SOFTMAX)
                .initializer(Initializer.XAVIER_INITIALIZER)
                .build();
```

The network is capable of carrying out the learning process using various learning techniques. At the moment just Backpropagation is implemented.


```java
Strategy backPropagation = new StrategyBuilder(new BackPropagation())
                            .learningRate(0.2f)
                            .momentum(0.9f)
                            .regularization(new Dropout())
                            .neuronalNetwork(network)
                            .build();
```

### Training

```java
/*Set Input Data*/
network.input(/* Input Data [784-Dimensions] */);

int outputLayerSize = network.layers.getLast().size();

/*Initialize output matrix for expected size*/
float[] out = new float[outputLayerSize];
for (int i = 0; i < outputLayerSize; i++) {
    out[i] = 0;
}

/*Set winning Output Neuron, e.g. number 3*/
out[3] = 1.0f;

/*Set expected Output Matrix*/
network.setAnticipatedOutput(out);

/*Let network train*/
backPropagation.learn();
```

# License

<a href="https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3)" target="_blank">![GitHub](https://img.shields.io/github/license/KlotzJesse/neuralnetwork?style=for-the-badge)</a>
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIwNjIzODcwMywtMjExMjk2MzE4NV19
-->