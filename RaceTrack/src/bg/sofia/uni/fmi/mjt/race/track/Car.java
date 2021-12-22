package bg.sofia.uni.fmi.mjt.race.track;

import java.util.Random;

public class Car implements Runnable {

    private int id;
    private int nPitStops;
    private Track track;

    public Car(int id, int nPitStops, Track track) {
        this.id = id;
        this.nPitStops = nPitStops;
        this.track = track;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextLong(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        track.getPit().submitCar(this);
    }

    public int getCarId() {
       return this.id;
    }

    public int getNPitStops() {
        return this.nPitStops;
    }

    public Track getTrack() {
        return this.track;
    }

}