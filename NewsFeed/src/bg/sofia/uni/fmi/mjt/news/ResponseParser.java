package bg.sofia.uni.fmi.mjt.news;

import bg.sofia.uni.fmi.mjt.news.response.OKResponse;
import bg.sofia.uni.fmi.mjt.news.response.Response;
import com.google.gson.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ResponseParser {
    private String response;

    public ResponseParser(String response) {
        if (response == null) {
            throw new RuntimeException("Response cannot be null");
        }
        this.response = response;
    }

    public void setResponse(String response) {
        if (response == null) {
            throw new RuntimeException("Response cannot be null");
        }
        this.response = response;
    }

    public <T extends Response> Optional<T> parseTo(Class<T> c) {
        Gson gson = new GsonBuilder().create();
//        if (map.get("status").toString().equals(""))
        T t = gson.fromJson(this.response, c);

        Optional<T> res = Optional.empty();

        if (t.isCorrect()) {
            res = Optional.of(t);
        }
        return res;
    }


}
