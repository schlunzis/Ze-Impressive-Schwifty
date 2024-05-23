package org.schlunzis.zis.math.analysis;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolynomialTest {

    @Test
    void testConstructor() {
        Polynomial p = new Polynomial();
        assertEquals(0, p.getDegree());

        ArrayList<Monomial> monomials = new ArrayList<>();
        monomials.add(new Monomial(2.0, 3));
        monomials.add(new Monomial(3.0, 2));
        p = new Polynomial(monomials);
        assertEquals(3, p.getDegree());
    }

    @Test
    void testFill() {
        Polynomial p = new Polynomial();
        p.add(new Monomial(2.0, 3));
        p.fill();
        assertEquals(4, p.getPolynomial().size());
    }

    @Test
    void testGetDerivation() {
        Polynomial p = new Polynomial();
        p.add(new Monomial(2.0, 3));
        Polynomial derived = p.getDerivation();
        assertEquals(1, derived.getPolynomial().size());
        assertEquals(6.0, derived.getPolynomial().getFirst().getCoefficient());
        assertEquals(2, derived.getPolynomial().getFirst().getDegree());
    }

    @Test
    void testGetDegree() {
        Polynomial p = new Polynomial();
        p.add(new Monomial(2.0, 3));
        assertEquals(3, p.getDegree());
    }

    @Test
    void testCopy() {
        Polynomial p1 = new Polynomial();
        p1.add(new Monomial(2.0, 3));
        Polynomial p2 = p1.copy();
        assertEquals(p1.getDegree(), p2.getDegree());
        assertEquals(p1.getPolynomial().size(), p2.getPolynomial().size());
    }

    @Test
    void testGetRoot() {
        List<Monomial> monomials = new ArrayList<>();
        monomials.add(new Monomial(-1d));
        monomials.add(new Monomial(2));
        Polynomial p = new Polynomial(monomials);
        assertEquals(-1, p.getRoot(-2, 20), 0.001);
        assertEquals(1, p.getRoot(0.1, 20), 0.001);
    }

    @Test
    void testGetY() {
        Polynomial p = new Polynomial();
        assertEquals(0, p.getY(5));

        p.add(new Monomial(2, 1));
        assertEquals(0, p.getY(0));
        assertEquals(6, p.getY(3));

        p.add(new Monomial(0.5, 3));
        assertEquals(0, p.getY(0));
        assertEquals(8, p.getY(2));
        assertEquals(-19.5, p.getY(-3));
    }

    @Test
    void testGetPolynomial() {
        Polynomial p = new Polynomial();
        p.add(new Monomial(2.0, 3));
        assertEquals(1, p.getPolynomial().size());
        assertEquals(2.0, p.getPolynomial().getFirst().getCoefficient());
        assertEquals(3, p.getPolynomial().getFirst().getDegree());
    }
}