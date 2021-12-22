package bg.sofia.uni.fmi.mjt.race.track;

import bg.sofia.uni.fmi.mjt.race.track.pit.Pit;
import bg.sofia.uni.fmi.mjt.race.track.pit.PitTeam;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        Pit p = new Pit(3);
//	    PitTeam t1 = new PitTeam(1, p);
//        PitTeam t2 = new PitTeam(2, p);
//        PitTeam t3 = new PitTeam(3, p);
        Thread t1 = new Thread(new MyClass());
        System.out.println(t1.getState());
        t1.start();
        System.out.println("---------------------------");
        System.out.println(t1.getState());
        t1.join();
        System.out.println("->>" + t1.getState());
    }
}
class MyClass implements Runnable {
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.State state = Thread.currentThread().getState();
        System.out.println("State of thread: " + state);
    }
}