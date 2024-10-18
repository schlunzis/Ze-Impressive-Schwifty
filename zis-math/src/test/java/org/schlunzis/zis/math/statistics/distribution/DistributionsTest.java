package org.schlunzis.zis.math.statistics.distribution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Random;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LeanedDistribution.class})
@ExtendWith(MockitoExtension.class)
class DistributionsTest {

    static final double MEAN = 2;
    static final double SD = 4;

    @Mock
    Random random;
    @Mock
    LeanedDistribution distribution;

    @Test
    void whenConstructorInvokedWithParameters_ThenMockObjectShouldBeCreated() throws Exception {
        PowerMockito.whenNew(LeanedDistribution.class).withAnyArguments().thenReturn(distribution);
        LeanedDistribution d = Distributions.leanedLeft(0, 10, 1);
        PowerMockito.verifyNew(LeanedDistribution.class).withArguments(0d, 10d, 0.8, 1d);
        Assertions.assertEqauls(distribution, d);
    }

}
