package bg.sofia.uni.fmi.mjt.boardgames.analyzer;

import bg.sofia.uni.fmi.mjt.boardgames.BoardGame;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BoardGamesStatisticsAnalyzer implements StatisticsAnalyzer {

    BoardGamesStatisticsAnalyzer(Collection<BoardGame> games) { }

    @Override
    public List<String> getNMostPopularCategories(int n) {
        return null;
    }

    @Override
    public double getAverageMinAge() {
        return 0;
    }

    @Override
    public double getAveragePlayingTimeByCategory(String category) {
        return 0;
    }

    @Override
    public Map<String, Double> getAveragePlayingTimeByCategory() {
        return null;
    }
}
