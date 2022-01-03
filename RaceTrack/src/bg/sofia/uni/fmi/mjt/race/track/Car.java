package bg.sofia.uni.fmi.mjt.race.track;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable {

    private int id;
    private AtomicInteger nPitStops;
    private Track track;

    public Car(int id, int nPitStops, Track track) {
        this.id = id;
        this.nPitStops = new AtomicInteger(nPitStops);
        this.track = track;
    }

    @Override
    public void run() {
        do {

            System.out.println("Car in race: " + this.id);
            try {
                Thread.sleep(new Random().nextLong(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            track.getPit().submitCar(this);

            nPitStops.decrementAndGet();
        } while (nPitStops.get() > 0);

    }

    public int getCarId() {
       return this.id;
    }

    public int getNPitStops() {
        return this.nPitStops.get();
    }

    public Track getTrack() {
        return this.track;
    }

    public synchronized void pause() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}