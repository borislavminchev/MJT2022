package bg.sofia.uni.fmi.mjt.analyzer.storage;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FoodStorage {

    Map<String, Food> getGtinUpcFoods();

    Map<Long, Food> getFoods();

    void addFood(Food food);

    Food getFoodById(long id);

    Food getFoodByGtinUpc(String gtinUpc);
}
