package bg.sofia.uni.fmi.mjt.rentalservice.vehicle;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;

public class Bicycle extends AbstractVehicle {

    private static final double PRICE_PER_MINUTE = 0.2;

    public Bicycle(String id, Location location) {
        super(id, location);
        this.type = "BICYCLE";
    }

    @Override
    public double getPricePerMinute() {
        return PRICE_PER_MINUTE;
    }
}
