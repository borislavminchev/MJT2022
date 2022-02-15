package bg.sofia.uni.fmi.mjt.analyzer.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Food {
    private long fdcId;
    private String gtinUpc;
    private String description;

    private List<Nutrient> foodNutrients;

    public Food(long fdcId, String description, String gtinUpc, List<Nutrient> foodNutrients) {
        this.fdcId = fdcId;
        this.description = description;
        this.gtinUpc = gtinUpc;
        this.foodNutrients = foodNutrients;
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
                && (this.foodNutrients.get(0).getName() != null && !this.foodNutrients.get(0).getName().isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;
        Food food = (Food) o;
        return fdcId == food.fdcId && gtinUpc.equals(food.gtinUpc) && description.equals(food.description)
                && Objects.equals(foodNutrients, food.foodNutrients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fdcId, gtinUpc, description);
    }

    @Override
    public String toString() {
        return "{" +
                "fdcId=" + fdcId +
                ", gtinUpc='" + gtinUpc + '\'' +
                ", description='" + description + '\'' +
                (hasReportInfo() ? ", foodNutrients=" + foodNutrients : "") +
                '}';
    }
}
