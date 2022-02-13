package bg.sofia.uni.fmi.mjt.analyzer.api;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Error;

public class ErrorResponse {
    private Error error;

    public ErrorResponse(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
