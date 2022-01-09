package bg.sofia.uni.fmi.mjt.boardgames;

import java.util.Collection;
import java.util.List;

public class BoardGameInfoExtractor {
    private static final int NUM_ARGUMENTS = 9;
    private static final String INSIDE_ARGUMENT_DELIMITER = ",";
    private int idIdx;
    private int nameIdx;
    private int descriptionIdx;
    private int maxPlayersIdx;
    private int minAgeIdx;
    private int minPlayersIdx;
    private int playingTimeIdx;
    private int categoriesIdx;
    private int mechanicsIdx;


    BoardGameInfoExtractor(List<String> argumentNames) {
        if (argumentNames.size() != NUM_ARGUMENTS) {
            throw new RuntimeException("Error in first line arguments (too few)");
        }

        for (int i = 0; i < NUM_ARGUMENTS; i++) {
            switch (argumentNames.get(i)) {
                case "id" -> this.idIdx = i;
                case "details.maxplayers" -> this.maxPlayersIdx = i;
                case "details.minage" -> this.minAgeIdx = i;
                case "details.minplayers" -> this.minPlayersIdx = i;
                case "details.name" -> this.nameIdx = i;
                case "details.playingtime" -> this.playingTimeIdx = i;
                case "attributes.boardgamecategory" -> this.categoriesIdx = i;
                case "attributes.boardgamemechanic" -> this.mechanicsIdx = i;
                case "details.description" -> this.descriptionIdx = i;
            }
        }
    }

    public int extractId(List<String> arguments) {
        String s = arguments.get(this.idIdx);
        return Integer.parseInt(s);
    }

    public String extractName(List<String> arguments) {
        return arguments.get(this.nameIdx);
    }

    public String extractDescription(List<String> arguments) {
        return arguments.get(this.descriptionIdx);
    }

    public int extractMaxPlayers(List<String> arguments) {
        String s = arguments.get(this.maxPlayersIdx);
        return Integer.parseInt(s);
    }

    public int extractMinAge(List<String> arguments) {
        String s = arguments.get(this.minAgeIdx);
        return Integer.parseInt(s);
    }

    public int extractMinPlayers(List<String> arguments) {
        String s = arguments.get(this.minPlayersIdx);
        return Integer.parseInt(s);
    }

    public int extractPlayingTime(List<String> arguments) {
        String s = arguments.get(this.playingTimeIdx);
        return Integer.parseInt(s);
    }

    public Collection<String> extractCategories(List<String> arguments) {
        String s = arguments.get(this.categoriesIdx);
        return List.of(s.split(INSIDE_ARGUMENT_DELIMITER));
    }

    public Collection<String> extractMechanics(List<String> arguments) {
        String s = arguments.get(this.mechanicsIdx);
        return List.of(s.split(INSIDE_ARGUMENT_DELIMITER));
    }

}
