package org.schlunzis.zis.math.linear;

/**
 * A Vector is a simplified version of a matrix, which only has one column.
 *
 * @author JayPi4c
 * @version 0.0.1
 */
public class Vector {

    private double data[];
    private int rows;

    /**
     * Creates an empty vector with the given size.
     *
     * @param rows size of the vector
     */
    public Vector(int rows) {
        this.rows = rows;
        this.data = new double[rows];
    }

    /**
     * Creates a vector with the given data.
     *
     * @param data data of the vector
     */
    public Vector(double[] data) {
        this.rows = data.length;
        this.data = new double[rows];
        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    /**
     * Converts the vector back to an array.
     *
     * @return vector data in an array
     */
    public double[] toArray() {
        double[] d = new double[rows];
        System.arraycopy(data, 0, d, 0, data.length);
        return d;
    }

    /**
     * Creates an independent  copy of the vector.
     *
     * @return a copy of the vector
     */
    public Vector copy() {
        return new Vector(this.data);
    }

    /**
     * Prints the vector to the console.
     */
    public void print() {
        System.out.println("-------------------------------------------------");
        for (double d : this.data) System.out.print(d + "\t");
        System.out.println(")^T");
        System.out.println("-------------------------------------------------");
    }

    /**
     * Returns the data of the vector. This is a reference to the data, so changes to the data will affect the vector.
     *
     * @return data of the vector
     */
    public double[] getData() {
        return this.data;
    }

    /**
     * Returns the value at the given index.
     *
     * @param index index of the value
     * @return value at the index
     * @throws IndexOutOfBoundsException if the index is not in the data
     */
    public double get(int index) {
        if (index >= data.length || index < 0)
            throw new IndexOutOfBoundsException("Der index ist nicht in der data vorhanden!");
        return this.data[index];
    }

    /**
     * Sets the value at the given index.
     *
     * @param index index of the value
     * @param value new value
     * @throws IndexOutOfBoundsException if the index is not in the data
     */
    public void set(int index, double value) {
        if (index >= data.length || index < 0)
            throw new IndexOutOfBoundsException("Der index ist nicht in der data vorhanden!");
        this.data[index] = value;
    }
}