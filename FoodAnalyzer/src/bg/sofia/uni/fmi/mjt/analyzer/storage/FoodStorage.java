package bg.sofia.uni.fmi.mjt.analyzer.storage;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;

import java.util.HashMap;
import java.util.Map;

public class FoodStorage {

    Map<Long, Food> foods;
    Map<String, Food> gtinUpcFoods;

    public FoodStorage() {
        this.foods = new HashMap<>();
        this.gtinUpcFoods = new HashMap<>();
    }

    public void addFood(Food food) {
        if (food == null || !food.isValid()) {
            throw new RuntimeException("Food is null or has invalid info");
        }

        this.foods.put(food.getFdcId(), food);
        if (food.getGtinUpc() != null) {
            this.gtinUpcFoods.put(food.getGtinUpc(), food);
        }
    }

    public Food getFoodById(long id) {
        return this.foods.get(id);
    }

    public  Food getFoodByGtinUpc(String gtinUpc) {
        return this.gtinUpcFoods.get(gtinUpc);
    }
}
