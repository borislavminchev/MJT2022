package bg.sofia.uni.fmi.mjt.analyzer.api;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestSender {
    private static final String API_KEY = "AHaQ3LpwZIf4RWzqSYZ7AWuD726Ad69et0TPWHsJ";
    private RequestSender() {
    }
    public static Response getFoodsByQuery(String query) {
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }
        Response r = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            URI uri = new URI("https", "api.nal.usda.gov", "/fdc/v1/foods/search",
                    "api_key=" + API_KEY + "&query=" + query, null);

            HttpResponse<String> response = client.send(HttpRequest.newBuilder(uri).build(),
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();

            final int okCode = 200;
            if (response.statusCode() != okCode) {
                ErrorResponse error = gson.fromJson(response.body(), ErrorResponse.class);
                throw new RuntimeException(error.getError());
            }

            r = gson.fromJson(response.body(), Response.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return r;
    }

    public static Food getFoodInfo(long fdcId) {
        Food res = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            URI uri = new URI("https", "api.nal.usda.gov", "/fdc/v1/food/" + fdcId,
                    "api_key=" + API_KEY + "&format=abridged&nutrients=203&nutrients=204" +
                            "&nutrients=205&nutrients=208&nutrients=291", null);
            HttpResponse<String> response = client.send(HttpRequest.newBuilder(uri).build(),
                    HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();

            final int okCode = 200;
            if (response.statusCode() != okCode) {
                throw new RuntimeException("Food with id: " + fdcId + " was not found or other problem occurred");
            }

            res = gson.fromJson(response.body(), Food.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return res;
    }
}
