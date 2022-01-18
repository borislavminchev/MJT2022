package bg.sofia.uni.fmi.mjt.news;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String API_KEY = "8d1153d6a324436e8690e4ed521d3716";

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder().build();
        URI uri = new URI("https", "newsapi.org", "/v2/top-headlines",
                "country=us&apiKey=" + API_KEY, null);
        HttpResponse<String> str = client.send(HttpRequest.newBuilder(uri).build(), HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Response map = gson.fromJson(str.body(), Response.class);
        System.out.println(map.getArticles().get(1));
    }
}

