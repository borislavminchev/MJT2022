package bg.sofia.uni.fmi.mjt.news.parser;

import bg.sofia.uni.fmi.mjt.news.response.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpResponse;
import java.util.Optional;

public class ResponseParser {
    private HttpResponse<String> response;

    private ResponseParser(HttpResponse<String> response) {
        if (response == null) {
            throw new RuntimeException("Response cannot be null");
        }
        this.response = response;
    }

    private ResponseParser() { }

    public static ResponseParser createNew(HttpResponse<String> response) {
        return new ResponseParser(response);
    }

    public <T extends Response> Optional<T> parseTo(Class<T> c) {
        Gson gson = new GsonBuilder().create();
        T t = gson.fromJson(this.response.body(), c);

        Optional<T> res = Optional.empty();

        if (t.isCorrect()) {
            res = Optional.of(t);
        }
        return res;
    }


}
