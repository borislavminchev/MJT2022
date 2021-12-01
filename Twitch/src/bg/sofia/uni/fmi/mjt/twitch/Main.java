package bg.sofia.uni.fmi.mjt.twitch;

import bg.sofia.uni.fmi.mjt.twitch.content.Category;
import bg.sofia.uni.fmi.mjt.twitch.content.Content;
import bg.sofia.uni.fmi.mjt.twitch.content.stream.Stream;
import bg.sofia.uni.fmi.mjt.twitch.content.video.Video;
import bg.sofia.uni.fmi.mjt.twitch.user.DefaultUser;
import bg.sofia.uni.fmi.mjt.twitch.user.User;
import bg.sofia.uni.fmi.mjt.twitch.user.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.twitch.user.UserStreamingException;
import bg.sofia.uni.fmi.mjt.twitch.user.service.DefaultUserService;
import bg.sofia.uni.fmi.mjt.twitch.user.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws UserNotFoundException, UserStreamingException, InterruptedException {
        Map<String, User> map = new HashMap<>();
        map.put("go6o", new DefaultUser("Georgi"));
        map.put("bobbito", new DefaultUser("Bobby"));

        UserService users = new DefaultUserService(map);
        StreamingPlatform twitch = new Twitch(users);

        Stream s = twitch.startStream("go6o", "lo6o", Category.MUSIC);
        System.out.println(users.getUsers().get("go6o").getStatus());
        twitch.watch("bobbito", s);

        System.out.println(s.getNumberOfViews());
        Thread.sleep(1000);

        Content c1 = twitch.getMostWatchedContent();
        System.out.println(c1.getMetadata().title() + " " + c1.getNumberOfViews() + " " + c1.getClass().getSimpleName());

        Video v = twitch.endStream("go6o", s);

        twitch.watch("bobbito", v);

        Content c = twitch.getMostWatchedContent();
        User u = twitch.getMostWatchedStreamer();
        System.out.println(c.getMetadata().title() + " " + c.getNumberOfViews() + " " + c.getClass().getSimpleName());
        System.out.println(u.getName());
        System.out.println(twitch.getMostWatchedContentFrom("go6o").getMetadata());
        System.out.println(twitch.getMostWatchedCategoriesBy("bobbito"));

    }
}
