package bg.sofia.uni.fmi.mjt.boardgames.recommender;

import bg.sofia.uni.fmi.mjt.boardgames.BoardGame;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class BoardGamesRecommenderTest {

    public static final int LIMIT = 10;

    private final Recommender recommender =  new BoardGamesRecommender(Path.of("testdata/data.zip"),
            "data.txt",
            Path.of("testdata/stopwords.txt"));

    @Test
    void testConstructingFirstConstructor() {
        assertDoesNotThrow(() -> new BoardGamesRecommender(Path.of("testdata/data.zip"),
                "data.txt",
                Path.of("testdata/stopwords.txt")));
        assertThrows(RuntimeException.class, () -> new BoardGamesRecommender(Path.of("testdata/data.zip"),
                "data123.txt",
                Path.of("testdata/stopwords.txt")));
    }

    @Test
    void testConstructingSecondConstructor() throws FileNotFoundException {
        InputStreamReader dataReader = new InputStreamReader(new FileInputStream("testdata/datafile.txt"));
        InputStreamReader stopwordReader = new InputStreamReader(new FileInputStream("testdata/stopwords.txt"));

        Recommender recom =  new BoardGamesRecommender(dataReader, stopwordReader);

        assertFalse(recom.getGames().isEmpty());
        assertEquals(this.recommender.getGames().size(), recom.getGames().size());
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

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test() {
        BoardGame g = new ArrayList<>(recommender.getGames()).get(0);

        this.recommender.getByDescription("Die");
        this.recommender.getSimilarTo(g, LIMIT);
    }
}