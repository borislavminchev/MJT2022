package bg.sofia.uni.fmi.mjt.twitch.content;

import bg.sofia.uni.fmi.mjt.twitch.user.User;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStatus;

import java.time.Duration;

public abstract class ContentBase implements Content {
    private final Metadata metadata;
    private Duration duration;
    protected int numberOfViews;

    public ContentBase(String title, Category category, User user, Duration duration) {
        this.metadata = new Metadata(title, category, user);
        this.duration = duration;
        this.numberOfViews = 0;
    }

    public ContentBase(Metadata metadata, Duration duration) {
        this.metadata = metadata;
        this.duration = duration;
        this.numberOfViews = 0;
    }

    public ContentBase(String title, Category category, User user) {
        this.metadata = new Metadata(title, category, user);
        this.duration = null;
        this.numberOfViews = 0;
    }
    public ContentBase(Metadata metadata) {
        this.metadata = metadata;
        this.duration = null;
        this.numberOfViews = 0;
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
    public void startWatching(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setStatus(UserStatus.WATCHING);
        this.numberOfViews++;
    }

    @Override
    public int getNumberOfViews() {
        return this.numberOfViews;
    }
}
