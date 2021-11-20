package bg.sofia.uni.fmi.mjt.twitch.content.stream;

import bg.sofia.uni.fmi.mjt.twitch.content.Category;
import bg.sofia.uni.fmi.mjt.twitch.content.ContentBase;
import bg.sofia.uni.fmi.mjt.twitch.content.Metadata;
import bg.sofia.uni.fmi.mjt.twitch.user.User;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class Stream extends ContentBase {

    private LocalDateTime startTime;

    public Stream(String title, Category category, User user) {
        super(title, category, user);
        this.startTime = LocalDateTime.now();
    }

    public Stream(Metadata metadata, Duration duration) {
        super(metadata, duration);
    }

    @Override
    public void stopWatching(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setStatus(UserStatus.ONLINE);
        this.numberOfViews--;
    }

    @Override
    public Duration getDuration() {
        return Duration.between(this.startTime, LocalDateTime.now());
    }
}
