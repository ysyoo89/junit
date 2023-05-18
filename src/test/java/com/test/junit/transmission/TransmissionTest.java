package com.test.junit.transmission;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransmissionTest {
    private Transmission transmission;
    private Car car;

    @Before
    public void create() {
        car = new Car();
        transmission = new Transmission(car);
    }

    @Test
    public void remainsInDriveAfterAcceleration() {
        transmission.shift(Gear.DRIVE);
        car.accelerateTo(35);
        assertThat(transmission.getGear()).isEqualTo(Gear.DRIVE);
    }

    @Test
    public void ignoresShiftToParkWhileInDrive() {
        transmission.shift(Gear.DRIVE);
        car.accelerateTo(30);

        transmission.shift(Gear.PARK);

        assertThat(transmission.getGear()).isEqualTo(Gear.DRIVE);
    }

    @Test
    public void allowsShiftToParkWhenNotMoving() {
        transmission.shift(Gear.DRIVE);
        car.accelerateTo(30);
        car.brakeToStop();

        transmission.shift(Gear.PARK);

        assertThat(transmission.getGear()).isEqualTo(Gear.PARK);
    }
}
