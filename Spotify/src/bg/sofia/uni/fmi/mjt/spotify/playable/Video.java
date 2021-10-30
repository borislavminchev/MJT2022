package bg.sofia.uni.fmi.mjt.spotify.playable;

import bg.sofia.uni.fmi.mjt.spotify.playable.Playable;

public class Video implements Playable {
    private String title;
    private String artist;
    private int year;
    private double duration;

    private static int totalPlays = 0;

    public Video(String title, String artist, int year, double duration) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.duration = duration;
    }

    @Override
    public String play() {
        totalPlays++;
        return "Currently playing" + "VIDEO"  + "content: " + title + " - " + artist;
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
