package bg.sofia.uni.fmi.mjt.news;

import java.util.List;

class Response {
    private String status;
    private int totalResults;
    private List<Entity> articles;

    public Response(String status, int totalResults, List<Entity> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Entity> getArticles() {
        return articles;
    }

    public void setArticles(List<Entity> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }
}
