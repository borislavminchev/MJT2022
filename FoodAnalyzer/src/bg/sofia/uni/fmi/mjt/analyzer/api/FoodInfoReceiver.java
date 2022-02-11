package bg.sofia.uni.fmi.mjt.analyzer.api;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import bg.sofia.uni.fmi.mjt.analyzer.storage.FoodStorage;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class FoodInfoReceiver {
    private static final String API_KEY = "AHaQ3LpwZIf4RWzqSYZ7AWuD726Ad69et0TPWHsJ";
    private final FoodStorage storage;

    public FoodInfoReceiver() {
        this.storage = new FoodStorage();
    }

    public synchronized Response getFood(String query) {
        Response r = RequestSender.getFoodsByQuery(query);
        List<Food> foods = r.getFoods();
        foods.forEach(i -> storage.addFood(i));
        return r;
    }

    public synchronized Response getFoodReport(long fdcId) {
        Food fromStorage = this.storage.getFoodById(fdcId);

        if (fromStorage != null && fromStorage.hasReportInfo()) {
            return new Response(List.of(fromStorage));
        }

        Food res = RequestSender.getFoodInfo(fdcId);
        this.storage.addFood(res);

        return new Response(List.of(res));
    }

    public synchronized Response getFoodByBarcode(String gtinUpc) {
        List<Food> fromStorage = this.storage.getFoodsByGtinUpc(gtinUpc);
        if (fromStorage.isEmpty()) {
            throw new RuntimeException("No foods found with gtinUpc code: " + gtinUpc);
        }
        return new Response(fromStorage);
    }
}
