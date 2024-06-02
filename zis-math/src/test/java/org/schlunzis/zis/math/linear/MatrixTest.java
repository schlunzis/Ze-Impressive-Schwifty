package org.schlunzis.zis.math.linear;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void testEquals() {
        Matrix m = new Matrix(new double[][]{{0, 0}, {0, 0}});
        Matrix m1 = new Matrix(2, 2);

        assertTrue(Matrix.equals(m, m1));

        m = new Matrix(new double[][]{{1, 2}, {3, 4}});
        m1.setRow(0, new double[]{1, 2});
        m1.setRow(1, new double[]{3, 4});
        assertTrue(Matrix.equals(m, m1));
        assertTrue(m1.equals(m));

        m = new Matrix(2, 2);
        assertFalse(Matrix.equals(m, m1));
        assertFalse(m.equals(m1));

        m1 = new Matrix(3, 4);
        assertFalse(Matrix.equals(m, m1));

    }

    @Test
    void testDeterminant() {
        Matrix m = new Matrix(new double[][]{{2, 3}, {4, 5}});
        assertEquals(-2, m.det());

        m = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        assertEquals(0, m.det());

        m = new Matrix(new double[][]{{6, 3, 4}, {8, 9, 3}, {7, 4, 3}});
        assertEquals(-43, m.det());

        assertThrows(IllegalArgumentException.class, () -> new Matrix(new double[][]{{2, 3, 4}, {4, 5, 6}}).det());
    }

    @Test
    void testMult() {

        // hadamard
        Matrix m = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix m1 = new Matrix(new double[][]{{3, 4}, {4, 5}});
        Matrix target = new Matrix(new double[][]{{3, 8}, {12, 20}});
        Matrix result = Matrix.hadamard(m, m1);
        assertTrue(Matrix.equals(target, result));
        assertTrue(Matrix.equals(target, m.hadamard(m1)));

        // dot product
        m = new Matrix(new double[][]{{1, 2}, {3, 4}});
        m1 = new Matrix(new double[][]{{3, 4}, {4, 5}});
        target = new Matrix(new double[][]{{11, 14}, {25, 32}});
        result = Matrix.matmul(m, m1);
        assertTrue(Matrix.equals(target, result));
        assertTrue(Matrix.equals(target, m.matmul(m1)));

        // scaling
        m = new Matrix(new double[][]{{2, 4}, {1, 3}});
        result = Matrix.mult(m, 0);
        m.mult(3);
        target = new Matrix(new double[][]{{6, 12}, {3, 9}});
        assertTrue(Matrix.equals(target, m));
        assertTrue(Matrix.equals(new Matrix(new double[][]{{0, 0}, {0, 0}}), result));

    }

    @Test
    void testFill() {
        Matrix m = new Matrix(3, 4);
        m.fill(2);
        assertTrue(Matrix.equals(new Matrix(new double[][]{{2, 2, 2, 2}, {2, 2, 2, 2}, {2, 2, 2, 2}}), m));
    }

    @Test
    void testMap() {
        Matrix m = new Matrix(2, 2);
        m.map((d, r, c) -> d + 2.0);
        assertTrue(m.equals(new Matrix(new double[][]{{2, 2}, {2, 2}})));
    }

    @Test
    void testTranspose() {
        Matrix m = new Matrix(new double[][]{{1, 2}, {3, 4}, {5, 6}});

        Matrix target = new Matrix(new double[][]{{1, 3, 5}, {2, 4, 6}});

        Matrix transposed = Matrix.transpose(m);
        m.transpose();
        assertTrue(Matrix.equals(transposed, m));
        assertTrue(Matrix.equals(target, m));

    }

    @Test
    void testBasicArithmetic() {
        // Addition
        Matrix a = new Matrix(2, 2);
        Matrix b = new Matrix(new double[][]{{1, 2}, {3, 4}});
        assertTrue(a.add(b).equals(b));
        a = new Matrix(new double[][]{{2, 2}, {2, 2}});
        a.add(b);
        assertTrue(a.equals(new Matrix(new double[][]{{3, 4}, {5, 6}})));

        // static
        a = new Matrix(3, 3);
        b = new Matrix(new double[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}});
        Matrix result = Matrix.add(a, b);
        assertTrue(Matrix.equals(result, b));

        // Subtraction
        a = new Matrix(2, 2);
        b = new Matrix(new double[][]{{1, 2}, {3, 4}});
        assertTrue(a.sub(b).equals(b.mult(-1)));

        a = new Matrix(new double[][]{{2, 2}, {2, 2}});
        b = new Matrix(new double[][]{{1, 2}, {3, 4}});
        a.sub(b);
        assertTrue(a.equals(new Matrix(new double[][]{{1, 0}, {-1, -2}})));

        // static
        a = new Matrix(new double[][]{{2, 2}, {2, 2}});
        b = new Matrix(new double[][]{{1, 2}, {3, 4}});
        result = Matrix.sub(a, b);
        assertTrue(Matrix.equals(result, new Matrix(new double[][]{{1, 0}, {-1, -2}})));

    }

    @Test
    public void testArray() {
        double[][] vals = {{3, 4, 5}, {1, 2, 3}, {4, 5, 6}};
        Matrix m = new Matrix(vals);
        double[][] data = m.toArray();
        Matrix m1 = new Matrix(data);
        assertTrue(Matrix.equals(m, m1));
        for (int i = 0; i < vals.length; i++) {
            assertArrayEquals(vals[i], data[i]);
        }
    }

}