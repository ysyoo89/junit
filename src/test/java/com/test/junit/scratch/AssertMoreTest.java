package com.test.junit.scratch;

import org.junit.*;

public class AssertMoreTest {
    @BeforeClass
    public static void initializeSomethingReallyExpensive() {
        // ...
    }

    @AfterClass
    public static void cleanUpSomethingReallyExpensive() {
        // ...
    }

    @Before
    public void createAccount() {
        // ...
    }

    @After
    public void closeConnections() {
        // ...
    }

    @Test
    public void depositIncreasesBalance() {
        // ...
    }

    @Test
    public void hasPositiveBalance() {
        // ...
    }
}
