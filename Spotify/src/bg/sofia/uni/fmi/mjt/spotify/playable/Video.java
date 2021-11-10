package bg.sofia.uni.fmi.mjt.spotify.playable;

public class Video extends AbstractPlayable {

    public Video(String title, String artist, int year, double duration) {
        super(title, artist, year, duration);
    }

    @Override
    public String play() {
        super.totalPlays++;
        return "Currently playing" + "VIDEO" + "content: " + super.getTitle() + " - " + super.getArtist();
    }

}
