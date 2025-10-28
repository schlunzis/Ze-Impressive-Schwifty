package org.schlunzis.zis.math.statistics.distribution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NormalDistributionTest {

    @Mock
    Random random;

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> new NormalDistribution(0, 1));
        assertDoesNotThrow(() -> new NormalDistribution(0, 1, new Random()));
        assertThrows(NullPointerException.class, () -> new NormalDistribution(0, 1, null));
    }

    @Test
    void testGetMean() {
        NormalDistribution normalDistribution = new NormalDistribution(42, 1);
        assertEquals(42, normalDistribution.getMean());
    }

    @Test
    void testGetStandardDeviation() {
        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
        assertEquals(1, normalDistribution.getStandardDeviation());
    }

    @Test
    void testGetLowerBound() {
        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
        assertEquals(Double.MIN_VALUE, normalDistribution.getLowerBound());
    }

    @Test
    void testGetUpperBound() {
        NormalDistribution normalDistribution = new NormalDistribution(0, 1);
        assertEquals(Double.MAX_VALUE, normalDistribution.getUpperBound());
    }

    @Test
    void testSample() {
        when(random.nextGaussian()).thenReturn(0.0);
        NormalDistribution normalDistribution = new NormalDistribution(0, 1, random);
        assertEquals(0.0, normalDistribution.sample());
    }

    @Test
    void testSample2() {
        when(random.nextGaussian()).thenReturn(1.0);
        NormalDistribution normalDistribution = new NormalDistribution(4, 1, random);
        assertEquals(5.0, normalDistribution.sample());
    }

    @Test
    void testSample3() {
        when(random.nextGaussian()).thenReturn(1.0, 2.0);
        NormalDistribution normalDistribution = new NormalDistribution(4, 3, random);
        assertEquals(7.0, normalDistribution.sample());
        assertEquals(10.0, normalDistribution.sample());
    }

    @Test
    void testSampleChecked() throws SampleException {
        when(random.nextGaussian()).thenReturn(0.0);
        NormalDistribution normalDistribution = new NormalDistribution(0, 1, random);
        assertEquals(0.0, normalDistribution.sampleChecked());
    }

    @Test
    void testSampleChecked2() throws SampleException {
        when(random.nextGaussian()).thenReturn(1.0);
        NormalDistribution normalDistribution = new NormalDistribution(4, 1, random);
        assertEquals(5.0, normalDistribution.sampleChecked());
    }

    @Test
    void testSampleChecked3() throws SampleException {
        when(random.nextGaussian()).thenReturn(1.0, 2.0);
        NormalDistribution normalDistribution = new NormalDistribution(4, 3, random);
        assertEquals(7.0, normalDistribution.sampleChecked());
        assertEquals(10.0, normalDistribution.sampleChecked());
    }

}
