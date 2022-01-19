package bg.sofia.uni.fmi.mjt.news.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OKResponse implements Response {

//    @Expose(serialize = false)
    private Status status;
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

//    @Override
//    public void setStatus(Status status) {
//        if (status != Status.OK) {
//            throw new RuntimeException("Cannot set status: " + status + " to OkResponse");
//        }
//        this.status = status;
//    }

    public int getTotalResults() {
        return totalResults.intValue();
    }

    public List<Entity> getArticles() {
        return articles;
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
