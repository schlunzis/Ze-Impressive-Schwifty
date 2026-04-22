package org.schlunzis.zis.ai.nn;

import java.util.concurrent.ThreadLocalRandom;

public class Mutators {

    private static final Mutator GAUSSIAN = (mutationRate, oldValue, r, c) -> {
        double z = ThreadLocalRandom.current().nextGaussian();
        return oldValue + z * mutationRate;
    };

    private Mutators() {
    }

    /**
     * A mutator which mutates each value based on a Gaussian distribution.
     *
     * @return the Gaussian mutator
     */
    public static Mutator gaussian() {
        return GAUSSIAN;
    }

}
