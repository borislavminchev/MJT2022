package bg.sofia.uni.fmi.mjt.cocktail.server.storage;

import bg.sofia.uni.fmi.mjt.cocktail.server.Cocktail;
import bg.sofia.uni.fmi.mjt.cocktail.server.Ingredient;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultCocktailStorage implements CocktailStorage {
    private final List<Cocktail> cocktails;

    public DefaultCocktailStorage() {
        cocktails = new ArrayList<>();
    }

    @Override
    public void createCocktail(Cocktail cocktail) throws CocktailAlreadyExistsException {
        if (cocktails.contains(cocktail)) {
            throw new CocktailAlreadyExistsException("Cocktail " + cocktail.name() + " already exists");
        }

        cocktails.add(cocktail);
    }

    @Override
    public Collection<Cocktail> getCocktails() {
        return cocktails;
    }

    @Override
    public Collection<Cocktail> getCocktailsWithIngredient(String ingredientName) {
        List<Cocktail> res = new ArrayList<>();
        for (Cocktail cocktail : this.cocktails) {
            for (Ingredient ingredient : cocktail.ingredients()) {
                if (ingredient.name().equals(ingredientName)) {
                    res.add(cocktail);
                    break;
                }
            }
        }

        return res;
    }

    @Override
    public Cocktail getCocktail(String name) throws CocktailNotFoundException {
        for (Cocktail cocktail : this.cocktails) {
            if (cocktail.name().equals(name)) {
                return cocktail;
            }
        }

        throw new CocktailNotFoundException("Cocktail " + name + " was not found");
    }
}
