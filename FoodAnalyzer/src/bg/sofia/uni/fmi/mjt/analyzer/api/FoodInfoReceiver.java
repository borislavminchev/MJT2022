package bg.sofia.uni.fmi.mjt.analyzer.api;

public interface FoodInfoReceiver {
    Response getFood(String query);

    Response getFoodReport(long fdcId);

    Response getFoodByBarcode(String gtinUpc);
}
