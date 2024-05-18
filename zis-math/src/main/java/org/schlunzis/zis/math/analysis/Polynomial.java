package org.schlunzis.zis.math.analysis;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The polynomial class offers the possibility to calculate and work with a
 * polynomial, which makes working with polynomials much easier.
 *
 * @author JayPi4c
 * @since 0.0.1
 */
public class Polynomial {

    /**
     * A polynomial is a collection of monomials, which are stored in this array
     */
    private List<Monomial> polynomial;

    /**
     * Create an empty polynomial object.
     */
    public Polynomial() {
        polynomial = new ArrayList<>();
    }

    /**
     * Create a polynomial from an array of monomials.
     *
     * @param monomials array of monomials to construct the polynomial
     */
    public Polynomial(Monomial... monomials) {
        polynomial = Arrays.asList(monomials);
        this.fill();
    }

    /**
     * Create a polynomial from a List of monomials.
     *
     * @param monomials List of monomials to construct the polynomial
     */
    public Polynomial(List<Monomial> monomials) {
        this.polynomial = new ArrayList<>();
        for (Monomial t : monomials)
            this.polynomial.add(t.copy());
        this.combine();
        this.fill();
        this.reorder();

    }

    /**
     * Create a polynomial with the degrees and coefficients.
     * The coefficients and degrees must be of the same length.
     * Example usage:
     * <pre>
     *     // f(x) = 2x + 0.5x^3
     *     Polynomial p = new Polynomial(new double[]{2, 0.5}, new int[]{1, 3});
     * </pre>
     *
     * @param coefficients array of coefficients
     * @param degrees      array of degrees
     * @throws IllegalArgumentException if coefficients and degrees are not of same
     *                                  length
     */
    public Polynomial(double[] coefficients, int[] degrees) {
        if (coefficients.length != degrees.length)
            throw new IllegalArgumentException("The number of coefficients and degrees is not identical!");
        this.polynomial = new ArrayList<>();
        for (int i = 0; i < coefficients.length; i++)
            this.polynomial.add(new Monomial(coefficients[i], degrees[i]));
        this.fill();
    }

    /**
     * Adds the polynomial to the polynomial.
     *
     * @param polynomial the polynomial to add
     * @return this after addition is done
     */
    public Polynomial add(Polynomial polynomial) {
        this.polynomial.addAll(polynomial.getPolynomial());
        this.combine();
        this.reorder();
        this.fill();
        return this;
    }

    /**
     * Adds the monomial to the polynomial.
     *
     * @param monomial the monomial to add
     * @return this after addition is done
     */
    public Polynomial add(Monomial monomial) {
        this.polynomial.add(monomial);
        this.combine();
        this.reorder();
        this.fill();
        return this;
    }

    /**
     * Returns the product of the calling polynomial with the corresponding scalar.
     *
     * @param scl a scalar
     * @return this after math is done
     */
    public Polynomial mult(double scl) {
        for (Monomial m : this.polynomial)
            m.mult(scl);
        this.fill();
        return this;
    }

    /**
     * Returns the product of the two polynomials.
     *
     * @param polynomial the polynomial to multiply with
     * @return this after multiplication is done
     */
    public Polynomial mult(Polynomial polynomial) {
        List<Monomial> monomials = new ArrayList<>();
        for (Monomial m : this.polynomial) {
            for (Monomial other : polynomial.getPolynomial()) {
                monomials.add(
                        new Monomial(m.getCoefficient() * other.getCoefficient(), m.getDegree() + other.getDegree()));
            }
        }
        this.polynomial = monomials;
        this.combine();
        this.reorder();
        this.fill();
        return this;
    }

    /**
     * This function combines the monomials with the same degrees. Example:
     * <pre>
     *     // f(x) = 2x + 5x +x^2
     *     Polynomial p = new Polynomial(new Monomial(2, 1), new Monomial(5, 1), new Monomial(1, 2));
     *     p.combine();
     *     // f(x) = 7x + x^2
     * </pre>
     */
    public void combine() {
        for (int i = 0; i < this.polynomial.size() - 1; i++) {
            for (int j = i + 1; j < this.polynomial.size(); j++) {
                Monomial a = this.polynomial.get(i);
                Monomial b = this.polynomial.get(j);
                if (a.isCombinable(b)) {
                    a.combine(b);
                    this.polynomial.remove(j);
                }
            }
        }
    }

    /**
     * This function sorts the polynomial by degree. This might be used before printing the polynomial.
     */
    public void reorder() {
        for (int i = 0; i < this.polynomial.size(); i++) {
            int degree = -Integer.MAX_VALUE;
            int index = -1;
            for (int j = i; j < this.polynomial.size(); j++) {
                Monomial t = this.polynomial.get(j);
                if (degree < t.getDegree()) {
                    degree = t.getDegree();
                    index = j;
                }
            }
            this.polynomial.add(i, this.polynomial.remove(index));
        }
    }

    /**
     * A polynomial can almost always contain monomials with a coefficient of 0.
     * Since the constructors do not need sorted and complete monomials, the missing
     * monomials can be inserted, so that the polynomial can be written completely.
     */
    public void fill() {
        for (int i = this.getDegree(); i >= 0; i--) {
            boolean found = false;
            for (Monomial t : this.polynomial) {
                found = i == t.getDegree();
                if (found)
                    break;
            }
            if (!found)
                this.polynomial.add(this.getDegree() - i, new Monomial(0, i));
        }
    }

    /**
     * Returns the derivation of the polynomial.
     *
     * @return a new derived polynomial
     */
    public Polynomial getDerivation() {
        Polynomial p = new Polynomial();
        for (Monomial t : this.polynomial)
            p.add(t.getDerivation());
        return p;
    }

    /**
     * Returns the degree of the polynomial. If the polynomial is empty or has no
     * monomials, then 0 is returned.
     *
     * @return degree or 0
     */
    public int getDegree() {
        int degree = 0;
        for (Monomial t : polynomial) {
            int d = t.getDegree();
            if (degree < d)
                degree = d;
        }
        return degree;
    }

    /**
     * Prints the Polynomial into the PrintStream.
     *
     * @param stream PrintStream to print the Polynomial
     * @deprecated
     */
    public void print(PrintStream stream) {
        stream.println(getFormula());
    }

    // https://de.wikipedia.org/wiki/Polynomdivision#Algorithmus
    // TODO: Implementation von Polynomdivision

    /**
     * Returns an independent copy of the calling polynomial object.
     *
     * @return independent Polynomial object
     */
    public Polynomial copy() {
        return new Polynomial(this.polynomial);
    }

    /**
     * Returns the string of the polynomial.
     *
     * @return Formula of Polynomial
     */
    public String getFormula() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.polynomial.size() - 1; i++) {
            builder.append(this.polynomial.get(i).toString());
            builder.append(this.polynomial.get(i + 1).getCoefficient() < 0 ? "" : "+");
        }
        builder.append(this.polynomial.getLast().toString());
        return builder.toString();
    }

    /**
     * Returns the string of the formatted polynomial.
     *
     * @return formatted formula of Polynomial
     */
    public String getFormulaFormatted() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.polynomial.size() - 1; i++) {
            builder.append(this.polynomial.get(i).toStringFormatted());
            builder.append(this.polynomial.get(i + 1).getCoefficient() < 0 ? "" : "+");
        }
        if (!this.polynomial.isEmpty())
            builder.append(this.polynomial.getLast().toStringFormatted());
        return builder.toString();

    }

    /**
     * With Newton's method one can get closer and closer to the zero point by
     * approximation, provided one has an initial guess.
     *
     * @param guess initial guess
     * @param n     number of iterations
     * @return Approximation of x for the root
     * @see <a href="https://en.wikipedia.org/wiki/Newton%27s_method">Wikipedia
     * Newton Method</a>
     */
    public double getRoot(double guess, int n) {
        double new_guess = guess - (this.getY(guess) / this.getDerivation().getY(guess));
        if (n == 0)
            return new_guess;
        return getRoot(new_guess, n - 1);
    }

    /**
     * Calculate y for a given x.
     *
     * @param x x value
     * @return calculated y
     */
    public double getY(double x) {
        double sum = 0;
        for (int i = 0; i < this.polynomial.size(); i++) {
            Monomial t = this.getPolynomial().get(i);
            sum += t.getCoefficient() * Math.pow(x, t.getDegree());
        }
        return sum;
    }

    // ----------------------HELPER--------------

    /**
     * Returns the list of monomials representing the polynomial.
     *
     * @return Monomial list
     */
    public List<Monomial> getPolynomial() {
        return this.polynomial;
    }

}