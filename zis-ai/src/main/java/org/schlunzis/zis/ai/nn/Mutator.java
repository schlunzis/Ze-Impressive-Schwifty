package org.schlunzis.zis.ai.nn;

@FunctionalInterface
public interface Mutator {

    /**
     * Mutates the value using the given mutation rate.
     * <p>
     * Implementations must make sure that the returned value is between -0.5 and 0.5.
     *
     * @param oldValue     the value to mutate
     * @param mutationRate the mutation rate
     * @param row          the row of the value in the weight or bias matrix
     * @param col          the column of the value in the weight or bias matrix
     * @return the new value after mutation
     */
    double mutate(double mutationRate, double oldValue, int row, int col);

}
