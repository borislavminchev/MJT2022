package bg.sofia.uni.fmi.mjt.game.recommender;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Main {
    private static List<Game> readAndPrint(ZipFile zf, ZipEntry entry) {
        try (var stream = new BufferedReader(new InputStreamReader(zf.getInputStream(entry), StandardCharsets.UTF_8))) {
            return stream.lines().skip(1).map(Game::of).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try (ZipFile zf = new ZipFile("video-games-data.zip")) {

            for (Iterator<? extends ZipEntry> i = zf.entries().asIterator(); i.hasNext() ;) {
                ZipEntry file = i.next();
                GameRecommender recommender =
                        new GameRecommender(new InputStreamReader(zf.getInputStream(file), StandardCharsets.UTF_8));

                recommender.getAllGamesByPlatform().forEach((k, v) -> System.out.println(k + " : " + v.size()));
                System.out.println(recommender.getHighestUserRatedGameByPlatform("PC"));
//                recommender.getGamesSimilarTo("Assassin's").forEach(System.out::println);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
