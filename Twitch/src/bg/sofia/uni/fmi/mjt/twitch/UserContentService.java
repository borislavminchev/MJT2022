package bg.sofia.uni.fmi.mjt.twitch;

import bg.sofia.uni.fmi.mjt.twitch.content.Content;
import bg.sofia.uni.fmi.mjt.twitch.user.UserNotFoundException;

import java.util.*;

public class UserContentService {
    Map<String, Set<Content>> ownersContent;
    Map<String, List<Content>> watcherContent;

    public UserContentService() {
        this.ownersContent = new HashMap<>();
        this.watcherContent = new HashMap<>();
    }

    public void newContent(Content content, String username) {
        if (content == null || username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        if (this.ownersContent.containsKey(username)) {
            this.ownersContent.get(username).add(content);
        } else {
            this.ownersContent.put(username, new LinkedHashSet<>(List.of(content)));
        }
    }

    public void removeContent(Content content, String username) {
        if (content == null || username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        this.ownersContent.get(username).remove(content);
    }

    public Set<Content> getContentOfUser(String username) throws UserNotFoundException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (!this.ownersContent.containsKey(username)) {
            throw new UserNotFoundException("User with username:" + username + "was not found");
        }
        return Set.copyOf(this.ownersContent.get(username));
    }

    public String getMostPopularUser() {
        String mostPopularUser = null;
        long userViews = 0;

        for (Map.Entry<String, Set<Content>> currentContents : this.ownersContent.entrySet()) {
            if (userViews <= getAllViews(currentContents.getValue())) {
                mostPopularUser = currentContents.getKey();
            }
        }

        return mostPopularUser;
    }

    public Set<Content> getAllContent() {
        Set<Content> allContent = new LinkedHashSet<>();
        for (Set<Content> contentSet : this.ownersContent.values()) {
            allContent.addAll(contentSet);
        }

        return Set.copyOf(allContent);
    }

    public void registerWatch(String username, Content content) {
        if (content == null || username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        if (this.watcherContent.containsKey(username)) {
            this.watcherContent.get(username).add(content);
        } else {
            this.watcherContent.put(username, new ArrayList<>(List.of(content)));
        }
    }

    public void removeWatchedContent(Content content) {
        for (Map.Entry<String, List<Content>> entry : this.watcherContent.entrySet()) {
            if (entry.getValue().contains(content)) {
                entry.getValue().removeAll(Collections.singletonList(content));
                this.watcherContent.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public List<Content> getAllWatchedContentBy(String username) throws UserNotFoundException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (!this.watcherContent.containsKey(username)) {
            throw new UserNotFoundException("User with username:" + username + "was not found");
        }

        return List.copyOf(this.watcherContent.get(username));
    }

    private long getAllViews(Set<Content> contentSet) {
        long allViews = 0;

        for (Content currentContent : contentSet) {
            allViews += currentContent.getNumberOfViews();
        }
        return allViews;
    }
}
