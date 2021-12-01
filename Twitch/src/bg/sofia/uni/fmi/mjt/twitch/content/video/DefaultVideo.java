package bg.sofia.uni.fmi.mjt.twitch.content.video;

import bg.sofia.uni.fmi.mjt.twitch.content.Category;
import bg.sofia.uni.fmi.mjt.twitch.content.Metadata;
import bg.sofia.uni.fmi.mjt.twitch.user.User;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStatus;

import java.time.Duration;

public class DefaultVideo implements Video {
    private final Metadata metadata;
    protected int numberOfViews;
    private final Duration duration;

    public DefaultVideo(String title, Category category, User user, Duration duration) {
        this.metadata = new Metadata(title, category, user);
        this.duration = duration;
        this.numberOfViews = 0;
    }

    public DefaultVideo(Metadata metadata, Duration duration) {
        this.metadata = metadata;
        this.duration = duration;
        this.numberOfViews = 0;
    }

    @Override
    public void startWatching(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setStatus(UserStatus.OFFLINE);
        this.numberOfViews++;
    }

    @Override
    public Metadata getMetadata() {
        return this.metadata;
    }

    @Override
    public Duration getDuration() {
        return this.duration;
    }

    @Override
    public int getNumberOfViews() {
        return this.numberOfViews;
    }

    @Override
    public void stopWatching(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setStatus(UserStatus.OFFLINE);
    }
}
