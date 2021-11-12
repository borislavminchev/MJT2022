package bg.sofia.uni.fmi.mjt.rentalservice.service;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;
import bg.sofia.uni.fmi.mjt.rentalservice.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RentalService implements RentalServiceAPI {

    private final Vehicle[] allVehicles;

    public RentalService(Vehicle[] allVehicles) {
        this.allVehicles = allVehicles;
    }

    @Override
    public double rentUntil(Vehicle vehicle, LocalDateTime until) {
        if (vehicle == null ||
                vehicle.getEndOfReservationPeriod().isAfter(LocalDateTime.now()) ||
                until.isBefore(LocalDateTime.now()) ||
                !isVehicleRegistered(vehicle)) {
            return -1;
        }

        vehicle.setEndOfReservationPeriod(until);
        long diff = LocalDateTime.now().until(until, ChronoUnit.MINUTES) + 1;
        return diff * vehicle.getPricePerMinute();
    }

    @Override
    public Vehicle findNearestAvailableVehicleInRadius(String type, Location location, double maxDistance) {
        Vehicle nearest = null;
        double minDistance = Double.MAX_VALUE;
        double dist = 0;
        for (Vehicle currentVehicle : this.allVehicles) {
            dist = getDistance(currentVehicle.getLocation(), location);
            if (currentVehicle.getType().equals(type) &&
                    !currentVehicle.getEndOfReservationPeriod().isAfter(LocalDateTime.now()) &&
                    dist < maxDistance &&
                    minDistance > dist) {
                minDistance = dist;
                nearest = currentVehicle;
            }
        }

        return nearest;
    }

    boolean isVehicleRegistered(Vehicle vehicle) {
        for (int i = 0; i < this.allVehicles.length; i++) {
            if (vehicle.equals(this.allVehicles[i])) {
                return true;
            }
        }
        return false;
    }

    private static double getDistance(Location location1, Location location2) {
        return Math.sqrt(Math.pow(location1.getX() - location2.getX(), 2) +
                Math.pow(location1.getY() - location2.getY(), 2));
    }
}
