package org.schlunzis.zis.math.statistics.distribution;

/**
 * This exception is thrown by implementations of the {@link Distribution} interface
 * if it could not create a sample. This might be due to limitations by the implementation.
 *
 * @author Til7701
 * @since 0.0.1
 */
public class SampleException extends Exception {

    public SampleException(String message) {
        super(message);
    }

}
