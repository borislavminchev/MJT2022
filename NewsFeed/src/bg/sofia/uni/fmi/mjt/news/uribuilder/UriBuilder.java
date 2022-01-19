package bg.sofia.uni.fmi.mjt.news.uribuilder;

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

    public UriBuilder setCountry(CountryCode country) {
        if (country == null) {
            throw new RuntimeException("Country to set cannot be null");
        }
        this.country = country;
        return this;
    }

    public UriBuilder setCategory(NewsCategory category) {
        if (category == null) {
            throw new RuntimeException("Category to set cannot be null");
        }
        this.category = category;
        return this;
    }

    public UriBuilder setSources(String sources) {
        if (sources == null || sources.isEmpty()) {
            throw new RuntimeException("Sources to set cannot be null or empty");
        }
        this.sources = sources;
        return this;
    }

    public UriBuilder setKeywords(List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            throw new RuntimeException("Trying to set wrong keywords list(empty or null)");
        }
        this.keywords = keywords;
        return this;
    }

    public UriBuilder setPageSize(Integer pageSize) {
        if (pageSize == null) {
            throw new RuntimeException("Page size to set cannot be null");
        } else if (pageSize.intValue() <= 0 || pageSize.intValue() > 100) {
            throw new RuntimeException("Page size out of bounds");
        }
        this.pageSize = pageSize;
        return this;
    }

    public UriBuilder setPage(Integer page) {
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

    private String getQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append(country != null ? "country=" + this.country.getValue() + "&" : "");
        builder.append(category != null ? "category=" + this.category.getValue() + "&" : "");
        builder.append(sources != null ? "sources=" + this.sources + "&" : "");

        builder.append(!keywords.isEmpty() ? "country=" +
                keywords.stream().collect(Collectors.joining("+")) + "&"
                : "");

        builder.append(pageSize != null ? "pageSize=" + this.pageSize.intValue() + "&" : "");
        builder.append(page != null ? "page=" + this.page.intValue() + "&" : "");
        builder.append("apiKey=" + API_KEY);
        return builder.toString();
    }
}
