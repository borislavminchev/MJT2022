package bg.sofia.uni.fmi.mjt.boardgames.analyzer;

import bg.sofia.uni.fmi.mjt.boardgames.recommender.BoardGamesRecommender;
import bg.sofia.uni.fmi.mjt.boardgames.recommender.Recommender;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardGamesStatisticsAnalyzerTest {

    private final StatisticsAnalyzer analyzer;

    BoardGamesStatisticsAnalyzerTest() {
        Recommender recommender = new BoardGamesRecommender(Path.of("testdata/data.zip"),
                "data.txt",
                Path.of("testdata/stopwords.txt"));
        this.analyzer = new BoardGamesStatisticsAnalyzer(recommender.getGames());
    }

    @Test
    public void testConstructing() {
        Recommender recommender = new BoardGamesRecommender(Path.of("testdata/data.zip"),
                "data.txt",
                Path.of("testdata/stopwords.txt"));
        assertDoesNotThrow(() -> new BoardGamesStatisticsAnalyzer(recommender.getGames()));
        assertThrows(IllegalArgumentException.class , () -> new BoardGamesStatisticsAnalyzer(null));
    }

    @Test
    public void testAverageMinAge() {
        final int expected = 11;
        assertEquals(expected, Math.floor(this.analyzer.getAverageMinAge()));
    }

    @Test
    public void testZeroAverageMinAge() {
        StatisticsAnalyzer statisticsAnalyzer = new BoardGamesStatisticsAnalyzer(new ArrayList<>());
        assertEquals(0, statisticsAnalyzer.getAverageMinAge());
    }

    @Test
    public void testAveragePlayingTimeByCategory() {
        final double expected = 52.25;
        assertEquals(expected, this.analyzer.getAveragePlayingTimeByCategory("Card Game"));
    }

    @Test
    public void testZeroAveragePlayingTimeByCategory() {
        StatisticsAnalyzer statisticsAnalyzer = new BoardGamesStatisticsAnalyzer(new ArrayList<>());
        assertEquals(0, statisticsAnalyzer.getAveragePlayingTimeByCategory("not included"));
        assertEquals(0, statisticsAnalyzer.getAveragePlayingTimeByCategory(null));
    }

    @Test
    public void testAveragePlayingTimeByCategoryMap() {
        Map<String, Double> map = analyzer.getAveragePlayingTimeByCategory();
        final double expected = 52.25;
        assertTrue(map.containsKey("Card Game"));
        assertEquals(expected, map.get("Card Game"));
    }

    @Test
    public void testNMostPopularCategories() {
        final int numberCategories = 3;
        List<String> mostPopular = analyzer.getNMostPopularCategories(numberCategories);
        final double expected = 52.25;
        assertEquals("Wargame" , mostPopular.get(0));
        assertEquals("Card Game" , mostPopular.get(1));

        assertThrows(IllegalArgumentException.class, () -> analyzer.getNMostPopularCategories(0));
        assertThrows(IllegalArgumentException.class, () -> analyzer.getNMostPopularCategories(-1));
    }

}