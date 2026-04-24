package org.schlunzis.zis.ai.nn;

import java.util.Random;

@FunctionalInterface
public interface Mutator {

    /**
     * Mutates the value using the given mutation rate.
     *
     * @param random       the random instance to use for mutation
     * @param mutationRate the mutation rate
     * @param oldValue     the value to mutate
     * @param row          the row of the value in the weight or bias matrix
     * @param col          the column of the value in the weight or bias matrix
     * @return the new value after mutation
     */
    double mutate(Random random, double mutationRate, double oldValue, int row, int col);

}
