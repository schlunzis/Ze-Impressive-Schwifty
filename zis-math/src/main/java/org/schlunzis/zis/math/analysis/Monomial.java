package org.schlunzis.zis.math.analysis;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Represents a monomial with a coefficient and a degree.
 *
 * @author JayPi4c
 * @since 0.0.1
 */
public class Monomial {

    private int degree;
    private double coefficient;

    /**
     * Creates a monomial with coefficient and degree.
     *
     * @param coefficient the coefficient of the monomial
     * @param degree      the degree of the monomial
     */
    public Monomial(double coefficient, int degree) {
        this.coefficient = coefficient;
        this.degree = degree;
    }

    /**
     * Creates a monomial with the specified coefficient and degree 0.
     *
     * @param coefficient the coefficient of the monomial
     */
    public Monomial(double coefficient) {
        this.coefficient = coefficient;
        this.degree = 0;
    }

    /**
     * Creates a monomial with the specified degree and the coefficient 1.
     *
     * @param degree the degree of the monomial
     */
    public Monomial(int degree) {
        this.degree = degree;
        this.coefficient = 1;
    }

    // ------------------FUNCTIONS-----------------

    /**
     * Checks if two monomials have the same degree and are therefore combinable.
     *
     * @param m the monomial to check combinability with
     * @return true, if same degree
     */
    public boolean isCombinable(Monomial m) {
        return this.degree == m.getDegree();
    }

    /**
     * If two monomials have the same degree, then the monomials can be combined.
     *
     * @param m the monomial that is combined with the calling one.
     * @return this
     */
    public Monomial combine(Monomial m) {
        if (!this.isCombinable(m))
            throw new IllegalArgumentException("These parts cannot be combined!");
        this.coefficient += m.getCoefficient();
        return this;
    }

    /**
     * Derives the monomial.
     *
     * @return derived monomial
     */
    public Monomial getDerivation() {
        int degree = Math.max(this.degree - 1, 0);
        return new Monomial(this.coefficient * this.degree, degree);
    }

    /**
     * Multiply the monomial by the scalar.
     *
     * @param scl the scalar to multiply with
     * @return this
     */
    public Monomial mult(double scl) {
        this.coefficient *= scl;
        return this;
    }

    /**
     * Returns the monomial as string.
     *
     * @return String Monomial
     */
    @Override
    public String toString() {
        return this.coefficient + (this.degree > 0 ? "x^" + this.degree : "");
    }

    /**
     * Returns the monomial as formatted string.
     *
     * @param pattern pattern for the decimal format
     * @return formatted Monomial
     */
    public String toStringFormatted(String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.HALF_UP);
        return coefficient == 0 ? "" : (df.format(coefficient) + (this.degree > 0 ? "x^" + this.degree : ""));
    }

    /**
     * Returns the monomial as formatted string with default pattern.
     *
     * @return formatted Monomial
     */
    public String toStringFormatted() {
        return toStringFormatted("#.####");
    }

    // ---------------------HELPER-----------------------------

    /**
     * Returns the degree of the calling monomial.
     *
     * @return degree of the monomial
     */
    public int getDegree() {
        return this.degree;
    }

    /**
     * Sets the degree of the calling monomial.
     *
     * @param degree the degree to set
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }

    /**
     * Returns the coefficient of the calling monomial.
     *
     * @return coefficient of the monomial
     */
    public double getCoefficient() {
        return this.coefficient;
    }

    /**
     * Sets the coefficient of the calling monomial.
     *
     * @param coefficient the coefficient to set
     */
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * Copies the monomial.
     *
     * @return an independent copy of the calling monomial.
     */
    public Monomial copy() {
        return new Monomial(this.coefficient, this.degree);
    }

}