package org.schlunzis.zis.math.linear;

import java.io.PrintStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a Matrix and offers the possibility to work with it.
 *
 * @author JayPi4c
 * @since 0.0.1
 */
public class Matrix implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private double[][] data;
    private int rows, cols;

    /**
     * Creates a Matrix with specified number of rows and columns, initialized with
     * default (0) values.
     *
     * @param rows Number of rows of the new Matrix
     * @param cols Number of columns of the new Matrix
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[this.rows][this.cols];
    }

    /**
     * Creates a Matrix from a given two-dimensional Array
     *
     * @param data two-dimensional Array to create the Matrix from
     */
    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = Arrays.stream(data).map(double[]::clone).toArray(double[][]::new);
    }

    /**
     * @param matrix the matrix to transpose
     * @return the transposed matrix
     */
    public static Matrix transpose(Matrix matrix) {
        Matrix newMatrix = new Matrix(matrix.cols, matrix.rows);
        for (int row = 0; row < matrix.rows; row++)
            for (int col = 0; col < matrix.cols; col++)
                newMatrix.data[col][row] = matrix.data[row][col];
        return newMatrix;
    }

    /**
     * Calculates the product of two matrices.
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return the product of both matrices
     */
    public static Matrix matmul(Matrix a, Matrix b) {
        if (a.cols != b.rows)
            throw new IllegalArgumentException("A's cols and B's rows must match!");

        double[][] newData = new double[a.rows][b.cols];
        for (int row = 0; row < a.rows; row++)
            for (int col = 0; col < b.cols; col++)
                for (int j = 0; j < a.cols; j++)
                    newData[row][col] += a.data[row][j] * b.data[j][col];

        return new Matrix(newData);
    }

    /**
     * Subtracts the second matrix from the first one.
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return the difference of both matrices
     */
    public static Matrix sub(Matrix a, Matrix b) {
        if (a.cols != b.cols || a.rows != b.rows)
            throw new IllegalArgumentException("rows and columns must match!");
        Matrix newMatrix = new Matrix(a.rows, a.cols);
        for (int row = 0; row < a.rows; row++)
            for (int col = 0; col < a.cols; col++)
                newMatrix.data[row][col] = a.data[row][col] - b.data[row][col];
        return newMatrix;
    }

    /**
     * Creates a matrix with the difference d - entry in each entry.
     *
     * @param d the value to subtract from
     * @param m the matrix to subtract
     * @return the difference of the matrix and the value
     */
    public static Matrix sub(double d, Matrix m) {
        Matrix newMatrix = new Matrix(m.rows, m.cols);
        for (int row = 0; row < newMatrix.rows; row++)
            for (int col = 0; col < newMatrix.cols; col++)
                newMatrix.data[row][col] = d - m.data[row][col];
        return newMatrix;
    }

    /**
     * Adds two matrices.
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return the sum of both matrices
     */
    public static Matrix add(Matrix a, Matrix b) {
        if (a.cols != b.cols || a.rows != b.rows)
            throw new IllegalArgumentException("rows and columns must match!");

        Matrix newMatrix = new Matrix(a.rows, a.cols);
        for (int row = 0; row < a.rows; row++)
            for (int col = 0; col < a.cols; col++)
                newMatrix.data[row][col] = a.data[row][col] + b.data[row][col];
        return newMatrix;
    }

    /**
     * Creates a matrix multiplied by a scalar.
     *
     * @param m   the matrix to multiply
     * @param scl the scalar to multiply with
     * @return the scaled matrix
     */
    public static Matrix mult(Matrix m, double scl) {
        Matrix newMatrix = new Matrix(m.rows, m.cols);
        for (int row = 0; row < newMatrix.rows; row++)
            for (int col = 0; col < newMatrix.cols; col++)
                newMatrix.data[row][col] = scl * m.data[row][col];
        return newMatrix;
    }

    /**
     * Calculates the hadamard product
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return the hadamard product of both matrices
     */
    public static Matrix hadamard(Matrix a, Matrix b) {
        if (a.cols != b.cols || a.rows != b.rows)
            throw new IllegalArgumentException("rows and columns must match!");
        Matrix newMatrix = new Matrix(a.rows, b.cols);
        for (int row = 0; row < newMatrix.rows; row++)
            for (int col = 0; col < newMatrix.cols; col++)
                newMatrix.data[row][col] = a.data[row][col] * b.data[row][col];
        return newMatrix;
    }

    /**
     * Checks for equality of two matrices.
     *
     * @param m1 the first matrix
     * @param m2 the second matrix
     * @return true if the matrices are equal, false otherwise
     */
    public static boolean equals(Matrix m1, Matrix m2) {
        if (m1.cols != m2.cols || m1.rows != m2.rows)
            return false;
        for (int i = 0; i < m1.rows; i++)
            for (int j = 0; j < m2.cols; j++)
                if (m1.get(i, j) != m2.get(i, j))
                    return false;
        return true;
    }

    /**
     * comes from https://stackoverflow.com/a/49251497
     *
     * @param matrix
     * @return
     */
    public static double[][] inverse(double[][] matrix) {
        double[][] inverse = new double[matrix.length][matrix.length];

        // minors and cofactors
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                inverse[i][j] = Math.pow(-1, i + j) * new Matrix(minor(matrix, i, j)).det();

        // adjugate and determinant
        double det = 1.0 / new Matrix(matrix).det();
        for (int i = 0; i < inverse.length; i++) {
            for (int j = 0; j <= i; j++) {
                double temp = inverse[i][j];
                inverse[i][j] = inverse[j][i] * det;
                inverse[j][i] = temp * det;
            }
        }

        return inverse;
    }

    /**
     * comes from <a href="https://stackoverflow.com/a/49251497">Stackoverflow</a>
     *
     * @param matrix
     * @param row
     * @param column
     * @return
     */
    private static double[][] minor(double[][] matrix, int row, int column) {
        double[][] minor = new double[matrix.length - 1][matrix.length - 1];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; i != row && j < matrix[i].length; j++)
                if (j != column)
                    minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
        return minor;
    }

    /**
     * Transposes the Matrix.
     *
     * @return this, after transposing
     */
    public Matrix transpose() {
        Matrix m = new Matrix(this.cols, this.rows);
        for (int row = 0; row < this.rows; row++)
            for (int col = 0; col < this.cols; col++)
                m.data[col][row] = this.data[row][col];
        this.data = m.data;
        this.rows = m.rows;
        this.cols = m.cols;

        return this;
    }

    /**
     * Creates a two-dimensional array from the calling matrix.
     *
     * @return two-dimensional array from the matrix
     */
    public double[][] toArray() {
        return Arrays.stream(data).map(double[]::clone).toArray(double[][]::new);
    }

    /**
     * Copies the Matrix to get an independent clone.
     *
     * @return independent copy of Matrix
     */
    public Matrix copy() {
        return new Matrix(this.data);
    }

    /**
     * Fills all spots of the Matrix with the specified value.
     *
     * @param d value to fill the Matrix with
     * @return this, after filling
     */
    public Matrix fill(double d) {
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                this.data[row][col] = d;
            }
        }
        return this;
    }

    /**
     * Example for adding two to every member of a randomized matrix
     *
     * <pre>
     * <code>
     *  Matrix m = new Matrix(2, 2).randomize();
     *  m.map((d, r, c) -> d + 2.0);
     * </code>
     * </pre>
     * <p>
     * Example for setting the members of the Matrix to the sum of the columns and
     * rows index (rows and columns are zero-based so we need to add two to the
     * sum):
     *
     * <pre>
     * <code>
     *  Matrix m = new Matrix(3, 3);
     *  m.map((d, r, c) -> r + c + 2);
     * </code>
     * </pre>
     *
     * @param helper functional interface to perform the mapping
     * @return this, after mapping
     */
    public Matrix map(IMathHelper<Double> helper) {
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                this.data[row][col] = helper.getValue(this.data[row][col], row, col);
            }
        }
        return this;
    }

    /**
     * Fills the Matrix with random values between the two bounds.
     *
     * @param min lower bound (inclusive)
     * @param max upper bound (exclusive)
     * @return this, after randomizing
     */
    public Matrix randomize(double min, double max) {
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                this.data[row][col] = ThreadLocalRandom.current().nextDouble(min, max);
            }
        }
        return this;
    }

    /**
     * Fills the Matrix randomly with numbers between 0 and 1
     *
     * @return this, after randomizing
     */
    public Matrix randomize() {
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                this.data[row][col] = ThreadLocalRandom.current().nextDouble();
            }
        }
        return this;
    }

    /**
     * Prints the Matrix into the standard System.out PrintStream.
     */
    public void print() {
        print(System.out);
    }

    /**
     * Prints the Matrix into the dedicated PrintStream.
     */
    public void print(PrintStream stream) {
        stream.println("-------------------------------------------------");
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                stream.print(this.data[row][col] + "\t");
            }
            stream.println();
        }
        stream.println("-------------------------------------------------");
    }


    /**
     * Multiplies the Matrix with another Matrix.
     *
     * @param m the Matrix to multiply with
     * @return this, after multiplication
     */
    public Matrix matmul(Matrix m) {
        if (this.cols != m.rows)
            throw new IllegalArgumentException("A's cols and B's rows must match!");
        double[][] newData = new double[this.rows][m.cols];
        for (int row = 0; row < this.rows; row++)
            for (int col = 0; col < m.cols; col++)
                for (int j = 0; j < this.cols; j++)
                    newData[row][col] += this.data[row][j] * m.data[j][col];
        this.data = newData;
        return this;
    }

    /**
     * Subtracts the Matrix m from the calling Matrix.
     *
     * @param m the Matrix to subtract
     * @return this, after subtraction
     */
    public Matrix sub(Matrix m) {
        if (m.cols != this.cols || m.rows != this.rows)
            throw new IllegalArgumentException("rows and columns must match!");

        for (int row = 0; row < this.rows; row++)
            for (int col = 0; col < this.cols; col++)
                this.data[row][col] -= m.data[row][col];
        return this;
    }

    /**
     * Adds the Matrix m to the calling Matrix.
     *
     * @param m the Matrix to add
     * @return this, after addition
     */
    public Matrix add(Matrix m) {
        if (this.cols != m.cols || this.rows != m.rows)
            throw new IllegalArgumentException("rows and columns must match!");

        for (int row = 0; row < this.rows; row++)
            for (int col = 0; col < this.cols; col++)
                this.data[row][col] += m.data[row][col];
        return this;
    }

    /**
     * Multiplies the Matrix with a scalar.
     *
     * @param scl the scalar to multiply with
     * @return this, after multiplication
     */
    public Matrix mult(double scl) {
        for (int row = 0; row < this.rows; row++)
            for (int col = 0; col < this.cols; col++)
                this.data[row][col] *= scl;
        return this;
    }

    /**
     * the hadamard product
     *
     * @param m the matrix to multiply with
     * @return this after multiplication with matrix m
     */
    public Matrix hadamard(Matrix m) {
        if (this.cols != m.cols || this.rows != m.rows)
            throw new IllegalArgumentException("rows and columns must match!");

        for (int row = 0; row < this.rows; row++)
            for (int col = 0; col < this.cols; col++)
                this.data[row][col] *= m.data[row][col];
        return this;
    }

    /**
     * Setter for a whole column.
     *
     * @param col  column index
     * @param data data to set
     * @return this, after setting
     * @throws IllegalArgumentException if the column does not exist or the data does not fit
     */
    public Matrix setColumn(int col, double[] data) {
        if (this.cols <= col || col < 0)
            throw new IllegalArgumentException("This column does not exist!");
        if (this.data.length != data.length)
            throw new IllegalArgumentException("The data does not fit in the column!");
        for (int i = 0; i < this.data.length; i++)
            this.data[i][col] = data[i];
        return this;
    }

    /**
     * Setter for a whole row.
     *
     * @param row  row index
     * @param data data to set
     * @return this, after setting
     * @throws IllegalArgumentException if the row does not exist or the data does not fit
     */
    public Matrix setRow(int row, double[] data) {
        if (this.rows <= row || row < 0)
            throw new IllegalArgumentException("This row does not exist!");
        if (this.data[row].length != data.length)
            throw new IllegalArgumentException("The data does not fit in the row!");
        this.data[row] = data;
        return this;
    }

    /**
     * Calculates the determinant of the matrix.
     *
     * @return the determinant of the matrix
     */
    public double det() {
        if (data.length != data[0].length)
            throw new IllegalArgumentException("The Matrix is not quadratic!");
        int n = data.length;

        if (n == 1)
            return data[0][0];

        if (n == 2)
            return (data[0][0] * data[1][1]) - (data[0][1] * data[1][0]);

        double det = 0;
        double[][] tmp;
        for (int i = 0; i < n; i++) {
            tmp = new double[n - 1][n - 1];
            for (int j = 1; j < n; j++)
                for (int k = 0; k < n; k++)
                    if (k < i)
                        tmp[j - 1][k] = data[j][k];
                    else if (k > i)
                        tmp[j - 1][k - 1] = data[j][k];
            Matrix m = new Matrix(tmp);
            det += (data[0][i] * Math.pow(-1, i) * m.det());
        }
        return det;
    }

    /**
     * Setter for a specific value.
     *
     * @param row row index
     * @param col column index
     * @param val value to set
     * @return this, after setting
     */
    public Matrix set(int row, int col, double val) {
        data[row][col] = val;
        return this;
    }

    /**
     * Getter for a specific value.
     *
     * @param row row index
     * @param col column index
     * @return the value at the specified location
     */
    public double get(int row, int col) {
        return this.data[row][col];
    }

    /**
     * Getter for the whole data of the matrix as 2D array. This is a reference to
     * the data, so changes to the data will affect the matrix.
     *
     * @return the data of the matrix
     */
    public double[][] getData() {
        return this.data;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Matrix other) {
            if (this.cols != other.cols || this.rows != other.rows)
                return false;
            for (int row = 0; row < this.rows; row++)
                for (int col = 0; col < other.cols; col++)
                    if (this.data[row][col] != other.data[row][col])
                        return false;
            return true;
        }
        return super.equals(o);
    }

}