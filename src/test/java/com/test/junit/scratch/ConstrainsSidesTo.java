package com.test.junit.scratch;

import com.test.junit.bearing.Rectangle;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ConstrainsSidesTo extends TypeSafeMatcher<Rectangle> {
    private int length;

    public ConstrainsSidesTo(int length) {
        this.length = length;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("both sides must be <= " + length);
    }

    @Override
    protected boolean matchesSafely(Rectangle rect) {
        return
                Math.abs(rect.origin().x - rect.opposite().x) <= length &&
                        Math.abs(rect.origin().y - rect.opposite().y) <= length;
    }

//    @Factory
    public static <T> Matcher<Rectangle> constrainsSidesTo(int length) {
        return new ConstrainsSidesTo(length);
    }
}
