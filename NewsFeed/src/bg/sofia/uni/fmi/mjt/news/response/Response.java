package bg.sofia.uni.fmi.mjt.news.response;

public interface Response {
    Status getStatus();

    boolean isCorrect();
}
