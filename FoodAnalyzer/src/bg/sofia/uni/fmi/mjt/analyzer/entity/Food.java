package bg.sofia.uni.fmi.mjt.analyzer.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class Food {
    private long fdcId;
    private String gtinUpc;
    private String description;

    private List<Nutrient> foodNutrients;

    public Food(long fdcId, String description, String gtinUpc) {
        this.fdcId = fdcId;
        this.description = description;
        this.gtinUpc = gtinUpc;
    }

    public long getFdcId() {
        return fdcId;
    }

    public String getDescription() {
        return description;
    }

    public String getGtinUpc() {
        return gtinUpc;
    }

    public List<Nutrient> getFoodNutrients() {
        return List.copyOf(foodNutrients);
    }

    public boolean isValid() {
        return this.fdcId != 0 && this.description != null && !this.description.isEmpty();
    }

    public boolean hasReportInfo() {
        return this.foodNutrients != null
                && !this.foodNutrients.isEmpty()
                && this.foodNutrients.get(0).getName() != null;
    }

    @Override
    public String toString() {
        return "{" +
                "fdcId=" + fdcId +
                ", gtinUpc='" + gtinUpc + '\'' +
                ", description='" + description + '\'' +
                ", foodNutrients=" + foodNutrients +
                '}';
    }
}
