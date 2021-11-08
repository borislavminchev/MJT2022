package bg.sofia.uni.fmi.mjt.rentalservice.vehicle;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;

public class Car extends AbstractVehicle {
    private static final double PRICE_PER_MINUTE = 0.5;

    public Car(Location location) {
        super(location);
        this.type = "CAR";
    }

    @Override
    public double getPricePerMinute() {
        return PRICE_PER_MINUTE;
    }
}
