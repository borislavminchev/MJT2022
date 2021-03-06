package bg.sofia.uni.fmi.mjt.news.response;

import java.util.List;

public class OKResponse implements Response {
    private final Status status;
    private final Integer totalResults;
    private final List<NewsEntity> articles;

    public OKResponse(int totalResults, List<NewsEntity> articles) {
        this.status = Status.OK;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults.intValue();
    }

    public List<NewsEntity> getArticles() {
        return List.copyOf(articles);
    }

    @Override
    public boolean isCorrect() {
        return this.status.getValue().equals("ok") && this.totalResults != null && this.articles != null;
    }
}
