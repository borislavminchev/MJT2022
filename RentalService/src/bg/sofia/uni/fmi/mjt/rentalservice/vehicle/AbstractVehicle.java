package bg.sofia.uni.fmi.mjt.rentalservice.vehicle;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class AbstractVehicle implements Vehicle {

    private final String id;
    private final Location location;
    protected String type;
    private LocalDateTime endOfReservationPeriod;

//    public AbstractVehicle(Location location) {
//        this.id = UUID.randomUUID().toString();
//        this.location = location;
//        this.endOfReservationPeriod = LocalDateTime.now();
//    }


    public AbstractVehicle(String id, Location location) {
        this.id = id;
        this.location = location;
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
    public String getType() {
        return this.type;
    }

    @Override
    public LocalDateTime getEndOfReservationPeriod() {
        if (this.endOfReservationPeriod != null && this.endOfReservationPeriod.isAfter(LocalDateTime.now())) {
            return this.endOfReservationPeriod;
        }
        return LocalDateTime.now();
    }

    @Override
    public void setEndOfReservationPeriod(LocalDateTime until) {
        if (until != null) {
            this.endOfReservationPeriod = until;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractVehicle)) return false;
        AbstractVehicle that = (AbstractVehicle) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
