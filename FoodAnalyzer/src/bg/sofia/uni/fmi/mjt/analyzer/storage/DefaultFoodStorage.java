package bg.sofia.uni.fmi.mjt.analyzer.storage;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;

import java.util.Map;
import java.util.HashMap;

public class DefaultFoodStorage implements FoodStorage {

    Map<Long, Food> foods;
    Map<String, Food> gtinUpcFoods;

    public DefaultFoodStorage() {
        this.foods = new HashMap<>();
        this.gtinUpcFoods = new HashMap<>();
    }

    @Override
    public Map<Long, Food> getFoods() {
        return Map.copyOf(this.foods);
    }

    @Override
    public Map<String, Food> getGtinUpcFoods() {
        return Map.copyOf(this.gtinUpcFoods);
    }

    @Override
    public void addFood(Food food) {
        if (food == null || !food.isValid()) {
            throw new RuntimeException("Food is null or has invalid info");
        }

        this.foods.put(food.getFdcId(), food);
        if (food.getGtinUpc() != null && !food.getGtinUpc().isEmpty()) {
            this.gtinUpcFoods.put(food.getGtinUpc(), food);
        }
    }

    @Override
    public Food getFoodById(long id) {
        return this.foods.get(id);
    }

    @Override
    public Food getFoodByGtinUpc(String gtinUpc) {
        if (gtinUpc == null || gtinUpc.isEmpty()) {
            throw new RuntimeException("GtinUpc code cannot be null or empty");
        }
        return this.gtinUpcFoods.get(gtinUpc);
    }
}
