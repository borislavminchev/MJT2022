package bg.sofia.uni.fmi.mjt.news;

public enum NewsCategory {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology");

    private final String value;

    NewsCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
