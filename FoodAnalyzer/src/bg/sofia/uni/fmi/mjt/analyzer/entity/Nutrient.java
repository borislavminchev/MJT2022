package bg.sofia.uni.fmi.mjt.analyzer.entity;

public class Nutrient {
    private String name;
    private double amount;
    private String unitName;

    public Nutrient(String name, double amount, String unitName) {
        this.name = name;
        this.amount = amount;
        this.unitName = unitName;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnitName() {
        return unitName;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", unitName='" + unitName + '\'' +
                '}';
    }
}
