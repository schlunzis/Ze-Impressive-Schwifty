package org.schlunzis.zis.math.linear;

/**
 * @param <A> Type of the values in the matrix
 * @author JayPi4c
 * @since 0.0.1
 */
public interface IMathHelper<A> {
    /**
     * This function allows to map every value from the matrix with a custom
     * calculation. Furthermore, the row and column of values location are provided,
     * allowing even more insights.
     *
     * @param value value at the spot
     * @param row   row index of the spot
     * @param col   column of the spot
     * @return new value for the spot
     */
    A getValue(A value, int row, int col);
}