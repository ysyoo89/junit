package com.test.junit.scratch;


import com.test.junit.bearing.Bearing;
import com.test.junit.bearing.BearingOutOfRangeException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BearingTest {
    @Test(expected = BearingOutOfRangeException.class)
    public void throwsOnNegativeNumber() {
        new Bearing(-1);
    }

    @Test(expected = BearingOutOfRangeException.class)
    public void throwsWhenBearingTooLarge() {
        new Bearing(Bearing.MAX + 1);
    }

    @Test
    public void answersValidBearing() {
        assertThat(new Bearing(Bearing.MAX).value()).isEqualTo(Bearing.MAX);
    }

    @Test
    public void answersAngleBetweenItAndAnotherBearing() {
        assertThat(new Bearing(15).angleBetween(new Bearing(12))).isEqualTo(3);
    }

    @Test
    public void angleBetweenIsNegativeWhenThisBearingSmaller() {
        assertThat(new Bearing(12).angleBetween(new Bearing(15))).isEqualTo(-3);
    }
}
