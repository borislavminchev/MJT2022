package bg.sofia.uni.fmi.mjt.spotify.playable;

public class Audio implements Playable {
    private static int totalPlays = 0;
    private final String title;
    private final String artist;
    private final int year;
    private final double duration;

    public Audio(String title, String artist, int year, double duration) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.duration = duration;
    }

    @Override
    public String play() {
        totalPlays++;
        return "Currently playing" + "AUDIO" + "content: " + "<content_name>";
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
