package bg.sofia.uni.fmi.mjt.news.uribuilder;

import bg.sofia.uni.fmi.mjt.news.retriever.NewsRetriever;
import bg.sofia.uni.fmi.mjt.news.retriever.StandardNewsRetriever;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UriBuilder {
    private static final String API_KEY = "8d1153d6a324436e8690e4ed521d3716";
    private CountryCode country;
    private NewsCategory category;
    private List<String> keywords;

    public UriBuilder() { }

    public static UriBuilder newBuilder() {
        return new UriBuilder();
    }

    public UriBuilder country(CountryCode country) {
        if (country == null) {
            throw new RuntimeException("Country to set cannot be null");
        }
        this.country = country;
        return this;
    }

    public UriBuilder category(NewsCategory category) {
        if (category == null) {
            throw new RuntimeException("Category to set cannot be null");
        }
        this.category = category;
        return this;
    }

    public UriBuilder keywords(String... keywords) {
        if (keywords == null) {
            throw new RuntimeException("Trying to set wrong keywords list(empty or null)");
        }
        List<String> words = Stream.of(keywords).filter(i -> i != null).filter(i -> !i.isEmpty()).distinct().toList();
        if (words.isEmpty()) {
            throw new RuntimeException("Trying to set wrong keywords list(empty or null)");
        }
        this.keywords = new ArrayList<>(words);
        return this;
    }

    public URI build() throws URISyntaxException {
        if (this.keywords == null || this.keywords.isEmpty()) {
            throw new RuntimeException("Keywords field was not set");
        }
        return new URI("https", "newsapi.org", "/v2/top-headlines",
                this.getQuery(), null);
    }

    public NewsRetriever retrieve() {
        return StandardNewsRetriever.create(this);
    }

    public String getQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append(country != null ? "country=" + this.country.getValue() + "&" : "")
                .append(category != null ? "category=" + this.category.getValue() + "&" : "")
                .append((keywords != null && !keywords.isEmpty()) ? "q=" +
                        keywords.stream().collect(Collectors.joining("+")) + "&" : "")
                .append("apiKey=" + API_KEY);
        return builder.toString();
    }
}
