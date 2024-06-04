package org.schlunzis.zis.math.statistics.distribution;

import java.util.Random;

/**
 * This class represents a distribution with a lower and upper bound. The sample is guaranteed to be between these bounds.
 * If the sample could not be created, a random value between the bounds is returned.
 *
 * @author Til7701
 * @since 0.0.1
 */
public class BoundDistribution extends NormalDistribution {

    private static final int MAX_SAMPLE_TRIES = 1_000;

    private final double lowerBound;
    private final double upperBound;

    /**
     * Creates a Distribution with the given behaviour.
     *
     * @param lowerBound lower bound for results
     * @param upperBound upper bound for results
     * @param mean       the mean of the distribution
     * @param sd         the standard deviation
     * @throws IllegalArgumentException if lowerBound is greater or equal to upperBound
     */
    public BoundDistribution(double lowerBound, double upperBound, double mean, double sd) {
        this(lowerBound, upperBound, mean, sd, new Random());
    }

    /**
     * Creates a Distribution with the given behaviour.
     *
     * @param lowerBound lower bound for results
     * @param upperBound upper bound for results
     * @param mean       the mean of the distribution
     * @param sd         the standard deviation
     * @param random     the random object to use
     * @throws IllegalArgumentException if lowerBound is greater or equal to upperBound
     */
    public BoundDistribution(double lowerBound, double upperBound, double mean, double sd, Random random) {
        super(mean, sd, random);
        if (lowerBound >= upperBound)
            throw new IllegalArgumentException("lower bound must be smaller than upper bound");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * This method returns a sample of the Distribution.
     * The sample is guaranteed to be between min and max (both inclusive).
     * If the sample could not be created, a random value between min and max is returned.
     *
     * @return a sample of the Distribution
     */
    @Override
    public double sample() {
        try {
            return attemptToGetSample();
        } catch (SampleException e) {
            return lowerBound + (Math.random() * (upperBound - lowerBound));
        }
    }

    /**
     * This method returns a sample of the Distribution.
     * The sample is guaranteed to be between min and max (both inclusive).
     *
     * @return a sample of the Distribution
     * @throws SampleException if the sample could not be created
     */
    @Override
    public double sampleChecked() throws SampleException {
        return attemptToGetSample();
    }

    /**
     * This method tries to get a sample from the distribution. If the sample is not within the bounds, a new sample is
     * created. This is repeated until a valid sample is found. If no valid sample is found after a certain number of
     * tries, an exception is thrown.
     *
     * @return a sample of the distribution
     * @throws IllegalStateException if no valid sample could be created after a certain number of tries
     */
    protected double attemptToGetSample() throws SampleException {
        double sample;
        int counter = 0;

        do {
            sample = super.sample();
            counter++;
            if (counter > MAX_SAMPLE_TRIES)
                throw new SampleException("failed to create sample within bounds after " + MAX_SAMPLE_TRIES + " tries");
        } while (sample < lowerBound || sample > upperBound);

        return sample;
    }

    /**
     * The lower bound for the results. The sample is guaranteed to be greater or equal to this value.
     *
     * @return the lower bound for this distribution
     */
    @Override
    public double getLowerBound() {
        return lowerBound;
    }

    /**
     * The upper bound for the results. The sample is guaranteed to be smaller or equal to this value.
     *
     * @return the upper bound for this distribution
     */
    @Override
    public double getUpperBound() {
        return upperBound;
    }

}
