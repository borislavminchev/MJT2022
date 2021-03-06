package bg.sofia.uni.fmi.mjt.spotify.playable;

public abstract class AbstractPlayable implements Playable {
    private final String title;
    private final String artist;
    private final int year;
    private final double duration;
    protected int totalPlays;

    public AbstractPlayable(String title, String artist, int year, double duration) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.duration = duration;
        totalPlays = 0;
    }

    @Override
    public int getTotalPlays() {
        return totalPlays;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getArtist() {
        return this.artist;
    }

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public double getDuration() {
        return this.duration;
    }
}
