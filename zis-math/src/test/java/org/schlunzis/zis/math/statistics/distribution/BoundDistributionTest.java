package org.schlunzis.zis.math.statistics.distribution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoundDistributionTest {

    @Mock
    Random random;

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> new BoundDistribution(0, 1, 0, 1));
        assertDoesNotThrow(() -> new BoundDistribution(0, 1, 0, 1, new Random()));
        assertThrows(IllegalArgumentException.class, () -> new BoundDistribution(1, 0, 0, 1));
        assertThrows(NullPointerException.class, () -> new BoundDistribution(0, 1, 0, 1, null));
        assertThrows(NullPointerException.class, () -> new BoundDistribution(1, 1, 0, 1, null));
    }

    @Test
    void testGetMean() {
        BoundDistribution boundDistribution = new BoundDistribution(0, 1, 42, 1);
        assertEquals(42, boundDistribution.getMean());
    }

    @Test
    void testGetStandardDeviation() {
        BoundDistribution boundDistribution = new BoundDistribution(0, 1, 0, 1);
        assertEquals(1, boundDistribution.getStandardDeviation());
    }

    @Test
    void testGetLowerBound() {
        BoundDistribution boundDistribution = new BoundDistribution(3, 5, 0, 1);
        assertEquals(3, boundDistribution.getLowerBound());
    }

    @Test
    void testGetUpperBound() {
        BoundDistribution boundDistribution = new BoundDistribution(3, 5, 0, 1);
        assertEquals(5, boundDistribution.getUpperBound());
    }

    @Test
    void testSample() {
        when(random.nextGaussian()).thenReturn(0.0, 0.5, -0.2);
        BoundDistribution boundDistribution = new BoundDistribution(-1, 1, 0, 1, random);
        assertEquals(0.0, boundDistribution.sample());
        assertEquals(0.5, boundDistribution.sample());
        assertEquals(-0.2, boundDistribution.sample());
    }

    @Test
    void testSample2() {
        when(random.nextGaussian()).thenReturn(0.0, 0.5, -0.2);
        BoundDistribution boundDistribution = new BoundDistribution(-1, 1, 0, 1, random);
        assertEquals(0.0, boundDistribution.sample());
        assertEquals(0.5, boundDistribution.sample());
        assertEquals(-0.2, boundDistribution.sample());
    }

    @Test
    void testSampleChecked() {
        when(random.nextGaussian()).thenReturn(0.0, 0.5, -0.2, 1.0);
        BoundDistribution boundDistribution = new BoundDistribution(-1, 1, 0, 1, random);
        assertDoesNotThrow(boundDistribution::sampleChecked);
        assertDoesNotThrow(boundDistribution::sampleChecked);
        assertDoesNotThrow(boundDistribution::sampleChecked);
        assertDoesNotThrow(boundDistribution::sampleChecked);
    }

    @Test
    void testSampleChecked2() {
        when(random.nextGaussian()).thenReturn(42.0);
        BoundDistribution boundDistribution = new BoundDistribution(-1, 1, 0, 1, random);
        assertThrows(SampleException.class, boundDistribution::sampleChecked);
    }

    @Test
    void testSampleChecked3() {
        when(random.nextGaussian()).thenReturn(-1.5);
        BoundDistribution boundDistribution = new BoundDistribution(-1, 1, 0, 1, random);
        assertThrows(SampleException.class, boundDistribution::sampleChecked);
    }

}
