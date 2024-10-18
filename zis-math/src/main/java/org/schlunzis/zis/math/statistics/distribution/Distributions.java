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

    public static final double DEFAULT_SKEW_FACTOR = 0.8;
    public static final double DEFAULT_LEFT_SKEW = -1;
    public static final double DEFAULT_RIGHT_SKEW = 1;

    private Distributions() {
        // utility class
    }

    /**
     * Creates a leaned distribution with the given values, which is leaned to the left. So skewed to the right.
     *
     * @param lowerBound the lower bound for results
     * @param upperBound the upper bound for results
     * @param sd         the standard deviation
     * @return the created distribution
     */
    public static LeanedDistribution leanedLeft(double lowerBound, double upperBound, double sd) {
        return new LeanedDistribution(lowerBound, upperBound, DEFAULT_LEFT_SKEW * DEFAULT_SKEW_FACTOR, sd);
    }

    /**
     * Creates a leaned distribution with the given values, which is leaned to the left. So skewed to the right.
     *
     * @param lowerBound the lower bound for results
     * @param upperBound the upper bound for results
     * @param sd         the standard deviation
     * @param random     the random object to use
     * @return the created distribution
     */
    public static LeanedDistribution leanedLeft(double lowerBound, double upperBound, double sd, Random random) {
        return new LeanedDistribution(lowerBound, upperBound, DEFAULT_LEFT_SKEW * DEFAULT_SKEW_FACTOR, sd, random);
    }

    /**
     * Creates a leaned distribution with the given values, which is leaned to the right. So skewed to the left.
     *
     * @param lowerBound the lower bound for results
     * @param upperBound the upper bound for results
     * @param sd         the standard deviation
     * @return the created distribution
     */
    public static LeanedDistribution leanedRight(double lowerBound, double upperBound, double sd) {
        return new LeanedDistribution(lowerBound, upperBound, DEFAULT_RIGHT_SKEW * DEFAULT_SKEW_FACTOR, sd);
    }

    /**
     * Creates a leaned distribution with the given values, which is leaned to the right. So skewed to the left.
     *
     * @param lowerBound the lower bound for results
     * @param upperBound the upper bound for results
     * @param sd         the standard deviation
     * @param random     the random object to use
     * @return the created distribution
     */
    public static LeanedDistribution leanedRight(double lowerBound, double upperBound, double sd, Random random) {
        return new LeanedDistribution(lowerBound, upperBound, DEFAULT_RIGHT_SKEW * DEFAULT_SKEW_FACTOR, sd, random);
    }

    /**
     * Creates a leaned distribution with the given values. The distribution is randomly leaned to the left or right, or
     * it is not leaned.
     *
     * @param lowerBound the lower bound for results
     * @param upperBound the upper bound for results
     * @param sd         the standard deviation
     * @return the created distribution
     */
    public static LeanedDistribution leanedRandomly(double lowerBound, double upperBound, double sd) {
        final int leaningDirection = ThreadLocalRandom.current().nextInt(3) - 1;
        return new LeanedDistribution(lowerBound, upperBound, leaningDirection * DEFAULT_SKEW_FACTOR, sd);
    }

    /**
     * Creates a leaned distribution with the given values. The distribution is randomly leaned to the left or right, or
     * it is not leaned.
     *
     * @param lowerBound the lower bound for results
     * @param upperBound the upper bound for results
     * @param sd         the standard deviation
     * @param random     the random object to use
     * @return the created distribution
     */
    public static LeanedDistribution leanedRandomly(double lowerBound, double upperBound, double sd, Random random) {
        final int leaningDirection = random.nextInt(3) - 1;
        return new LeanedDistribution(lowerBound, upperBound, leaningDirection * DEFAULT_SKEW_FACTOR, sd, random);
    }

    /**
     * Creates a leaned distribution with the given values. The distribution is randomly leaned to the left or right.
     *
     * @param lowerBound the lower bound for results
     * @param upperBound the upper bound for results
     * @param sd         the standard deviation
     * @return the created distribution
     */
    public static LeanedDistribution leanedRandomlyLeftRight(double lowerBound, double upperBound, double sd) {
        return ThreadLocalRandom.current().nextBoolean() ? leanedLeft(lowerBound, upperBound, sd) : leanedRight(lowerBound, upperBound, sd);
    }

    /**
     * Creates a leaned distribution with the given values. The distribution is randomly leaned to the left or right.
     *
     * @param lowerBound the lower bound for results
     * @param upperBound the upper bound for results
     * @param sd         the standard deviation
     * @param random     the random object to use
     * @return the created distribution
     */
    public static LeanedDistribution leanedRandomlyLeftRight(double lowerBound, double upperBound, double sd, Random random) {
        return random.nextBoolean() ? leanedLeft(lowerBound, upperBound, sd, random) : leanedRight(lowerBound, upperBound, sd, random);
    }

}
