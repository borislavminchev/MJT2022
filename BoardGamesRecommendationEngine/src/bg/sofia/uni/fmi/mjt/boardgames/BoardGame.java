package bg.sofia.uni.fmi.mjt.boardgames;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record BoardGame(int id, String name, String description, int maxPlayers, int minAge, int minPlayers,
                        int playingTimeMins, Collection<String> categories, Collection<String> mechanics) {

    private static final String LINE_ARGUMENT_DELIMITER = ";";


    public static BoardGame of(String line, String argNameLine) {
        String[] infos = line.split(LINE_ARGUMENT_DELIMITER);

        BoardGameInfoExtractor extractor = new BoardGameInfoExtractor(
                List.of(argNameLine.split(LINE_ARGUMENT_DELIMITER)));

        return new BoardGame(extractor.extractId(List.of(infos)),
                extractor.extractName(List.of(infos)),
                extractor.extractDescription(List.of(infos)),
                extractor.extractMaxPlayers(List.of(infos)),
                extractor.extractMinAge(List.of(infos)),
                extractor.extractMinPlayers(List.of(infos)),
                extractor.extractPlayingTime(List.of(infos)),
                extractor.extractCategories(List.of(infos)),
                extractor.extractMechanics(List.of(infos)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardGame)) return false;
        BoardGame boardGame = (BoardGame) o;
        return maxPlayers == boardGame.maxPlayers
                && minAge == boardGame.minAge
                && minPlayers == boardGame.minPlayers
                && playingTimeMins == boardGame.playingTimeMins
                && name.equals(boardGame.name)
                && description.equals(boardGame.description)
                && categories.equals(boardGame.categories)
                && mechanics.equals(boardGame.mechanics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, maxPlayers,
                minAge, minPlayers, playingTimeMins, categories, mechanics);
    }
}