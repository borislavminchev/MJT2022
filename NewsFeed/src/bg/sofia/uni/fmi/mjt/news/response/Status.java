package bg.sofia.uni.fmi.mjt.news.response;

import com.google.gson.annotations.SerializedName;

public enum Status {
    @SerializedName("ok")
    OK("ok"),
    @SerializedName("error")
    ERROR("error");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
