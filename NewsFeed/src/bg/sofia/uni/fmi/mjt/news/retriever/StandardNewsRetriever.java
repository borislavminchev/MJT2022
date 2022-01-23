package bg.sofia.uni.fmi.mjt.news.retriever;

import bg.sofia.uni.fmi.mjt.news.parser.ResponseParser;
import bg.sofia.uni.fmi.mjt.news.response.ErrorResponse;
import bg.sofia.uni.fmi.mjt.news.response.NewsEntity;
import bg.sofia.uni.fmi.mjt.news.response.OKResponse;
import bg.sofia.uni.fmi.mjt.news.uribuilder.UriBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

public class StandardNewsRetriever implements NewsRetriever {

    private UriBuilder uri;
    private List<NewsEntity> news;

    private StandardNewsRetriever() { }

    private StandardNewsRetriever(UriBuilder uri) {
        if (uri == null) {
            throw new RuntimeException("Uri cannot be null");
        }
        this.uri = uri;
    }

    public static NewsRetriever createDefault() {
        UriBuilder uri = UriBuilder.newBuilder();
        return new StandardNewsRetriever(uri);
    }

    public static NewsRetriever create(UriBuilder uri) {
        return new StandardNewsRetriever(uri);
    }

    @Override
    public UriBuilder where() {
        return UriBuilder.newBuilder();
    }

    @Override
    public NewsRetriever then() throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> str = client.send(HttpRequest.newBuilder(uri.build()).build(),
                HttpResponse.BodyHandlers.ofString());

        OKResponse r = ResponseParser.createNew(str).parseTo(OKResponse.class).orElseThrow(() -> {
            throw new RuntimeException(ResponseParser.createNew(str).parseTo(ErrorResponse.class).get().getMessage());
        });

        this.news = r.getArticles();
        return this;
    }
    @Override
    public Collection<NewsEntity> retrieveTopN(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N cannot be negative");
        } else if (this.news == null) {
            throw new RuntimeException("There isn't any news to retrieve");
        }
        return this.news.stream().limit(n).toList();
    }

    @Override
    public Collection<NewsEntity> retrieveTopN(int n, String filePathToSave) {
        Collection<NewsEntity> news = this.retrieveTopN(n);
        int num = 1;

        for (NewsEntity entity : news) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathToSave, true))) {
                writer.write( num + ") " + formatEntity(entity));
                num++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return news;
    }

    private String formatEntity(NewsEntity entity) {
        return new StringBuilder().append(entity.getSource().getName() + "\n")
                .append(entity.getAuthor())
                .append(" - published at: " + entity.getPublishedAt())
                .append("\n" + entity.getTitle() + "\n")
                .append("\t description: " + entity.getDescription() + "\n")
                .append(entity.getContent() + "\n\n")
                .append(entity.getUrl() + "\n")
                .append(entity.getUrlToImage() + "\n")
                .append("\n\n").toString();
    }
}
