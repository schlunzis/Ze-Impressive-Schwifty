package org.schlunzis.zis.ai;

import org.schlunzis.zis.math.linear.Matrix;

import java.io.Serial;
import java.util.Arrays;

/**
 * A neural network that can be used for genetic algorithms.
 *
 * @author JayPi4c
 * @since 0.0.1
 */
public class GeneticNeuralNetwork extends NeuralNetwork {
    @Serial
    private static final long serialVersionUID = 1L;

    private double mutationRate;

    /**
     * Creates a new neural network with a given learning rate, mutation rate, input nodes, output nodes and hidden nodes.
     *
     * @param inputnodes   The number of input nodes.
     * @param hiddennodes  The number of hidden nodes.
     * @param outputnodes  The number of output nodes.
     * @param learningrate The learning rate of the neural network.
     * @param mutationRate The mutation rate of the neural network.
     * @see NeuralNetwork#NeuralNetwork(double, int, int, int...)
     */
    public GeneticNeuralNetwork(double learningrate, double mutationRate, int inputnodes, int outputnodes,
                                int... hiddennodes) {
        super(learningrate, inputnodes, outputnodes, hiddennodes);
        this.mutationRate = mutationRate;
    }

    /**
     * Calls {@link #mutate(double)} with the mutation rate of the instance.
     *
     * @return mutated instance of calling object
     */
    public GeneticNeuralNetwork mutate() {
        return mutate(this.mutationRate);
    }

    /**
     * A portion (mutation rate) of the weights in the network are randomly reassigned through a mutation of the neural network.
     * <p>
     * This function modifies the calling neural network object. This means that the return value of this function does not necessarily have to be used.
     *
     * @param mutationRate The rate of mutation. The higher the rate, the more weights are mutated.
     * @return this, after mutation
     */
    public GeneticNeuralNetwork mutate(double mutationRate) {

        for (Matrix weight : this.weights) {
            for (int i = 0; i < weight.getColumns(); i++) {
                for (int j = 0; j < weight.getRows(); j++) {
                    if (Math.random() < mutationRate)
                        weight.set(j, i, Math.random() - 0.5);

                }
            }
        }

        for (Matrix bias : this.biases) {
            for (int i = 0; i < bias.getColumns(); i++) {
                for (int j = 0; j < bias.getRows(); j++) {
                    if (Math.random() < mutationRate)
                        bias.set(j, i, Math.random() - 0.5);
                }
            }
        }
        return this;
    }

    /**
     * Via crossover of two neural networks, a random mix of these two networks is created, as in real DNA.
     *
     * @param other The other neural network to crossover with.
     * @param rate  The rate of crossover. The higher the rate, the more weights are crossed over.
     * @return The new neural network, which is a mix of the two input networks.
     */
    public NeuralNetwork crossover(NeuralNetwork other, double rate) {
        // FIXME: implement this
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a deep copy of the neural network.
     *
     * @return A deep copy of the neural network.
     */
    @Override
    public GeneticNeuralNetwork copy() {
        GeneticNeuralNetwork output = new GeneticNeuralNetwork(this.learningrate, this.mutationRate, this.layers[0],
                this.layers[layers.length - 1], Arrays.copyOfRange(layers, 1, layers.length - 1));

        for (int i = 0; i < weights.length; i++)
            output.weights[i] = weights[i].copy();

        for (int i = 0; i < biases.length; i++)
            output.biases[i] = biases[i].copy();

        return output;
    }

}