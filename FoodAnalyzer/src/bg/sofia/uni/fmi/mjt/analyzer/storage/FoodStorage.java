package bg.sofia.uni.fmi.mjt.analyzer.storage;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;

import java.util.Map;

public interface FoodStorage {

    Map<String, Food> getGtinUpcFoods();

    Map<Long, Food> getFoods();

    void addFood(Food food);

    Food getFoodById(long id);

    Food getFoodByGtinUpc(String gtinUpc);
}
