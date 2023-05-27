package com.test.junit.util;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.TestFactory;

import java.util.List;

public class ContainsMatches extends TypeSafeMatcher<List<Match>> {
    private Match[] expected;

    public ContainsMatches(Match[] expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("<" + expected.toString() + ">");
    }

    private boolean equals(Match expected, Match actual) {
        return expected.searchString.equals(actual.searchString) && expected.surroundingContext.equals(actual.surroundingContext);
    }

    @Override
    protected boolean matchesSafely(List<Match> actual) {
        if (actual.size() != expected.length) {
            return false;
        }
        for (int i = 0; i < expected.length; i++) {
            if (!equals(expected[i], actual.get(i))) {
                return false;
            }
        }
        return true;
    }

    @TestFactory
    public static <T> Matcher<List<Match>> containsMatches(Match[] expected) {
        return new ContainsMatches(expected);
    }
}
