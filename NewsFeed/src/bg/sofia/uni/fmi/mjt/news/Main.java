package bg.sofia.uni.fmi.mjt.news;

import bg.sofia.uni.fmi.mjt.news.response.ErrorResponse;
import bg.sofia.uni.fmi.mjt.news.response.OKResponse;
import bg.sofia.uni.fmi.mjt.news.response.Response;
import bg.sofia.uni.fmi.mjt.news.uribuilder.CountryCode;
import bg.sofia.uni.fmi.mjt.news.uribuilder.UriBuilder;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class Main {
    private static final String API_KEY = "8d1153d6a324436e8690e4ed521d3716";

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder().build();
        URI uri = new URI("https", "newsapi.org", "/v2/top-headlines",
                "q=japan+coronavirus", null);
//        URI uri = new UriBuilder().setCountry(CountryCode.US).build();
        HttpResponse<String> str = client.send(HttpRequest.newBuilder(uri).build(), HttpResponse.BodyHandlers.ofString());

        Optional<ErrorResponse> r = new ResponseParser(str.body()).parseTo(ErrorResponse.class);
        System.out.println(r.orElse(null));


    }
}

