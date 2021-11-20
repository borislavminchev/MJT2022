package bg.sofia.uni.fmi.mjt.twitch.content.video;

import bg.sofia.uni.fmi.mjt.twitch.content.Category;
import bg.sofia.uni.fmi.mjt.twitch.content.ContentBase;
import bg.sofia.uni.fmi.mjt.twitch.content.Metadata;
import bg.sofia.uni.fmi.mjt.twitch.user.User;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStatus;

import java.time.Duration;

public class Video extends ContentBase {
    public Video(String title, Category category, User user, Duration duration) {
        super(title, category, user, duration);
    }

    public Video(Metadata metadata, Duration duration) {
        super(metadata, duration);
    }

    @Override
    public void stopWatching(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setStatus(UserStatus.ONLINE);
    }

}
