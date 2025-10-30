package org.schlunzis.zis.math.linear;


/**
 * LSSolver, short for Linear System Solver, is a class that provides methods to solve systems of linear equations.
 * The systems need to be provided as matrices.
 *
 * @author JayPi4c
 * @since 0.0.1
 */
public class LSSolver {

    /**
     *
     * This method solves a system of linear equations represented by a matrix and a result vector using the Gaussian elimination algorithm.
     *
     * @param m the matrix to solve
     * @param v the result vector
     * @return the solution vector
     * @see <a href="https://wiki.freitagsrunde.org/Javakurs/%C3%9Cbungsaufgaben/Gau%C3%9F-Algorithmus/Musterloesung">https://wiki.freitagsrunde.org</a>
     */
    public static Vector getSolution(Matrix m, Vector v) {
        Vector vector = v.copy();
        if (m.getData().length < m.getData()[0].length)
            throw new IllegalArgumentException("Equation system cannot be solved!");

        int tmpColumn = -1;

        for (int line = 0; line < m.getData().length; line++) {
            tmpColumn = -1;
            for (int column = 0; column < m.getData()[line].length; column++) {
                for (int row = line; row < m.getData().length; row++) {
                    if (m.get(row, column) != 0) {
                        tmpColumn = column;
                        break;
                    }
                }
                if (tmpColumn != -1) {
                    break;
                }
            }

            if (tmpColumn == -1) {
                for (int row = line; row < m.getData().length; row++) {
                    if (vector.get(line) != 0)
                        throw new IllegalArgumentException("Equation system has no solution!");
                }
                if (m.getData()[0].length - 1 >= line) {
                    throw new IllegalArgumentException("Equation system cannot be solved!");
                }
                break;
            }

            if (m.get(line, tmpColumn) == 0) {
                for (int row = line + 1; row < m.getData().length; row++) {
                    if (m.get(row, tmpColumn) != 0) {

                        swapTwoLines(line, row, m.getData(), vector);
                        break;
                    }
                }
            }

            if (m.get(line, tmpColumn) != 0) {
                divideLine(line, m.get(line, tmpColumn), m.getData(), vector);
            }

            for (int row = line + 1; row < m.getData().length; row++) {
                removeRowLeadingNumber(m.get(row, tmpColumn), line, row, m.getData(), vector);
            }
        }

        for (int column = m.getData()[0].length - 1; column > 0; column--) {

            for (int row = column; row > 0; row--) {
                removeRowLeadingNumber(m.get(row - 1, column), column, row - 1, m.getData(), vector);
            }
        }

        return vector;
    }

    /**
     * Helper method to swap two lines in the matrix and the corresponding entries in the vector.
     *
     * @param rowOne index of the first row to swap
     * @param rowTwo index of the second row to swap
     * @param matrix the matrix in which the rows will be swapped
     * @param vector the vector in which the corresponding entries will be swapped
     * @see <a href="https://wiki.freitagsrunde.org/Javakurs/%C3%9Cbungsaufgaben/Gau%C3%9F-Algorithmus/Musterloesung">https://wiki.freitagsrunde.org</a>
     */
    private static void swapTwoLines(int rowOne, int rowTwo, double[][] matrix, Vector vector) {
        double[] tmpLine;
        double tmpVar;

        tmpLine = matrix[rowOne];
        tmpVar = vector.get(rowOne);

        matrix[rowOne] = matrix[rowTwo];
        vector.set(rowOne, vector.get(rowTwo));

        matrix[rowTwo] = tmpLine;
        vector.set(rowTwo, tmpVar);
    }

    /**
     * Helper method to divide a row in the matrix and the corresponding entry in the vector by a given divisor.
     *
     * @param row    index of the row to be divided
     * @param div    the divisor
     * @param matrix the matrix containing the row to be divided
     * @param vector the vector containing the corresponding entry to be divided
     * @see <a href="https://wiki.freitagsrunde.org/Javakurs/%C3%9Cbungsaufgaben/Gau%C3%9F-Algorithmus/Musterloesung">https://wiki.freitagsrunde.org</a>
     */
    private static void divideLine(int row, double div, double[][] matrix, Vector vector) {
        for (int column = 0; column < matrix[row].length; column++) {
            matrix[row][column] = matrix[row][column] / div;
        }
        vector.set(row, vector.get(row) / div);
    }

    /**
     * Helper method to eliminate the leading number of a specified row in the matrix and adjust the corresponding entry in the vector.
     *
     * @param factor  the factor used to eliminate the leading number
     * @param rowRoot index of the row used as the root for elimination
     * @param row     index of the row to be modified
     * @param matrix  the matrix containing the rows to be modified
     * @param vector  the vector containing the corresponding entry to be modified
     * @see <a href="https://wiki.freitagsrunde.org/Javakurs/%C3%9Cbungsaufgaben/Gau%C3%9F-Algorithmus/Musterloesung">https://wiki.freitagsrunde.org</a>
     */
    private static void removeRowLeadingNumber(double factor, int rowRoot, int row, double[][] matrix, Vector vector) {
        for (int column = 0; column < matrix[row].length; column++) {
            matrix[row][column] = matrix[row][column] - factor * matrix[rowRoot][column];
        }
        vector.set(row, vector.get(row) - factor * vector.get(rowRoot));
    }
}
