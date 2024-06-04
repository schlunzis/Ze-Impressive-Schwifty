package org.schlunzis.zis.math.statistics.distribution;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class provides static methods to create different distributions.
 *
 * @author Til7701
 * @since 0.0.1
 */
public class Distributions {

    public static final double DEFAULT_SKEW_FACTOR = 3;
    public static final double DEFAULT_LEFT_SKEW = -1;
    public static final double DEFAULT_RIGHT_SKEW = 1;

    private Distributions() {
        // utility class
    }

    /**
     * Creates a normal distribution with the given mean and standard deviation.
     *
     * @param mean the mean of the distribution
     * @param sd   the standard deviation
     * @return a normal distribution
     */
    public static NormalDistribution normal(double mean, double sd) {
        return new NormalDistribution(mean, sd);
    }

    public static NormalDistribution normal(double mean, double sd, Random random) {
        return new NormalDistribution(mean, sd, random);
    }

    public static BoundDistribution bound(double lowerBound, double upperBound, double mean, double sd) {
        return new BoundDistribution(lowerBound, upperBound, mean, sd);
    }

    public static BoundDistribution bound(double lowerBound, double upperBound, double mean, double sd, Random random) {
        return new BoundDistribution(lowerBound, upperBound, mean, sd, random);
    }

    public static LeanedDistribution leaned(double lowerBound, double upperBound, double pseudoSkew, double sd) {
        return new LeanedDistribution(lowerBound, upperBound, pseudoSkew, sd);
    }

    public static LeanedDistribution leaned(double lowerBound, double upperBound, double pseudoSkew, double sd, Random random) {
        return new LeanedDistribution(lowerBound, upperBound, pseudoSkew, sd, random);
    }

    public static LeanedDistribution leanedLeft(double lowerBound, double upperBound, double sd) {
        return leaned(lowerBound, upperBound, DEFAULT_LEFT_SKEW * DEFAULT_SKEW_FACTOR, sd);
    }

    public static LeanedDistribution leanedLeft(double lowerBound, double upperBound, double sd, Random random) {
        return leaned(lowerBound, upperBound, DEFAULT_LEFT_SKEW * DEFAULT_SKEW_FACTOR, sd, random);
    }

    public static LeanedDistribution leanedRight(double lowerBound, double upperBound, double sd) {
        return leaned(lowerBound, upperBound, DEFAULT_RIGHT_SKEW * DEFAULT_SKEW_FACTOR, sd);
    }

    public static LeanedDistribution leanedRight(double lowerBound, double upperBound, double sd, Random random) {
        return leaned(lowerBound, upperBound, DEFAULT_RIGHT_SKEW * DEFAULT_SKEW_FACTOR, sd, random);
    }

    public static LeanedDistribution leanedRandomly(double lowerBound, double upperBound, double sd) {
        final int leaningDirection = ThreadLocalRandom.current().nextInt(3) - 1;
        return leaned(lowerBound, upperBound, leaningDirection * DEFAULT_SKEW_FACTOR, sd);
    }

    public static LeanedDistribution leanedRandomlyLeftRight(double lowerBound, double upperBound, double sd) {
        return ThreadLocalRandom.current().nextBoolean() ? leanedLeft(lowerBound, upperBound, sd) : leanedRight(lowerBound, upperBound, sd);
    }

}
