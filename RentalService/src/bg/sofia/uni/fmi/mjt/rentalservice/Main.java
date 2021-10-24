package bg.sofia.uni.fmi.mjt.rentalservice;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;
import bg.sofia.uni.fmi.mjt.rentalservice.service.RentalService;
import bg.sofia.uni.fmi.mjt.rentalservice.service.RentalServiceAPI;
import bg.sofia.uni.fmi.mjt.rentalservice.vehicle.Bicycle;
import bg.sofia.uni.fmi.mjt.rentalservice.vehicle.Car;
import bg.sofia.uni.fmi.mjt.rentalservice.vehicle.Scooter;
import bg.sofia.uni.fmi.mjt.rentalservice.vehicle.Vehicle;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {
        Vehicle v1 = new Car(new Location(1, 1));
        Vehicle v2 = new Car(new Location(0, 1));
        Vehicle v3 = new Car(new Location(-1, 1));
        Vehicle v4 = new Bicycle(new Location(0, 2));
        Vehicle v5 = new Scooter(new Location(-1, -2));

        Vehicle[] vehicles = {v1,v2,v3,v4,v5};
        for (int i = 0; i < vehicles.length; i++) {
            System.out.printf("[%d] - %s \n", i+1, vehicles[i].getId());
        }
        RentalServiceAPI rentalService = new RentalService(vehicles);
        rentalService.rentUntil(vehicles[1], LocalDateTime.now().plusDays(1));
        Vehicle vehicle = rentalService.findNearestAvailableVehicleInRadius("Car", new Location(0, 0), 5);
        System.out.println("Nearest: " + vehicle.getId());
    }
}
