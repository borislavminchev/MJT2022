package bg.sofia.uni.fmi.mjt.race.track;

import bg.sofia.uni.fmi.mjt.race.track.pit.Pit;

import java.util.List;

public class RaceTrack implements Track {
    private Pit pit;
    public RaceTrack(int numberOfTeams) {
        pit = new Pit(numberOfTeams);
    }

    @Override
    public void enterPit(Car car) {
        this.pit.submitCar(car);
    }

    @Override
    public int getNumberOfFinishedCars() {
        return pit.getFinishedCars().size();
    }

    @Override
    public List<Integer> getFinishedCarsIds() {
        return List.copyOf(pit.getFinishedCars());
    }

    @Override
    public Pit getPit() {
        return this.pit;
    }


}
