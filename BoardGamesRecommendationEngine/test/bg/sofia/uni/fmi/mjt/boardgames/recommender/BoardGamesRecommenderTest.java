package bg.sofia.uni.fmi.mjt.boardgames.recommender;

import bg.sofia.uni.fmi.mjt.boardgames.BoardGame;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class BoardGamesRecommenderTest {
    private final Recommender recommender = new BoardGamesRecommender(Path.of("data.zip"),
            "data.csv",
            Path.of("stopwords.txt"));

    @Test
    void testConstructingFirstConstructor() {
        assertDoesNotThrow(() -> new BoardGamesRecommender(Path.of("data.zip"),
                "data.csv",
                Path.of("stopwords.txt")));
        assertThrows(RuntimeException.class, () -> new BoardGamesRecommender(Path.of("data.zip"),
                "data123.csv",
                Path.of("stopwords.txt")));

        assertThrows(IllegalArgumentException.class, () ->
                new BoardGamesRecommender(null, "data.csv", Path.of("stopwords.txt")));
        assertThrows(IllegalArgumentException.class, () ->
                new BoardGamesRecommender(Path.of("data.zip"), "data.csv", null));
        assertThrows(IllegalArgumentException.class, () ->
                new BoardGamesRecommender(Path.of("data.zip"), "", null));
    }

    @Test
    void testConstructingSecondConstructor() throws FileNotFoundException {
        InputStreamReader dataReader = new InputStreamReader(new FileInputStream("data.csv"));
        InputStreamReader stopwordReader = new InputStreamReader(new FileInputStream("stopwords.txt"));

        Recommender recom = new BoardGamesRecommender(dataReader, stopwordReader);

        assertFalse(recom.getGames().isEmpty());
        assertEquals(this.recommender.getGames().size(), recom.getGames().size());
        assertThrows(IllegalArgumentException.class, () ->
                new BoardGamesRecommender(null, null));
    }

    @Test
    void testAreLoaded() {
        assertFalse(this.recommender.getGames().isEmpty());
    }

    @Test
    void testStoreGamesIndex() {
        StringWriter writer = new StringWriter();
        this.recommender.storeGamesIndex(writer);
        final String testResultStart = """
                half: 22, 103
                spilling: 33
                hall: 67
                """;
        assertFalse(writer.toString().isEmpty());
        assertTrue(writer.toString().startsWith(testResultStart));
        assertThrows(IllegalArgumentException.class, () -> this.recommender.storeGamesIndex(null));

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetByDescriptionOneWord() {
        List<BoardGame> wantedGames = this.recommender.getByDescription("Die");

        assertEquals(2, wantedGames.size());
        assertEquals("Die Macher", wantedGames.get(0).name());
        assertEquals("Catan", wantedGames.get(1).name());
    }

    @Test
    void testGetByDescriptionStopWords() {
        List<BoardGame> wantedGames = this.recommender.getByDescription("Die", "and", "card");

        final int expectedSize = 34;
        assertEquals(expectedSize, wantedGames.size());
        assertEquals("Elfenland", wantedGames.get(0).name());
    }

    @Test
    void testGetByDescriptionTwoWords() {
        List<BoardGame> wantedGames = this.recommender.getByDescription("Die", "card");
        List<BoardGame> sameGames = this.recommender.getByDescription("card", "Die");

        final int expectedSize = 34;
        assertEquals(expectedSize, wantedGames.size());
        assertEquals("Elfenland", wantedGames.get(0).name());
        assertEquals(expectedSize, sameGames.size());
        assertEquals("Elfenland", sameGames.get(0).name());

        assertFalse(wantedGames.stream()
                .filter(i -> i.name().equals("Die Macher"))
                .collect(Collectors.toList())
                .isEmpty());
    }

    @Test
    void testGetByDescriptionNullValues() {
        List<BoardGame> wantedGames = this.recommender.getByDescription(null, "Die", null);

        assertEquals(2, wantedGames.size());
        assertEquals("Die Macher", wantedGames.get(0).name());
        assertEquals("Catan", wantedGames.get(1).name());
        assertThrows(IllegalArgumentException.class, () -> this.recommender.getByDescription(null).isEmpty());
        assertTrue(this.recommender.getByDescription(null, null).isEmpty());
    }


    @Test
    void testSimilarTo() {
        BoardGame game = new ArrayList<>(recommender.getGames()).get(0);
        final int limit = 10;

        List<BoardGame> similarGames = this.recommender.getSimilarTo(game, limit);
        assertFalse(similarGames.isEmpty());
        assertFalse(similarGames.contains(game));
        assertEquals("Twilight Imperium", similarGames.get(0).name());
        assertEquals("Schoko & Co.", similarGames.get(1).name());
    }

    @Test
    void testSimilarToNegative() {
        BoardGame game = new ArrayList<>(recommender.getGames()).get(0);
        assertThrows(IllegalArgumentException.class, () -> this.recommender.getSimilarTo(game, -1));
    }

    @Test
    void testSimilarToNull() {
        final int limit = 10;
        assertThrows(IllegalArgumentException.class, () -> this.recommender.getSimilarTo(null, limit));
    }
}