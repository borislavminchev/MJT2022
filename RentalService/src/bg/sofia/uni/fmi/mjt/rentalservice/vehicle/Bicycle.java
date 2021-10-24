package bg.sofia.uni.fmi.mjt.rentalservice.vehicle;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;

import java.time.LocalDateTime;
import java.util.UUID;

public class Bicycle implements Vehicle {

    private String id;
    private final String type;
    private Location location;
    private LocalDateTime endOfReservationPeriod;

    public Bicycle(Location location) {
        this.id = UUID.randomUUID().toString();
        this.location = location;
        this.type = "Bicycle";
        this.endOfReservationPeriod = LocalDateTime.now();
    }

    @Override
    public double getPricePerMinute() {
        return 0.2;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public LocalDateTime getEndOfReservationPeriod() {
        if(this.endOfReservationPeriod != null && this.endOfReservationPeriod.isAfter(LocalDateTime.now())) {
            return this.endOfReservationPeriod;
        }
        return LocalDateTime.now();
    }

    @Override
    public void setEndOfReservationPeriod(LocalDateTime until) {
        if(until != null) {
            this.endOfReservationPeriod = until;
        }
    }
}
