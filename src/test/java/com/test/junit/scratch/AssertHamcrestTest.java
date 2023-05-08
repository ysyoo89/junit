package com.test.junit.scratch;

import org.assertj.core.data.Percentage;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
public class AssertHamcrestTest {

    @Test
    public void floatingPointTest() {
        /**
         * 해당 테스트 결과는 실패한다.
         * 소수점의 비교값이 우리가 생각하는 것과 다른 결과값이 나올 때가 있다.
         */
        assertThat(2.32 * 3).isEqualTo(6.96);
        /**
         * 해당 방식으로 테스트하면 정확한 오류 로그를 알기가 힘들다.
         */
        assertTrue(Math.abs((2.32 * 3) - 6.96) < 0.0005);
        /**
         * isCloseTo를 통해 좀 더 쉽게 소수점 비교를 할 수 있다.
         */
        assertThat(Math.abs(2.32 * 3)).isCloseTo(6.96, Percentage.withPercentage(0.0005));
    }
}
