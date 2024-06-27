package org.schlunzis.zis.math.statistics.distribution;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LeanedDistributionTest {

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> new LeanedDistribution(0, 1, 0, 1));
        assertDoesNotThrow(() -> new LeanedDistribution(0, 1, 0, 1, new Random()));
        assertThrows(IllegalArgumentException.class, () -> new LeanedDistribution(1, 0, 0, 1));
        assertThrows(NullPointerException.class, () -> new LeanedDistribution(0, 1, 0, 1, null));
        assertThrows(NullPointerException.class, () -> new LeanedDistribution(1, 1, 0, 1, null));
    }

    @Test
    void testGetPseudoSkew() {
        LeanedDistribution leanedDistribution = new LeanedDistribution(0, 1, 42, 1);
        assertEquals(42, leanedDistribution.getPseudoSkew());
    }

    @Test
    void testSkew0() {
        LeanedDistribution leanedDistribution = new LeanedDistribution(0, 10, 0, 1);
        assertEquals(5, leanedDistribution.getMean());
    }

    @Test
    void testSkewPositive() {
        LeanedDistribution leanedDistribution = new LeanedDistribution(0, 10, 0.2, 1);
        assertEquals(4, leanedDistribution.getMean());
        leanedDistribution = new LeanedDistribution(0, 10, 0.5, 1);
        assertEquals(2.5, leanedDistribution.getMean());
        leanedDistribution = new LeanedDistribution(0, 10, 0.75, 1);
        assertEquals(1.25, leanedDistribution.getMean());
    }

    @Test
    void testSkewNegative() {
        LeanedDistribution leanedDistribution = new LeanedDistribution(0, 10, -0.2, 1);
        assertEquals(6, leanedDistribution.getMean());
        leanedDistribution = new LeanedDistribution(0, 10, -0.5, 1);
        assertEquals(7.5, leanedDistribution.getMean());
        leanedDistribution = new LeanedDistribution(0, 10, -0.75, 1);
        assertEquals(8.75, leanedDistribution.getMean());
    }

    @Test
    void testSkewOtherBounds() {
        LeanedDistribution leanedDistribution = new LeanedDistribution(5, 15, 0.2, 1);
        assertEquals(9, leanedDistribution.getMean());
        leanedDistribution = new LeanedDistribution(5, 15, 0.5, 1);
        assertEquals(7.5, leanedDistribution.getMean());
        leanedDistribution = new LeanedDistribution(5, 15, 0.75, 1);
        assertEquals(6.25, leanedDistribution.getMean());

        leanedDistribution = new LeanedDistribution(-30, 30, 0, 1);
        assertEquals(0, leanedDistribution.getMean());
        leanedDistribution = new LeanedDistribution(-30, 30, 0.5, 1);
        assertEquals(-15, leanedDistribution.getMean());
        leanedDistribution = new LeanedDistribution(-30, 30, -0.75, 1);
        assertEquals(22.5, leanedDistribution.getMean());

        leanedDistribution = new LeanedDistribution(-30, -20, 0.75, 1);
        assertEquals(-28.75, leanedDistribution.getMean());
        leanedDistribution = new LeanedDistribution(-30, -20, -0.75, 1);
        assertEquals(-21.25, leanedDistribution.getMean());
    }

}
