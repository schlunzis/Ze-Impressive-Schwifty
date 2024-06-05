package org.schlunzis.zis.ai.nn;

import org.schlunzis.zis.math.linear.Matrix;

import java.awt.*;
import java.awt.image.BufferedImage;
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
    protected double learningrate;

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
     * <pre>
     * NeuralNetwork nn = new NeuralNetwork(0.1, 2, 1, 2, 2);
     * </pre>
     *
     * @param learningrate learning rate of the neural network
     * @param inputnodes   number of nodes in the input layer
     * @param outputnodes  number of nodes in the output layer
     * @param hiddennodes  number of nodes in the hidden layers. Each number represents the number of hidden nodes in the n-th layer.
     */
    public NeuralNetwork(double learningrate, int inputnodes, int outputnodes, int... hiddennodes) {
        this.learningrate = learningrate;

        this.layers = new int[hiddennodes.length + 2];

        // add input nodes to layers
        if (inputnodes < 1)
            throw new IllegalArgumentException("Inputnodes must at least be one!");
        else
            this.layers[0] = inputnodes;

        // add output nodes to layers
        if (outputnodes < 1)
            throw new IllegalArgumentException("Outputnodes must at least be one!");
        else
            this.layers[layers.length - 1] = outputnodes;

        // add hidden nodes to layers
        if (hiddennodes.length == 0)
            throw new IllegalArgumentException("At least one hidden layer must be provided!");
        for (int i = 0; i < hiddennodes.length; i++)
            if (hiddennodes[i] < 1)
                throw new IllegalArgumentException(
                        "All hidden layers must at least have one neuron, which is not true for layer #" + (i + 1));
            else
                layers[i + 1] = hiddennodes[i];

        weights = new Matrix[layers.length - 1];
        biases = new Matrix[layers.length - 1];
        for (int i = 1; i < layers.length; i++) {
            weights[i - 1] = new Matrix(layers[i], layers[i - 1]).randomize(-0.5, 0.5);
            biases[i - 1] = new Matrix(layers[i], 1).randomize(-0.5, 0.5);
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
        gradients.mult(learningrate);

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
            gradients.mult(learningrate);

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
     * creates a Buffered Image representing the neural networks nodes and weights
     * with the specified background.
     *
     * @param background the specified color for the background
     * @param width      the width of the image
     * @param height     the height of the image
     * @return a BufferedImage representing the neural network
     */
    public BufferedImage getSchemeImage(Color background, int width, int height) {
        int maxNodes = 25;
        int radius = 5, diameter = radius * 2;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) img.getGraphics();
        if (background != null) {
            graphics.setColor(background);
            graphics.fillRect(0, 0, width, height);
        }
        // top and bottom will have 10 pixels spare to the border
        int y_min = (int) (0.02 * height);
        int y_max = height - y_min;
        int x_min = (int) (0.02 * width);
        int x_max = width - x_min;

        double x = (x_max - x_min) / (double) (layers.length - 1);

        // draw the weights
        for (int layer = 0; layer < weights.length; layer++) {
            int x_left = (int) (x_min + layer * x);
            int x_right = (int) (x_left + x);
            double y_spacer_left = (y_max - y_min) / Math.min(layers[layer] + 1, maxNodes);
            double y_spacer_right = (y_max - y_min) / Math.min(layers[layer + 1] + 1, maxNodes);
            for (int left_nodes = 0; left_nodes < Math.min(layers[layer], maxNodes); left_nodes++) {
                for (int right_nodes = 0; right_nodes < Math.min(layers[layer + 1], maxNodes); right_nodes++) {
                    float val = (float) weights[layer].getData()[right_nodes][left_nodes];
                    float abs_val = Math.min(Math.abs(val), 1);
                    graphics.setColor(new Color(val < 0 ? abs_val : 0f, val > 0 ? abs_val : 0f, 0f, abs_val));
                    graphics.drawLine(x_left, (int) (y_min + (left_nodes + 1) * y_spacer_left), x_right,
                            (int) (y_min + (right_nodes + 1) * y_spacer_right));
                }
            }
        }

        // draw nodes
        // set node color according to bias
        graphics.setColor(Color.BLUE);
        for (int i = 0; i < layers.length; i++) {
            double y = (y_max - y_min) / Math.min(layers[i] + 1, maxNodes);
            for (int node = 0; node < Math.min(layers[i], maxNodes); node++) {
                if (i > 0) {
                    float val = (float) biases[i - 1].getData()[node][0];
                    float abs_val = Math.min(Math.abs(val), 1);
                    graphics.setColor(new Color(val < 0 ? abs_val : 0f, val > 0 ? abs_val : 0f, 0f, abs_val));
                }
                graphics.fillOval((int) (x_min + i * x - radius), (int) (y_min + (node + 1) * y - radius), diameter,
                        diameter);

            }
        }

        return img;
    }

    /**
     * Create s a scheme image of the neural network with the default size 640x480 and transparent background.
     *
     * @return a default scheme image
     */
    public BufferedImage getSchemeImage() {
        return getSchemeImage(null, 640, 480);
    }

    /**
     * Creates a Scheme Image of the Neural Network with the specified size and a transparent background
     *
     * @param width  width of the image
     * @param height height of the image
     * @return a BufferedImage with the specified parameters
     */
    public BufferedImage getSchemeImage(int width, int height) {
        return getSchemeImage(null, width, height);
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
    public double getLearningrate() {
        return this.learningrate;
    }

    /**
     * Sets the learning rate to the given value
     *
     * @param learningrate new learning rate
     */
    public void setLearningrate(double learningrate) {
        this.learningrate = learningrate;
    }

    /**
     * Creates a copy of the neural network. Changes of to each object will not affect the other neural network.
     *
     * @return independent copy of the neural network
     */
    public NeuralNetwork copy() {
        NeuralNetwork output = new NeuralNetwork(this.learningrate, this.layers[0], this.layers[layers.length - 1],
                Arrays.copyOfRange(layers, 1, layers.length - 1));
        for (int i = 0; i < weights.length; i++)
            output.weights[i] = weights[i].copy();

        for (int i = 0; i < biases.length; i++)
            output.biases[i] = biases[i].copy();

        return output;
    }

}