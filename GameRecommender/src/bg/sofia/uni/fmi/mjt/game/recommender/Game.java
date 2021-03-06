package bg.sofia.uni.fmi.mjt.game.recommender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record Game(String name, String platform, LocalDate release_date,
                   String summary, double meta_score, double user_review) {

    private static final int LIMIT1 = 3;

    private static final String GAME_INFO_DELIMITER = ",";
    public static Game of(String line) {
        String[] infos = line.split(GAME_INFO_DELIMITER);
        return new Game(infos[0], infos[1], LocalDate.parse(infos[2],
                DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.US)),
                infos[LIMIT1], Double.parseDouble(infos[LIMIT1 + 1]), Double.parseDouble(infos[LIMIT1 + 2]));
    }
}
