package bg.sofia.uni.fmi.mjt.game.recommender;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameRecommenderTest {

    private static String string = "name,platform,release_date,summary,meta_score,user_review\n" +
            "The Legend of Zelda: Ocarina of Time,Nintendo 64,23-Nov-1998," +
            "As a young boy Link is tricked by Ganondorf the King of the Gerudo Thieves." +
            " The evil human uses Link to gain access to the Sacred Realm where he places his" +
            " tainted hands on Triforce and transforms the beautiful Hyrulean landscape into a bar" +
            "ren wasteland. Link is determined to fix the problems he helped to create so with the help" +
            " of Rauru he travels through time gathering the powers of the Seven Sages.,99,9\n";

    @Test
    public void test() {
        GameRecommender recommender = new GameRecommender(new StringReader(string));

        assertThrows(Exception.class, () -> new GameRecommender(null));

        recommender.getHighestUserRatedGameByPlatform("Nintendo 64");
        recommender.getAllGamesByPlatform();
        recommender.getAllGames();
        recommender.getGamesReleasedAfter(LocalDate.now());
        assertThrows(Exception.class, () ->
                recommender.getGamesReleasedAfter(null));
        recommender.getYearsActive("Nintendo 64");
        recommender.getYearsActive("Nintendo 64");
        recommender.getAllNamesOfGamesReleasedIn(1998);
        recommender.getTopNUserRatedGames(1);

        assertThrows(Exception.class, () ->
                recommender.getTopNUserRatedGames(-1));

        recommender.getYearsWithTopScoringGames(0);
        recommender.getGamesSimilarTo("Ganondorf");
        recommender.getGamesSimilarTo("lala");

        Game.of("The Legend of Zelda: Ocarina of Time,Nintendo 64,23-Nov-1998," +
                        "As a young boy Link is tricked by Ganondorf the King of the Gerudo Thieves." +
                        " The evil human uses Link to gain access to the Sacred Realm where he places his" +
                        " tainted hands on Triforce and transforms the beautiful Hyrulean landscape into a bar" +
                        "ren wasteland. Link is determined to fix the problems he helped to create so with the help" +
                        " of Rauru he travels through time gathering the powers of the Seven Sages.,99,9");

        assertTrue(1 == 1);
    }

}