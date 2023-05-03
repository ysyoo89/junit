package com.test.junit.sample;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ScoreCollectionTest {

    @Test
    void testArithmeticMean() {
        //fail("Not yet implemented");
    }

    @Test
    void answersArithmeticMeanOfTwoNumbers() {
        ScoreCollection collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);

        // 실행
        int actualResult = collection.arithmeticMean();

        // 단언
        assertThat(actualResult).isEqualTo(6);
    }
}