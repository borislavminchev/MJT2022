package bg.sofia.uni.fmi.mjt.twitch.content.video;

import bg.sofia.uni.fmi.mjt.twitch.content.Content;
import bg.sofia.uni.fmi.mjt.twitch.content.Metadata;
import bg.sofia.uni.fmi.mjt.twitch.user.User;

import java.time.Duration;

public class Video implements Content {
    @Override
    public Metadata getMetadata() {
        return null;
    }

    @Override
    public Duration getDuration() {
        return null;
    }

    @Override
    public void startWatching(User user) {

    }

    @Override
    public void stopWatching(User user) {

    }

    @Override
    public int getNumberOfViews() {
        return 0;
    }
}
