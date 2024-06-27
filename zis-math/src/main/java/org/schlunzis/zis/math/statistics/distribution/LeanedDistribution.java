package org.schlunzis.zis.math.statistics.distribution;

import java.util.Random;

/**
 * This class represents a leaned distribution. The mean is not in the middle of the lowerBound and upperBound value.
 * Instead, the mean is closer to the lowerBound or upperBound value depending on the pseudoSkew. Note that this pseudo
 * skew does explicitly not follow the mathematical definition of skewness.
 *
 * @author Til7701
 * @since 0.0.1
 */
public class LeanedDistribution extends BoundDistribution {

    private final double pseudoSkew;

    /**
     * Creates a Distribution with the given behaviour.
     *
     * @param lowerBound lower bound for results
     * @param upperBound upper bound for results
     * @param pseudoSkew defines the mean relative to lowerBound and upperBound.
     *                   0.5 -> first quarter
     *                   0 -> in the middle
     *                   -0.5 -> third quarter
     * @param sd         the standard deviation
     */
    public LeanedDistribution(double lowerBound, double upperBound, double pseudoSkew, double sd) {
        this(lowerBound, upperBound, pseudoSkew, sd, new Random());
    }

    /**
     * Creates a Distribution with the given behaviour.
     *
     * @param lowerBound lower bound for results
     * @param upperBound upper bound for results
     * @param pseudoSkew defines the mean relative to lowerBound and upperBound.
     *                   0.5 -> first quarter
     *                   0 -> in the middle
     *                   -0.5 -> third quarter
     * @param sd         the standard deviation
     * @param random     the random object to use
     */
    public LeanedDistribution(double lowerBound, double upperBound, double pseudoSkew, double sd, Random random) {
        super(lowerBound, upperBound, createMeanFromPseudoSkew(lowerBound, upperBound, pseudoSkew), sd, random);
        this.pseudoSkew = pseudoSkew;
    }

    private static double createMeanFromPseudoSkew(double lowerBound, double upperBound, double pseudoSkew) {
        double range = upperBound - lowerBound;
        double halfRange = range / 2.0;
        double middle = lowerBound + halfRange;
        if (pseudoSkew == 0) {
            return middle;
        } else if (pseudoSkew > 0) {
            double distanceToMiddle = halfRange * pseudoSkew;
            return middle - distanceToMiddle;
        } else {
            double distanceToMiddle = halfRange * -pseudoSkew;
            return middle + distanceToMiddle;
        }
    }

    /**
     * Returns the pseudo skew, this distribution was initialized with.
     *
     * @return the pseudo skew
     */
    public double getPseudoSkew() {
        return pseudoSkew;
    }

}
