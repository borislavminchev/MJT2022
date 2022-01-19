package bg.sofia.uni.fmi.mjt.news.response;

import java.util.List;

public class OKResponse implements Response {
    private final Status status;
    private Integer totalResults;
    private List<Entity> articles;

    public OKResponse(int totalResults, List<Entity> articles) {
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

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Entity> getArticles() {
        return articles;
    }

    public void setArticles(List<Entity> articles) {
        this.articles = articles;
    }


    @Override
    public boolean isCorrect() {
        return this.status.getValue().equals("ok") && this.totalResults != null && this.articles != null;
    }

    @Override
    public String toString() {
        return "{" +
                "status='" + this.status.getValue() + '\'' +
                ", totalResults=" + totalResults.intValue() +
                ", articles=" + articles +
                '}';
    }
}
