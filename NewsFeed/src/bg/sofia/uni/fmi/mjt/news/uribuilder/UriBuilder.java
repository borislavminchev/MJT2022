package bg.sofia.uni.fmi.mjt.news.uribuilder;

import bg.sofia.uni.fmi.mjt.news.retriever.NewsRetriever;
import bg.sofia.uni.fmi.mjt.news.retriever.StandardNewsRetriever;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UriBuilder {
    private static final String API_KEY = "8d1153d6a324436e8690e4ed521d3716";
    private CountryCode country;
    private NewsCategory category;
    private String sources;
    private List<String> keywords;
    private Integer pageSize;
    private Integer page;

    public UriBuilder() {
        this.keywords = new ArrayList<>();
    }

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

    public UriBuilder sources(String sources) {
        if (sources == null || sources.isEmpty()) {
            throw new RuntimeException("Sources to set cannot be null or empty");
        }
        this.sources = sources;
        return this;
    }

    public UriBuilder keywords(String... keywords) {
        if (keywords == null) {
            throw new RuntimeException("Trying to set wrong keywords list(empty or null)");
        }
        this.keywords = new ArrayList<>(List.of(keywords));
        return this;
    }

    public UriBuilder pageSize(Integer pageSize) {
        final int maxPageSize = 100;
        if (pageSize == null) {
            throw new RuntimeException("Page size to set cannot be null");
        } else if (pageSize.intValue() <= 0 || pageSize.intValue() > maxPageSize) {
            throw new RuntimeException("Page size out of bounds");
        }
        this.pageSize = pageSize;
        return this;
    }

    public UriBuilder page(Integer page) {
        if (page == null) {
            throw new RuntimeException("Page size to set cannot be null");
        } else if (pageSize.intValue() <= 0) {
            throw new RuntimeException("Page size out of bounds");
        }
        this.page = page;
        return this;
    }

    public URI build() throws URISyntaxException {
        return new URI("https", "newsapi.org", "/v2/top-headlines",
                this.getQuery(), null);
    }

    public NewsRetriever retrieve() throws URISyntaxException {
        return StandardNewsRetriever.create(this.build());
    }

    private String getQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append(country != null ? "country=" + this.country.getValue() + "&" : "")
                .append(category != null ? "category=" + this.category.getValue() + "&" : "")
                .append(sources != null ? "sources=" + this.sources + "&" : "")
                .append(!keywords.isEmpty() ? "q=" +
                        keywords.stream().collect(Collectors.joining("+")) + "&" : "")
                .append(pageSize != null ? "pageSize=" + this.pageSize.intValue() + "&" : "")
                .append(page != null ? "page=" + this.page.intValue() + "&" : "")
                .append("apiKey=" + API_KEY);
        return builder.toString();
    }
}
