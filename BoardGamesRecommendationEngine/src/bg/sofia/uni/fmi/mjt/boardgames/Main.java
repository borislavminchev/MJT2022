package bg.sofia.uni.fmi.mjt.boardgames;

import bg.sofia.uni.fmi.mjt.boardgames.analyzer.BoardGamesStatisticsAnalyzer;
import bg.sofia.uni.fmi.mjt.boardgames.analyzer.StatisticsAnalyzer;
import bg.sofia.uni.fmi.mjt.boardgames.recommender.BoardGamesRecommender;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Main {
    public static final int LIMIT = 10;

    public static void main(String[] args) {
//        BoardGamesRecommender recommender = new BoardGamesRecommender(Path.of("data/data.zip"),
//                "data.csv",
//                Path.of("data/stopwords.txt") );
//        StatisticsAnalyzer analyzer = new BoardGamesStatisticsAnalyzer(recommender.getGames());
//        BoardGame g = new ArrayList<>(recommender.getGames()).get(0);
//        System.out.println(recommender.getByDescription("Die")
//                .stream().limit(LIMIT).map(i -> i.toString()).collect(Collectors.joining("\n")));


        try (ZipFile zipFile = new ZipFile("data/data.zip")) {
            ZipEntry entry = zipFile.getEntry("data.csv");

            try (BufferedReader r = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
                    FileWriter writer = new FileWriter("data.csv", true)) {

                r.lines().limit(LIMIT).forEach(i -> {
                    try {
                        writer.write(i + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
