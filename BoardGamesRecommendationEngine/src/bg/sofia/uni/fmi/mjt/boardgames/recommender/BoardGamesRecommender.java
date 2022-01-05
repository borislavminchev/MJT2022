package bg.sofia.uni.fmi.mjt.boardgames.recommender;

import bg.sofia.uni.fmi.mjt.boardgames.BoardGame;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BoardGamesRecommender implements Recommender {


    private List<BoardGame> games;
    private List<String> stopwords;
    private Map<String, Set<Integer>> wordsGameIdx;

    /**
     * Constructs an instance using the provided file names.
     *
     * @param datasetZipFile  ZIP file containing the board games dataset file
     * @param datasetFileName the name of the dataset file (inside the ZIP archive)
     * @param stopwordsFile   the stopwords file
     */
    BoardGamesRecommender(Path datasetZipFile, String datasetFileName, Path stopwordsFile) {
        try (ZipFile zipFile = new ZipFile(datasetZipFile.toString())) {

            for (Iterator<? extends ZipEntry> it = zipFile.entries().asIterator(); it.hasNext(); ) {
                ZipEntry entry = it.next();

                if (!entry.isDirectory() && entry.getName().endsWith(datasetFileName)) {
                    this.games = loadGames(new InputStreamReader(zipFile.getInputStream(entry)));
                    this.stopwords = retriveStopwords(new FileReader(stopwordsFile.toString()));
                    this.wordsGameIdx = loadWords(this.games);
                    return;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs an instance using the provided Reader streams.
     *
     * @param dataset   Reader from which the dataset can be read
     * @param stopwords Reader from which the stopwords list can be read
     */
    BoardGamesRecommender(Reader dataset, Reader stopwords) {
        this.games = loadGames(dataset);
        this.stopwords = retriveStopwords(stopwords);
        this.wordsGameIdx = loadWords(this.games);
    }

    @Override
    public Collection<BoardGame> getGames() {
        return List.copyOf(this.games);
    }

    @Override
    public List<BoardGame> getSimilarTo(BoardGame game, int n) {
        return this.games.stream()
                .filter(i -> !game.equals(i))
                .sorted(Comparator.comparingDouble(g -> getSimilarityIndex(game, g)))
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardGame> getByDescription(String... keywords) {
        List<String> wordList = Stream.of(keywords)
                .filter(i -> !this.stopwords.contains(i))
                .distinct()
                .collect(Collectors.toList());

        Set<Integer> gamesIdx = new HashSet<>();
        this.wordsGameIdx.entrySet().stream()
                .filter(wordList::contains)
                .forEach(i -> gamesIdx.addAll(i.getValue()));

        return this.games.stream().filter(i -> gamesIdx.contains(i.id())).toList();
    }

    @Override
    public void storeGamesIndex(Writer writer) {
        this.wordsGameIdx.entrySet().forEach(i -> writeEntry(i, writer) );
    }

    private double getSimilarityIndex(BoardGame game1, BoardGame game2) {
        return getNumericValuesDistance(game1, game2)
                + getStringDistance(game1.categories(), game2.categories())
                + getStringDistance(game1.mechanics(), game2.mechanics());
    }

    private double getNumericValuesDistance(BoardGame game1, BoardGame game2) {
        return Math.sqrt(Math.pow(game1.playingTimeMins() - game2.playingTimeMins(), 2) +
                Math.pow(game1.maxPlayers() - game2.maxPlayers(), 2) +
                Math.pow(game1.minAge() - game2.minAge(), 2) +
                Math.pow(game1.minPlayers() - game2.minPlayers(), 2));
    }

    private double getStringDistance(Collection<String> list1, Collection<String> list2) {
        List<String> combined = Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());

        List<String>  common = list1.stream()
                .filter(list2::contains)
                .collect(Collectors.toList());

        return combined.size() - common.size();
    }

    private void writeEntry(Map.Entry<String, Set<Integer>> entry, Writer writer) {
        try (BufferedWriter bw = new BufferedWriter(writer)) {
            String idString = entry.getValue().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            bw.write(entry.getKey() + ": " + idString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<BoardGame> loadGames(Reader dataset) {
        List<BoardGame> games = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(dataset)) {
            String firstLine = reader.lines().limit(1).toList().get(0);

            games = reader.lines()
                    .skip(1)
                    .map(i->BoardGame.of(i, firstLine))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return games;
    }

    private Map<String, Set<Integer>> loadWords(List<BoardGame> boardGames) {
        Map<String, Set<Integer>> result = new HashMap<>();

        for (BoardGame game : boardGames) {
            Set<String> words = Stream.of(game.description().split("[\\p{IsPunctuation}\\p{IsWhite_Space}]+"))
                    .filter(i -> !this.stopwords.contains(i))
                    .collect(Collectors.toSet());

            for (String word : words) {
                if (result.containsKey(word)) {
                    Set<Integer> ids = result.get(word);
                    ids.add(game.id());
                    result.put(word, ids);
                } else {
                    result.put(word, new HashSet<>(Set.of(game.id())));
                }
            }
        }

        return result;
    }


    private List<String> retriveStopwords(Reader stopwordsReader) {
        List<String> stopwords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(stopwordsReader)) {
            stopwords = reader.lines()
                    .filter(i -> !i.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopwords;
    }
}
