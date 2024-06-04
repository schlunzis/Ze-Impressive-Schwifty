package org.schlunzis.zis.ai;

/**
 * This interface provides the basic methods for an activation function.
 *
 * @author JayPi4c
 * @since 0.0.1
 */
public interface ActivationFunction {

    /**
     * This function activates the given value.
     *
     * @param x The value to activate.
     * @return The activated value.
     */
    double activate(double x);

    /**
     * This function deactivates the given value.
     * <p>
     * Usually this is the derivative of the activation function.
     *
     * @param y The value to deactivate.
     * @return The deactivated value.
     */
    double deactivate(double y);
}