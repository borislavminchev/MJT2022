package bg.sofia.uni.fmi.mjt.twitch;

import bg.sofia.uni.fmi.mjt.twitch.content.Category;
import bg.sofia.uni.fmi.mjt.twitch.content.Content;
import bg.sofia.uni.fmi.mjt.twitch.content.stream.Stream;
import bg.sofia.uni.fmi.mjt.twitch.content.video.Video;
import bg.sofia.uni.fmi.mjt.twitch.user.User;
import bg.sofia.uni.fmi.mjt.twitch.user.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStatus;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStreamingException;
import bg.sofia.uni.fmi.mjt.twitch.user.service.UserService;

import java.util.*;

public class Twitch implements StreamingPlatform {
    private final UserService userService;
    private final UserContentService ownersContent;

    Twitch(UserService userService) {
        this.userService = userService;
        ownersContent = new UserContentService();
    }

    @Override
    public Stream startStream(String username, String title, Category category)
            throws UserNotFoundException, UserStreamingException {

        if (title == null || title.isEmpty() || category == null) {
            throw new IllegalArgumentException("Invalid arguments passed");
        }

        User user = getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User was not found");
        }

        if (user.getStatus() == UserStatus.STREAMING) {
            throw new UserStreamingException("User is currently streaming");
        }

        user.setStatus(UserStatus.STREAMING);

        Stream stream = new Stream(title, category, user);
        ownersContent.newContent(stream, username);
        return stream;
    }

    @Override
    public Video endStream(String username, Stream stream) throws UserNotFoundException, UserStreamingException {
        if (stream == null) {
            throw new IllegalArgumentException("Stream cannot be null");
        }

        User user = getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User was not found");
        }

        if (user.getStatus() != UserStatus.STREAMING) {
            throw new UserStreamingException("User is currently not streaming");
        }

        user.setStatus(UserStatus.OFFLINE);

        Video video = new Video(stream.getMetadata(), stream.getDuration());
        this.ownersContent.removeContent(stream, username);
        this.ownersContent.newContent(video, username);

        return video;
    }

    @Override
    public void watch(String username, Content content) throws UserNotFoundException, UserStreamingException {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }

        User user = getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User was not found");
        }

        if (user.getStatus() == UserStatus.STREAMING) {
            throw new UserStreamingException("User is currently streaming. Cannot watch another content");
        }

        content.startWatching(user);
        this.ownersContent.registerWatch(username, content);
    }

    @Override
    public User getMostWatchedStreamer() {
        return getUserByUsername(this.ownersContent.getMostPopularUser());
    }

    @Override
    public Content getMostWatchedContent() {
        return getMostWatched(this.ownersContent.getAllContent());
    }

    @Override
    public Content getMostWatchedContentFrom(String username) throws UserNotFoundException {
        return getMostWatched(this.ownersContent.getContentOfUser(username));
    }

    @Override
    public List<Category> getMostWatchedCategoriesBy(String username) throws UserNotFoundException {
        List<Content> userContent = new ArrayList<>(List.copyOf(this.ownersContent.getAllWatchedContentBy(username)));
        Collections.sort(userContent, Collections.reverseOrder(new Comparator<>() {
            @Override
            public int compare(Content o1, Content o2) {
                return Integer.compare(o1.getNumberOfViews(), o2.getNumberOfViews());
            }
        }));

        Set<Category> mostWatchedCategory = new LinkedHashSet<>();

        for (Content content : userContent) {
            mostWatchedCategory.add(content.getMetadata().category());
            if (mostWatchedCategory.size() == Category.values().length) {
                break;
            }
        }

        return List.copyOf(mostWatchedCategory);
    }

    private User getUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is either null or empty");
        }

        return this.userService.getUsers().get(username);
    }

    private Content getMostWatched(Set<Content> contentSet) {
        Content mostWatched = null;

        for (Content content : contentSet) {
            if (mostWatched == null || mostWatched.getNumberOfViews() < content.getNumberOfViews()) {
                mostWatched = content;
            }
        }
        return mostWatched;
    }
}
