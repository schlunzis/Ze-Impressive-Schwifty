package org.schlunzis.zis.math.linear;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void testConstructor() {
        Vector v = new Vector(3);
        assertEquals(3, v.getData().length);

        double[] data = {1.0, 2.0, 3.0};
        v = new Vector(data);
        assertArrayEquals(data, v.getData());
    }

    @Test
    void testToArray() {
        double[] data = {1.0, 2.0, 3.0};
        Vector v = new Vector(data);
        assertArrayEquals(data, v.toArray());
    }

    @Test
    void testCopy() {
        double[] data = {1.0, 2.0, 3.0};
        Vector v1 = new Vector(data);
        Vector v2 = v1.copy();
        assertArrayEquals(v1.getData(), v2.getData());
    }

    @Test
    void testGetData() {
        double[] data = {1.0, 2.0, 3.0};
        Vector v = new Vector(data);
        assertArrayEquals(data, v.getData());
    }

    @Test
    void testGet() {
        double[] data = {1.0, 2.0, 3.0};
        Vector v = new Vector(data);
        assertEquals(1.0, v.get(0));
        assertEquals(2.0, v.get(1));
        assertEquals(3.0, v.get(2));
    }

    @Test
    void testSet() {
        double[] data = {1.0, 2.0, 3.0};
        Vector v = new Vector(data);
        v.set(0, 4.0);
        assertEquals(4.0, v.get(0));
    }

    @Test
    void testGetException() {
        Vector v = new Vector(3);
        assertThrows(IndexOutOfBoundsException.class, () -> v.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> v.get(-1));
    }
}