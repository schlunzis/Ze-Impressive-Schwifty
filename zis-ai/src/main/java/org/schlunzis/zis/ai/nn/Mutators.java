package org.schlunzis.zis.ai.nn;

public class Mutators {

    private static final Mutator GAUSSIAN = (random, mutationRate, oldValue, r, c) -> {
        double z = random.nextGaussian();
        return oldValue + z * mutationRate;
    };

    private static final Mutator UNIFORM = (random, mutationRate, oldValue, r, c) -> {
        return random.nextDouble() < mutationRate ? random.nextDouble() - 0.5 : oldValue;
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

    /**
     * A mutator which mutates each value based on a uniform distribution between -0.5 and 0.5
     *
     * @return the uniform mutator
     */
    public static Mutator uniform() {
        return UNIFORM;
    }

}
