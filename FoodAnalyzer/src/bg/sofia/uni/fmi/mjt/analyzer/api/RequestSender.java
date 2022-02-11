package bg.sofia.uni.fmi.mjt.analyzer.api;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestSender {
    private static final String API_KEY = "AHaQ3LpwZIf4RWzqSYZ7AWuD726Ad69et0TPWHsJ";

    public static Response getFoodsByQuery(String query) {
        Response r = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            URI uri = new URI("https", "api.nal.usda.gov", "/fdc/v1/foods/search",
                    "api_key=" + API_KEY + "&query=" + query, null);

            HttpResponse<String> response = client.send(HttpRequest.newBuilder(uri).build(), HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();

            if (response.statusCode() != 200) {
                ErrorResponse error = gson.fromJson(response.body(), ErrorResponse.class);
                throw new RuntimeException(error.getMessage());
            }

            r = gson.fromJson(response.body(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
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
            HttpResponse<String> response = client.send(HttpRequest.newBuilder(uri).build(), HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();

            if (response.statusCode() != 200) {
                ErrorResponse error = gson.fromJson(response.body(), ErrorResponse.class);
                throw new RuntimeException(error.getMessage());
            }

            res = gson.fromJson(response.body(), Food.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }
}
