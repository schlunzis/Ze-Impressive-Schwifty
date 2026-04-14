package org.schlunzis.zis.ai.nn;

import org.junit.jupiter.api.Test;
import org.schlunzis.zis.math.linear.Matrix;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NeuralNetworkTest {

    final Matrix[][] xOrData = new Matrix[][]{{
            new Matrix(new double[][]{{0, 0}}), new Matrix(new double[][]{{0}})},
            {new Matrix(new double[][]{{0, 1}}), new Matrix(new double[][]{{1}})},
            {new Matrix(new double[][]{{1, 0}}), new Matrix(new double[][]{{1}})},
            {new Matrix(new double[][]{{1, 1}}), new Matrix(new double[][]{{0}})
            }};

    @Test
    void testXOr() {
        // Create a neural network with 2 input nodes, 2 hidden nodes and 1 output node
        NeuralNetwork nn = new NeuralNetwork(42, 0.01, 2, 1, 2);
        nn.setActivationFunction(NeuralNetwork.tanh);

        // Train the neural network
        int epochs = 5000;
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (Matrix[] xOrDatum : xOrData) {
                nn.train(xOrDatum[0], xOrDatum[1]);
            }
        }

        // Test the neural network
        for (Matrix[] xOrDatum : xOrData) {
            Matrix input = xOrDatum[0];
            double predicted = nn.query(input).get(0, 0);
            double expected = xOrDatum[1].get(0, 0);
            assertTrue(Math.abs(expected - predicted) < 0.2, "Input: " + input.get(0, 0) + ", " + input.get(0, 1) +
                    " | Expected: " + expected + " | Predicted: " + predicted);
        }
    }

    @Test
    void testLoad() throws IOException, ClassNotFoundException {
        NeuralNetwork nn = NeuralNetwork.deserialize(new File("src/test/resources/xor.nn"));
        for (Matrix[] xOrDatum : xOrData) {
            Matrix input = xOrDatum[0];
            double predicted = nn.query(input).get(0, 0);
            double expected = xOrDatum[1].get(0, 0);
            assertTrue(Math.abs(expected - predicted) < 0.2, "Input: " + input.get(0, 0) + ", " + input.get(0, 1) +
                    " | Expected: " + expected + " | Predicted: " + predicted);
        }
    }

    @Test
    void testSave() throws IOException, ClassNotFoundException {
        NeuralNetwork nn = new NeuralNetwork(42, 0.01, 2, 1, 2);
        nn.setActivationFunction(NeuralNetwork.tanh);
        // Train the neural network
        int epochs = 5000;
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (Matrix[] xOrDatum : xOrData) {
                nn.train(xOrDatum[0], xOrDatum[1]);
            }
        }
        // Save the neural network
        File file = new File("/tmp/xor_saved.nn");
        nn.serialize(file);
    }


}
