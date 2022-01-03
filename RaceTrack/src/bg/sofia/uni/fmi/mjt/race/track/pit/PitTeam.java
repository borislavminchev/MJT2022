package bg.sofia.uni.fmi.mjt.race.track.pit;

import bg.sofia.uni.fmi.mjt.race.track.Car;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PitTeam extends Thread {
    private final int id;
    private final Pit pitStop;
    private final AtomicInteger stops;

    public PitTeam(int id, Pit pitStop) {
        this.id = id;
        this.pitStop = pitStop;
        this.stops = new AtomicInteger(0);
    }

    @Override
    public void run() {
        while (pitStop.hasWaitingCars()) {
            serviceCar();
        }

    }

    public int getPitStoppedCars() {
        return this.stops.get();
    }

    private synchronized void serviceCar() {
        Car c = this.pitStop.getCar();
        if(c != null) {
            System.out.println("Car in pit: " + c.getCarId());
            try {
                Thread.sleep(new Random().nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stops.incrementAndGet();
            c.notify();
            //new Thread(new Car(c.getCarId(), c.getNPitStops() - 1, c.getTrack())).start();
        }

        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}