package bg.sofia.uni.fmi.mjt.twitch;

import bg.sofia.uni.fmi.mjt.twitch.content.Category;
import bg.sofia.uni.fmi.mjt.twitch.content.Content;
import bg.sofia.uni.fmi.mjt.twitch.content.stream.Stream;
import bg.sofia.uni.fmi.mjt.twitch.content.video.Video;
import bg.sofia.uni.fmi.mjt.twitch.user.User;
import bg.sofia.uni.fmi.mjt.twitch.user.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStreamingException;
import bg.sofia.uni.fmi.mjt.twitch.user.service.UserService;

import java.util.List;

public class Twitch implements StreamingPlatform {
    private final UserService userService;

    Twitch(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Stream startStream(String username, String title, Category category) throws UserNotFoundException, UserStreamingException {
        return null;
    }

    @Override
    public Video endStream(String username, Stream stream) throws UserNotFoundException, UserStreamingException {
        return null;
    }

    @Override
    public void watch(String username, Content content) throws UserNotFoundException, UserStreamingException {

    }

    @Override
    public User getMostWatchedStreamer() {
        return null;
    }

    @Override
    public Content getMostWatchedContent() {
        return null;
    }

    @Override
    public Content getMostWatchedContentFrom(String username) throws UserNotFoundException {
        return null;
    }

    @Override
    public List<Category> getMostWatchedCategoriesBy(String username) throws UserNotFoundException {
        return null;
    }

    private User getUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is either null or empty");
        }

        return this.userService.getUsers().get(username);
    }
}
