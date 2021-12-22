package bg.sofia.uni.fmi.mjt.race.track.pit;

import bg.sofia.uni.fmi.mjt.race.track.Car;

import java.util.*;

public class Pit {

    private int nPitTeams;
    private List<Integer> finishedCars;
    private List<PitTeam> pitTeams;
    private Queue<Car> waitingCars;

    public Pit(int nPitTeams) {
        this.nPitTeams = nPitTeams;
        this.waitingCars = new LinkedList<>();
        this.pitTeams = new ArrayList<>();
        for (int i = 0; i < nPitTeams; i++) {
            this.pitTeams.add(new PitTeam(i, this));
        }
    }

    public void submitCar(Car car) {
        if (car.getNPitStops() == 0) {
            finishedCars.add(car.getCarId());
        } else {
            waitingCars.add(car);
        }

        for (PitTeam pitTeam : this.pitTeams) {
            if (!this.waitingCars.isEmpty()) {
                if (pitTeam.getState() != Thread.State.RUNNABLE) {
                    pitTeam.run();
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

    }

    public List<Integer> getFinishedCars() {
        return List.copyOf(this.finishedCars);
    }
}