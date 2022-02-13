package bg.sofia.uni.fmi.mjt.analyzer.api;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import bg.sofia.uni.fmi.mjt.analyzer.storage.FoodStorage;

import java.util.List;

public class FoodInfoReceiver {
    private final FoodStorage storage;

    public FoodInfoReceiver() {
        this.storage = new FoodStorage();
    }

    public synchronized Response getFood(String query) {
        Response r = RequestSender.getFoodsByQuery(query);
        List<Food> foods = r.getFoods();
        if (!foods.isEmpty()) {
            foods.forEach(i -> storage.addFood(i));
        }
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
        if (gtinUpc == null || gtinUpc.isEmpty()) {
            throw new RuntimeException("GtinUpc code cannot be null or empty");
        }
        List<Food> fromStorage = this.storage.getFoodsByGtinUpc(gtinUpc);
        if (fromStorage.isEmpty()) {
            throw new RuntimeException("No foods found with gtinUpc code: " + gtinUpc);
        }
        return new Response(fromStorage);
    }
}
