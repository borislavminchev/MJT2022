package bg.sofia.uni.fmi.mjt.race.track;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        RaceTrack track = new RaceTrack(2);
        Thread c1 = new Thread(new Car(1, 3, track));
        Thread c2 = new Thread(new Car(2, 3, track));
        Thread c3 = new Thread(new Car(3, 3, track));

        c1.start();
        c2.start();
        c3.start();



    }
}
