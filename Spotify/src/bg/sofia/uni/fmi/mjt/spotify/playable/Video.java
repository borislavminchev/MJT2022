package bg.sofia.uni.fmi.mjt.spotify.playable;

public class Video implements Playable {
    private static int totalPlays = 0;
    private final String title;
    private final String artist;
    private final int year;
    private final double duration;

    public Video(String title, String artist, int year, double duration) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.duration = duration;
    }

    @Override
    public String play() {
        totalPlays++;
        return "Currently playing" + "VIDEO" + "content: " + title + " - " + artist;
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
