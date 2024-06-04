package org.schlunzis.zis.math.analysis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonomialTest {

    @Test
    void testConstructor() {
        Monomial m = new Monomial(2.0, 3);
        assertEquals(2.0, m.getCoefficient());
        assertEquals(3, m.getDegree());

        m = new Monomial(2.0);
        assertEquals(2.0, m.getCoefficient());
        assertEquals(0, m.getDegree());

        m = new Monomial(3);
        assertEquals(1.0, m.getCoefficient());
        assertEquals(3, m.getDegree());
    }

    @Test
    void testIsCombinable() {
        Monomial m1 = new Monomial(2.0, 3);
        Monomial m2 = new Monomial(3.0, 3);
        assertTrue(m1.isCombinable(m2));

        m2 = new Monomial(3.0, 2);
        assertFalse(m1.isCombinable(m2));
    }

    @Test
    void testCombine() {
        Monomial m1 = new Monomial(2.0, 3);
        Monomial m2 = new Monomial(3.0, 3);
        m1.combine(m2);
        assertEquals(5.0, m1.getCoefficient());
        assertEquals(3, m1.getDegree());
    }

    @Test
    void testGetDerivation() {
        Monomial m = new Monomial(2.0, 3);
        Monomial derived = m.getDerivation();
        assertEquals(6.0, derived.getCoefficient());
        assertEquals(2, derived.getDegree());

        m = new Monomial(2.0, 0);
        derived = m.getDerivation();
        assertEquals(0.0, derived.getCoefficient());
        assertEquals(0, derived.getDegree());
    }

    @Test
    void testMult() {
        Monomial m = new Monomial(2.0, 3);
        m.mult(3.0);
        assertEquals(6.0, m.getCoefficient());
        assertEquals(3, m.getDegree());

        m.mult(-1.0);
        assertEquals(-6.0, m.getCoefficient());
        assertEquals(3, m.getDegree());

        m.mult(0);
        if (m.getCoefficient() != 0) {
            fail("Coefficient should be 0");
        }
        // assertEquals(0.0, m.getCoefficient());
        assertEquals(3, m.getDegree());
    }

    @Test
    void testToString() {
        Monomial m = new Monomial(2.0, 3);
        assertEquals("2.0x^3", m.toString());

        m = new Monomial(2.0, 0);
        assertEquals("2.0", m.toString());
    }

    @Test
    void testToStringFormatted() {
        Monomial m = new Monomial(2.1234, 3);
        assertEquals("2.1234x^3", m.toStringFormatted("#.####"));

        m = new Monomial(2.1234, 0);
        assertEquals("2.1234", m.toStringFormatted("#.####"));
    }

    @Test
    void testGetSetDegree() {
        Monomial m = new Monomial(2.0, 3);
        assertEquals(3, m.getDegree());

        m.setDegree(4);
        assertEquals(4, m.getDegree());
    }

    @Test
    void testGetSetCoefficient() {
        Monomial m = new Monomial(2.0, 3);
        assertEquals(2.0, m.getCoefficient());

        m.setCoefficient(3.0);
        assertEquals(3.0, m.getCoefficient());
    }

    @Test
    void testCopy() {
        Monomial m1 = new Monomial(2.0, 3);
        Monomial m2 = m1.copy();
        assertEquals(m1.getCoefficient(), m2.getCoefficient());
        assertEquals(m1.getDegree(), m2.getDegree());
    }
}