package org.schlunzis.zis.math.statistics.distribution;

/**
 * This interface represents a distribution. A distribution can be sampled to get a value. The distribution can be
 * described by its mean and standard deviation. Additionally, the distribution can have a lower and upper bound.
 * The {@link #sample()} method returns a value that is within the bounds of the distribution. If the sample could not be created,
 * a random value between the bounds is returned. If the sample could not be created and this is not acceptable, the
 * {@link  #sampleChecked()} method can be used. This method throws a {@link SampleException} if the sample could not be created.
 *
 * @author Til7701
 * @since 0.0.1
 */
public interface Distribution {

    /**
     * Returns the mean of the distribution.
     *
     * @return the mean of the distribution
     */
    double getMean();

    /**
     * Returns the standard deviation of the distribution.
     *
     * @return the standard deviation of the distribution
     */
    double getStandardDeviation();

    /**
     * Returns the lower bound of the distribution. Usually inclusive.
     *
     * @return the lower bound of the distribution
     */
    double getLowerBound();

    /**
     * Returns the upper bound of the distribution. Usually inclusive.
     *
     * @return the upper bound of the distribution
     */
    double getUpperBound();

    /**
     * Returns a sample of the distribution. The sample is guaranteed to be between the lower and upper bound.
     * If the sample could not be created, a random value between the bounds is returned.
     *
     * @return a sample of the distribution
     */
    double sample();

    /**
     * Returns a sample of the distribution. The sample is guaranteed to be between the lower and upper bound.
     * If the sample could not be created, a SampleException is thrown.
     *
     * @return a sample of the distribution
     * @throws SampleException if the sample could not be created
     */
    default double sampleChecked() throws SampleException {
        return sample();
    }

}
