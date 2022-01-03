package bg.sofia.uni.fmi.mjt.boardgames;

import java.util.Collection;

public record BoardGame(int id, String name, String description, int maxPlayers, int minAge, int minPlayers,
                        int playingTimeMins, Collection<String> categories, Collection<String> mechanics) {
    // [...]
}