package org.schlunzis.zis.math.linear;

/**
 * @author jaypi4c
 * @version 1.0.0
 */
public class Vector {
    int rows;
    double data[];

    /**
     * @param rows
     * @since 1.0.0
     */
    public Vector(int rows) {
        this.rows = rows;
        this.data = new double[rows];
    }

    /**
     * @param data
     * @since 1.0.0
     */
    public Vector(double data[]) {
        this.data = new double[data.length];
        for (int i = 0; i < data.length; i++)
            this.data[i] = data[i];
    }

    /**
     * @return
     * @since 1.0.0
     */
    public double[] toArray() {
        double[] d = new double[rows];
        for (int i = 0; i < data.length; i++)
            d[i] = data[i];
        return d;
    }

    /**
     * @return
     * @since 1.0.0
     */
    public Vector copy() {
        return new Vector(this.data);
    }

    /**
     * @since 1.0.0
     */
    public void print() {
        System.out.println("-------------------------------------------------");
        for (int i = 0; i < this.data.length; i++)
            System.out.print(data[i] + "\t");
        System.out.println(")^T");
        System.out.println("-------------------------------------------------");
    }

    /**
     * @return
     * @since 1.0.0
     */
    public double[] getData() {
        return this.data;
    }

    /**
     * @param index
     * @return
     * @since 1.0.0
     */
    public double get(int index) {
        if (index >= data.length || index < 0)
            throw new IllegalArgumentException("Der index ist nicht in der data vorhanden!");
        return this.data[index];
    }
}