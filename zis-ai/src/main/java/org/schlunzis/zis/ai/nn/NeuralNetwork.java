package org.schlunzis.zis.ai.nn;

import org.schlunzis.zis.math.linear.Matrix;

import java.io.*;
import java.util.Arrays;

/**
 * This class represents a simple neural network that can be trained using the backpropagation algorithm.
 *
 * @author JayPi4c
 * @since 0.0.1
 */
public class NeuralNetwork implements Serializable {

    public static final ActivationFunction sigmoid = new ActivationFunction() {

        @Override
        public double deactivate(double y) {
            return y * (1 - y);
        }

        @Override
        public double activate(double x) {
            return (1 / (1 + Math.pow(Math.E, -x)));
        }
    };
    public static final ActivationFunction tanh = new ActivationFunction() {

        @Override
        public double deactivate(double y) {
            return 1 - (y * y);
        }

        @Override
        public double activate(double x) {
            return Math.tanh(x);
        }
    };

    public static final ActivationFunction reLU = new ActivationFunction() {

        @Override
        public double deactivate(double y) {
            return y > 0 ? 1 : 0;
        }

        @Override
        public double activate(double x) {
            return Math.max(0, x);
        }
    };

    @Serial
    private static final long serialVersionUID = 1L;
    protected ActivationFunction actFunc = sigmoid;

    /**
     * This array includes the number of nodes for each layer<br>
     * - layers[0] -> number of nodes in input layer<br>
     * - layers[nodes.length-1] -> number of nodes in output layer<br>
     * - layers[n] -> number of nodes in nth hidden layer
     */
    protected int[] layers;
    protected double learningRate;

    /**
     * This array includes the weight matrices for each layer<br>
     * - weights[0] -> weights for input hidden#1 - weights[weights.length-1]<br>
     * - weights for hidden->output
     */
    protected Matrix[] weights;

    /**
     * This array includes the bias matrices for each layer<br>
     * -> biases[0] -> biases for layer hidden#1<br>
     * -> biases[biases.length-1] -> biases for output layer
     */
    protected Matrix[] biases;

    /**
     * Constructor for creating a neural network with the defined parameters.
     * <p>
     * Example usage to create a neural network with 2 input nodes, 2 nodes in the first hidden layer, 2 nodes in the second hidden layer and 1 node for the output and a learningrate of 0.1:
     * <pre><code>
     * NeuralNetwork nn = new NeuralNetwork(0.1, 2, 1, 2, 2);
     * </code></pre>
     *
     * @param learningRate learning rate of the neural network
     * @param inputNodes   number of nodes in the input layer
     * @param outputNodes  number of nodes in the output layer
     * @param hiddenNodes  number of nodes in the hidden layers. Each number represents the number of hidden nodes in the n-th layer.
     */
    public NeuralNetwork(double learningRate, int inputNodes, int outputNodes, int... hiddenNodes) {
        this.learningRate = learningRate;

        this.layers = new int[hiddenNodes.length + 2];

        // add input nodes to layers
        if (inputNodes < 1)
            throw new IllegalArgumentException("Input nodes must at least be one!");
        else
            this.layers[0] = inputNodes;

        // add output nodes to layers
        if (outputNodes < 1)
            throw new IllegalArgumentException("Output nodes must at least be one!");
        else
            this.layers[layers.length - 1] = outputNodes;

        // add hidden nodes to layers
        if (hiddenNodes.length == 0)
            throw new IllegalArgumentException("At least one hidden layer must be provided!");
        for (int i = 0; i < hiddenNodes.length; i++)
            if (hiddenNodes[i] < 1)
                throw new IllegalArgumentException(
                        "All hidden layers must at least have one neuron, which is not true for layer #" + (i + 1));
            else
                layers[i + 1] = hiddenNodes[i];

        weights = new Matrix[layers.length - 1];
        biases = new Matrix[layers.length - 1];
        for (int i = 1; i < layers.length; i++) {
            weights[i - 1] = new Matrix(layers[i], layers[i - 1]).randomize(-0.5, 0.5);
            biases[i - 1] = new Matrix(layers[i], 1).randomize(-0.5, 0.5);
        }

    }

    /**
     * Constructor for creating a neural network with the defined parameters and a seed for the random number generator of the weights and biases.
     *
     * @param seed         seed for the random number generator
     * @param learningRate learning rate of the neural network
     * @param inputNodes   number of nodes in the input layer
     * @param outputNodes  number of nodes in the output layer
     * @param hiddenNodes  number of nodes in the hidden layers. Each number represents the number of hidden nodes in the n-th layer.
     * @see #NeuralNetwork(double, int, int, int...)
     */
    public NeuralNetwork(long seed, double learningRate, int inputNodes, int outputNodes, int... hiddenNodes) {
        this(learningRate, inputNodes, outputNodes, hiddenNodes);
        // re-initialize weights and biases with seed
        for (int i = 1; i < layers.length; i++) {
            weights[i - 1] = new Matrix(layers[i], layers[i - 1]).setSeed(seed).randomize(-0.5, 0.5);
            biases[i - 1] = new Matrix(layers[i], 1).setSeed(seed).randomize(-0.5, 0.5);
        }
    }


    /**
     * Serializes a given neural network object to a file. The file will be saved in the same directory as the program. Using {@link #deserialize(File)} the object can be loaded again.
     *
     * @param nn the neural network object to serialize
     * @throws IOException if an error occurs while writing the file
     * @see #deserialize(File)
     */
    public static void serialize(NeuralNetwork nn) throws IOException {
        String absolutePath = new File(".").getAbsolutePath();
        File file = new File(absolutePath);
        absolutePath = file.getParentFile().toString();
        FileOutputStream fos = new FileOutputStream(absolutePath + "/NeuralNetwork.nn");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(nn);
        oos.close();
    }

    /**
     * Deserializes a neural network object from a file. The file must be created by {@link #serialize(NeuralNetwork)}.
     *
     * @param f the file to deserialize
     * @return the deserialized neural network object
     * @throws IOException            if an error occurs while reading the file
     * @throws ClassNotFoundException if the class of the object in the file cannot be found
     * @see #serialize(NeuralNetwork)
     */
    public static NeuralNetwork deserialize(File f) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        NeuralNetwork output = (NeuralNetwork) ois.readObject();
        ois.close();
        return output;
    }


    /**
     * Queries the neural network with the given inputs. The output of the neural network is the estimation of the neural network.
     *
     * @param inputs_list the inputs into the neural network
     * @return the networks guess
     */
    public Matrix query(Matrix inputs_list) {

        Matrix matrix = Matrix.transpose(inputs_list);
        for (int i = 0; i < weights.length; i++) {
            matrix = Matrix.matmul(weights[i], matrix);
            matrix.add(biases[i]);
            matrix.map((d, r, c) -> actFunc.activate(d));
        }
        return matrix;
    }

    /**
     * Convenient method to query the neural network with double arrays. The output of the neural network is the estimation of the neural network.
     *
     * @param inputs_list the inputs into the neural network
     * @return the networks guess
     * @see #query(Matrix)
     */
    public Matrix query(double[][] inputs_list) {
        return this.query(new Matrix(inputs_list));
    }


    /**
     * Trains the network with the given inputs and outputs. The inputs and outputs must be matrices. The training is done by the backpropagation algorithm.
     *
     * @param inputs_list  inputs in the neural network
     * @param targets_list targets of the neural network
     */
    public void train(Matrix inputs_list, Matrix targets_list) {

        Matrix[] results = new Matrix[weights.length + 1];
        results[0] = Matrix.transpose(inputs_list);
        for (int i = 0; i < weights.length; i++) {
            results[i + 1] = Matrix.matmul(weights[i], results[i]);
            results[i + 1].add(biases[i]);
            results[i + 1].map((d, r, c) -> actFunc.activate(d));
        }

        Matrix error = Matrix.sub(Matrix.transpose(targets_list), results[results.length - 1]);
        Matrix gradients = results[results.length - 1].map((d, r, c) -> actFunc.deactivate(d));
        gradients.hadamard(error);
        gradients.mult(learningRate);

        // calculate deltas
        Matrix prev_T = Matrix.transpose(results[results.length - 2]);
        Matrix weight_deltas = Matrix.matmul(gradients, prev_T);

        // Adjust the weights by deltas
        weights[results.length - 2].add(weight_deltas);
        // Adjust the bias by its deltas (which is just the gradients)
        biases[results.length - 2].add(gradients);

        for (int i = results.length - 2; i >= 1; i--) {
            // calculate error
            error = Matrix.matmul(Matrix.transpose(weights[i]), error);

            gradients = results[i].map((d, r, c) -> actFunc.deactivate(d));
            gradients.hadamard(error);
            gradients.mult(learningRate);

            // calculate deltas
            prev_T = Matrix.transpose(results[i - 1]);
            weight_deltas = Matrix.matmul(gradients, prev_T);

            // Adjust the weights by deltas
            weights[i - 1].add(weight_deltas);
            // Adjust the bias by its deltas (which is just the gradients)
            biases[i - 1].add(gradients);
        }
    }

    /**
     * This method trains the neural network with the given inputs and targets. It is a convenient method that allows the
     * direct input of double arrays.
     *
     * @param inputs_list  inputs in the neural network
     * @param targets_list targets of the neural network
     * @see #train(Matrix, Matrix)
     */
    public void train(double[][] inputs_list, double[][] targets_list) {
        this.train(new Matrix(inputs_list), new Matrix(targets_list));
    }

    /**
     * Setter for the activation function of the neural network
     *
     * @param actFunc new activation function
     */
    public void setActivationFunction(ActivationFunction actFunc) {
        this.actFunc = actFunc;
    }

    /**
     * Returns the current learning rate of the neural network
     *
     * @return current learning rate
     */
    public double getLearningRate() {
        return this.learningRate;
    }

    /**
     * Sets the learning rate to the given value
     *
     * @param learningRate new learning rate
     */
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }


    /**
     * Getter for the weights of the neural network.
     *
     * @return the weights of the neural network
     */
    public Matrix[] getWeights() {
        return weights;
    }

    /**
     * Getter for the biases of the neural network.
     *
     * @return the biases of the neural network
     */
    public Matrix[] getBiases() {
        return biases;
    }

    /**
     * Setter for the weights of the neural network.
     *
     * @param weights the new weights of the neural network
     */
    public void setWeights(Matrix[] weights) {
        if (weights.length != this.weights.length)
            throw new IllegalArgumentException("The provided weights do not match the neural networks architecture!");
        this.weights = weights;
    }

    /**
     * Setter for the biases of the neural network.
     *
     * @param biases the new biases of the neural network
     */
    public void setBiases(Matrix[] biases) {
        if (biases.length != this.biases.length)
            throw new IllegalArgumentException("The provided biases do not match the neural networks architecture!");
        this.biases = biases;
    }

    /**
     * Creates a copy of the neural network. Changes of to each object will not affect the other neural network.
     *
     * @return independent copy of the neural network
     */
    public NeuralNetwork copy() {
        NeuralNetwork output = new NeuralNetwork(this.learningRate, this.layers[0], this.layers[layers.length - 1],
                Arrays.copyOfRange(layers, 1, layers.length - 1));
        for (int i = 0; i < weights.length; i++)
            output.weights[i] = weights[i].copy();

        for (int i = 0; i < biases.length; i++)
            output.biases[i] = biases[i].copy();

        return output;
    }

    /**
     * Sets the seed for the random number generator of the weights and biases.
     *
     * @param seed the seed to set
     */
    public void setSeed(long seed) {
        for (Matrix weight : this.weights) {
            weight.setSeed(seed);
        }
        for (Matrix bias : this.biases) {
            bias.setSeed(seed);
        }
    }

}