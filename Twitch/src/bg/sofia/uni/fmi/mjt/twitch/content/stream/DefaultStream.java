package bg.sofia.uni.fmi.mjt.twitch.content.stream;

import bg.sofia.uni.fmi.mjt.twitch.content.Category;
import bg.sofia.uni.fmi.mjt.twitch.content.Metadata;
import bg.sofia.uni.fmi.mjt.twitch.user.User;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class DefaultStream implements Stream {

    private final Metadata metadata;
    private final LocalDateTime startTime;
    private int numberOfViews;


    public DefaultStream(Metadata metadata, Duration duration) {
        this.metadata = metadata;
        this.startTime = LocalDateTime.now();
        this.numberOfViews = 0;
    }

    public DefaultStream(String title, Category category, User user) {
        this.metadata = new Metadata(title, category, user);
        this.startTime = LocalDateTime.now();
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
        return Duration.between(this.startTime, LocalDateTime.now());
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
        this.numberOfViews--;
    }
}