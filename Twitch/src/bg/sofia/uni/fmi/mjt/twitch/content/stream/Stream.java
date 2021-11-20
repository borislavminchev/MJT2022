package bg.sofia.uni.fmi.mjt.twitch.content.stream;

import bg.sofia.uni.fmi.mjt.twitch.content.Category;
import bg.sofia.uni.fmi.mjt.twitch.content.ContentBase;
import bg.sofia.uni.fmi.mjt.twitch.content.Metadata;
import bg.sofia.uni.fmi.mjt.twitch.user.User;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStatus;

import java.time.Duration;

public class Stream extends ContentBase {

    public Stream(String title, Category category, User user, Duration duration) {
        super(title, category, user, duration);
        super.numberOfViews = 0;
    }

    public Stream(Metadata metadata, Duration duration) {
        super(metadata, duration);
        super.numberOfViews = 0;
    }

    @Override
    public void stopWatching(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setStatus(UserStatus.ONLINE);
        this.numberOfViews--;
    }
}
