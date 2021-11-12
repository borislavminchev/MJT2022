package bg.sofia.uni.fmi.mjt.rentalservice.vehicle;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;

public class Scooter extends AbstractVehicle {
    private static final double PRICE_PER_MINUTE = 0.3;

    public Scooter(String id, Location location) {
        super(id, location);
        this.type = "SCOOTER";
    }

    @Override
    public double getPricePerMinute() {
        return PRICE_PER_MINUTE;
    }
}
