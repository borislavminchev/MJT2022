package bg.sofia.uni.fmi.mjt.boardgames.recommender;

import bg.sofia.uni.fmi.mjt.boardgames.BoardGame;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class BoardGamesRecommender implements Recommender {

    /**
     * Constructs an instance using the provided file names.
     *
     * @param datasetZipFile  ZIP file containing the board games dataset file
     * @param datasetFileName the name of the dataset file (inside the ZIP archive)
     * @param stopwordsFile   the stopwords file
     */
    BoardGamesRecommender(Path datasetZipFile, String datasetFileName, Path stopwordsFile) { }

    /**
     * Constructs an instance using the provided Reader streams.
     *
     * @param dataset   Reader from which the dataset can be read
     * @param stopwords Reader from which the stopwords list can be read
     */
    BoardGamesRecommender(Reader dataset, Reader stopwords) { }

    @Override
    public Collection<BoardGame> getGames() {
        return null;
    }

    @Override
    public List<BoardGame> getSimilarTo(BoardGame game, int n) {
        return null;
    }

    @Override
    public List<BoardGame> getByDescription(String... keywords) {
        return null;
    }

    @Override
    public void storeGamesIndex(Writer writer) {

    }
}
