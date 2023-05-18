package com.test.junit.util;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SparseArrayTest {
    private SparseArray array;

    @Before
    public void create() {
        array = new SparseArray();
    }

    @Test
    public void handlesInsertionInDescendingOrder() {
        array.put(7, "seven");
        array.checkInvariants();
        array.put(6, "six");
        array.checkInvariants();
        assertThat(array.get(6)).isEqualTo("six");
        assertThat(array.get(7)).isEqualTo("seven");
    }
}
