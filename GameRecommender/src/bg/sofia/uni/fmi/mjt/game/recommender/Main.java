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
        try (var stream =new BufferedReader(new InputStreamReader(zf.getInputStream(entry), StandardCharsets.UTF_8))) {
            return stream.lines().skip(1).map(Game::of).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try (ZipFile zf = new ZipFile("src/video-games-data.zip")) {

            for (Iterator<? extends ZipEntry> i = zf.entries().asIterator(); i.hasNext() ;) {
                ZipEntry file = i.next();
//                InputStream stream = zf.getInputStream(file);
                List<Game> games = readAndPrint(zf, file);
                System.out.println(games.get(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
