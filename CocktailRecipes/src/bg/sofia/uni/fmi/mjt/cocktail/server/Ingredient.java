package bg.sofia.uni.fmi.mjt.cocktail.server;

public record Ingredient(String name, String amount) {
    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"amount\":\"" + amount + '\"' +
                '}';
    }
}
