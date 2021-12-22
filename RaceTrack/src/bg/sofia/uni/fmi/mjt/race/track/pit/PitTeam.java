package bg.sofia.uni.fmi.mjt.race.track.pit;

import bg.sofia.uni.fmi.mjt.race.track.Car;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PitTeam extends Thread {

    private int id;
    private Pit pitStop;
    private AtomicInteger stops;

    static boolean flag = false;

    public PitTeam(int id, Pit pitStop) {
        this.id = id;
        this.pitStop = pitStop;
        this.stops = new AtomicInteger(0);
    }

    @Override
    public void run() {
        Car c = this.pitStop.getCar();
        try {
            Thread.sleep(new Random().nextInt(200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stops.incrementAndGet();
        new Thread(new Car(c.getCarId(), c.getNPitStops() - 1, c.getTrack())).start();

    }
    public int getPitStoppedCars() {
        return this.stops.get();
    }

}