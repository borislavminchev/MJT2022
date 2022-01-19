package bg.sofia.uni.fmi.mjt.news.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse implements Response {
    private Status status;
    private String code;
    private String message;

    public ErrorResponse(String code, String message) {
        this.status = Status.ERROR;
        this.code = code;
        this.message = message;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

//    public void setCode(String code) {
//        this.code = code;
//    }

    public String getMessage() {
        return message;
    }

//    public void setMessage(String message) {
//        this.message = message;
//    }

    @Override
    public boolean isCorrect() {
        return this.status.getValue().equals("error") && this.code != null && this.status != null;
    }

    @Override
    public String toString() {
        return "{" +
                "status='" + this.status.getValue() + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
