package bg.sofia.uni.fmi.mjt.race.track.pit;

import bg.sofia.uni.fmi.mjt.race.track.Car;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Pit {

    private final int nPitTeams;
    private final List<Integer> finishedCars;
    private final List<PitTeam> pitTeams;
    private final Queue<Car> waitingCars;

    public Pit(int nPitTeams) {
        this.nPitTeams = nPitTeams;
        this.waitingCars = new LinkedList<>();
        this.finishedCars = new ArrayList<>();
        this.pitTeams = new ArrayList<>();

        for (int i = 0; i < nPitTeams; i++) {
            this.pitTeams.add(new PitTeam(i, this));
        }
    }

    public void submitCar(Car car) {
        if (car.getNPitStops() == 0) {
            finishedCars.add(car.getCarId());
            System.out.println("Car finished: " + car.getCarId());
        } else {
            car.pause();

            waitingCars.add(car);
        }

        for (PitTeam pitTeam : this.pitTeams) {
            if (!this.waitingCars.isEmpty()) {
                if (pitTeam.getState() == Thread.State.NEW) {
                    new Thread(pitTeam).start();
                }
            }
        }
    }

    //synchro?
    public synchronized Car getCar() {
        return this.waitingCars.poll();
    }

    public int getPitStopsCount() {
        int count = 0;
        for (PitTeam pitTeam : this.pitTeams) {
            if (pitTeam.getState() != Thread.State.RUNNABLE) {
                count++;
            }
        }
        return count;
    }

    public List<PitTeam> getPitTeams() {
        return this.pitTeams;
    }

    //synchro?
    public synchronized void finishRace() {
        while (!this.waitingCars.isEmpty()) {
            for (PitTeam pitTeam : this.pitTeams) {
                pitTeam.notify();
            }
        }


    }

    public List<Integer> getFinishedCars() {
        return List.copyOf(this.finishedCars);
    }

    public boolean hasWaitingCars() {
        return !this.waitingCars.isEmpty();
    }

    public boolean areAllPitsWaiting() {
        for (PitTeam pitTeam : this.pitTeams) {
            if (pitTeam.getState() == Thread.State.RUNNABLE) {
                return false;
            }
        }

        return true;
    }
}