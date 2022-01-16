package bg.sofia.uni.fmi.mjt.cocktail.server;

import java.util.List;

public class ResponseObject {
    private final String status;
    private String errorMessage;
    private List<Cocktail> cocktails;

    public ResponseObject(String status) {
        this.status = status;
    }

    public ResponseObject(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public ResponseObject(String status, List<Cocktail> cocktails) {
        this.status = status;
        this.cocktails = cocktails;
    }

    @Override
    public String toString() {
        return "{" +
                "\"status\":\"" + status + '\"' +
                (errorMessage == null ? "" : ", \"errorMessage\":" + '\"' + errorMessage + '\"') +
                (cocktails == null ? "" : ", \"cocktails\":" + cocktails) +
                '}';
    }
}
