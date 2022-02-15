package bg.sofia.uni.fmi.mjt.analyzer.api;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import bg.sofia.uni.fmi.mjt.analyzer.storage.FoodStorage;

import java.util.List;

public class DefaultFoodInfoReceiver implements FoodInfoReceiver {
    private final FoodStorage storage;

    public DefaultFoodInfoReceiver(FoodStorage storage) {
        this.storage = storage;
    }

    public FoodStorage getStorage() {
        return storage;
    }

    @Override
    public synchronized Response getFood(String query) {
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }

        Response r = RequestSender.getFoodsByQuery(query);
        List<Food> foods = r.getFoods();
        if (!foods.isEmpty()) {
            foods.forEach(i -> storage.addFood(i));
        }
        return r;
    }

    @Override
    public synchronized Response getFoodReport(long fdcId) {
        if (fdcId < 0) {
            throw new IllegalArgumentException("fdcId cannot be negative");
        }
        Food fromStorage = this.storage.getFoodById(fdcId);

        if (fromStorage != null && fromStorage.hasReportInfo()) {
            return new Response(List.of(fromStorage));
        }

        Food res = RequestSender.getFoodInfo(fdcId);
        this.storage.addFood(res);

        return new Response(List.of(res));
    }

    @Override
    public synchronized Response getFoodByBarcode(String gtinUpc) {
        if (gtinUpc == null || gtinUpc.isEmpty()) {
            throw new RuntimeException("GtinUpc code cannot be null or empty");
        }
        Food fromStorage = this.storage.getFoodByGtinUpc(gtinUpc);
        if (fromStorage == null) {
            throw new RuntimeException("No foods found with gtinUpc code: " + gtinUpc);
        }
        return new Response(List.of(fromStorage));
    }
}
