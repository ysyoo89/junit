package com.test.junit.scratch;

import org.assertj.core.data.Percentage;
import org.junit.Test;

import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;

public class NewtonTest {
    static class Newton {
        private static final double TOLERANCE = 1E-16;

        public static double squareRoot(double n) {
            double approx = n;
            while (abs(approx - n / approx) > TOLERANCE * approx) {
                approx = (n / approx + approx) / 2.0;
            }
            return approx;
        }

    }

    @Test
    public void squareRoot() {
        double result = Newton.squareRoot(250.0);
        assertThat(result * result).isCloseTo(250.0, Percentage.withPercentage(Newton.TOLERANCE));
        assertThat(Newton.squareRoot(1969.0)).isCloseTo(Math.sqrt(1969.0), Percentage.withPercentage(Newton.TOLERANCE));
    }
}
