package bg.sofia.uni.fmi.mjt.boardgames.analyzer;

import bg.sofia.uni.fmi.mjt.boardgames.BoardGame;

import java.util.*;
import java.util.stream.Collectors;

public class BoardGamesStatisticsAnalyzer implements StatisticsAnalyzer {

    private final List<BoardGame> games;

    public BoardGamesStatisticsAnalyzer(Collection<BoardGame> games) {
        if (games == null) {
            throw new IllegalArgumentException("Games cannot be null");
        }

        this.games = new ArrayList<>(games);
    }

    @Override
    public List<String> getNMostPopularCategories(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Argument cannot be negative");
        }

        if (n == 0) {
            return new ArrayList<>();
        }

        Map<String, Integer> categoriesCount = new HashMap<>();
        for (BoardGame game : games) {
            List<String> categories = List.copyOf(game.categories());
            for (String category : categories) {
                if (categoriesCount.containsKey(category)) {
                    int count = categoriesCount.get(category);
                    categoriesCount.put(category, count + 1);
                } else {
                    categoriesCount.put(category, 1);
                }
            }
        }

        return categoriesCount.entrySet().stream()
                .sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue()))
                .limit(n)
                .map(i -> i.getKey())
                .toList();
    }

    @Override
    public double getAverageMinAge() {
        return this.games.stream()
                .mapToInt(i -> i.minAge())
                .average()
                .orElse(0);
    }

    @Override
    public double getAveragePlayingTimeByCategory(String category) {
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }

        return this.games.stream()
                .filter(i -> i.categories().contains(category))
                .mapToInt(i -> i.playingTimeMins())
                .average()
                .orElse(0);
    }

    @Override
    public Map<String, Double> getAveragePlayingTimeByCategory() {
        Set<String> categoriesSet = new HashSet<>();
        for (BoardGame game : this.games) {
            for (String category : game.categories()) {
                categoriesSet.add(category);
            }
        }

        return categoriesSet.stream()
                .collect(Collectors.toMap(i -> i, i -> getAveragePlayingTimeByCategory(i)));
    }
}
