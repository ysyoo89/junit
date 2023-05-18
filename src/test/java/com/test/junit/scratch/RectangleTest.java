package com.test.junit.scratch;


import com.test.junit.bearing.Rectangle;
import org.junit.After;
import org.junit.Test;

import java.awt.*;

import static com.test.junit.scratch.ConstrainsSidesTo.constrainsSidesTo;
import static org.assertj.core.api.Assertions.assertThat;

public class RectangleTest {
    private Rectangle rectangle;

    @After
    public void ensureInvariant() {
        //assertThat(rectangle, constrainsSidesTo(100));
    }

    @Test
    public void answersArea() {
        rectangle = new Rectangle(new Point(5, 5), new Point(15, 10));
        assertThat(rectangle.area()).isEqualTo(50);
    }

    @Test
    public void allowsDynamicallyChangingSize() {
        rectangle = new Rectangle(new Point(5, 5));
        rectangle.setOppositeCorner(new Point(130, 130));
        assertThat(rectangle.area()).isEqualTo(15625);
    }
}
