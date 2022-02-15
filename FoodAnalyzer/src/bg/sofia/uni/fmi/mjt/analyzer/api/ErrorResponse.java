package bg.sofia.uni.fmi.mjt.analyzer.api;


public class ErrorResponse {
    private String message;

    public ErrorResponse(String error) {
        this.message = error;
    }

    public String getError() {
        return message;
    }
}
