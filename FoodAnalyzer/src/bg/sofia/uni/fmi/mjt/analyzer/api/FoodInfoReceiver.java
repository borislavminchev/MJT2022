package bg.sofia.uni.fmi.mjt.analyzer.api;

import bg.sofia.uni.fmi.mjt.analyzer.storage.FoodStorage;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FoodInfoReceiver {
    private static final String API_KEY = "AHaQ3LpwZIf4RWzqSYZ7AWuD726Ad69et0TPWHsJ";
    private final FoodStorage storage;

    public FoodInfoReceiver() {
        this.storage = new FoodStorage();
    }

    public synchronized Response getFood(String query) {
        Response r = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            URI uri = new URI("https", "api.nal.usda.gov", "/fdc/v1/foods/search",
                    "api_key=" + API_KEY + "&query=" + query, null);

            HttpResponse<String> response = client.send(HttpRequest.newBuilder(uri).build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return null;
            }

            Gson gson = new Gson();
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

    public synchronized Response getFoodReport(long fdcId) {
        return null;
    }

    public synchronized Response getFoodByBarcode(String gtinUpc) {
        return null;
    }
}
