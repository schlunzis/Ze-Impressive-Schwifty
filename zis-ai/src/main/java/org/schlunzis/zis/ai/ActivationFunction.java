package org.schlunzis.zis.ai;

/**
 * @author JayPi4c
 * @since 0.0.1
 */
public interface ActivationFunction {
    double activate(double x);

    double deactivate(double y);
}