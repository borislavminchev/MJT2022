package bg.sofia.uni.fmi.mjt.cocktail.server.storage;

import bg.sofia.uni.fmi.mjt.cocktail.server.Cocktail;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailNotFoundException;

import java.util.Collection;

public class DefaultCocktailStorage implements CocktailStorage {
    @Override
    public void createCocktail(Cocktail cocktail) throws CocktailAlreadyExistsException {

    }

    @Override
    public Collection<Cocktail> getCocktails() {
        return null;
    }

    @Override
    public Collection<Cocktail> getCocktailsWithIngredient(String ingredientName) {
        return null;
    }

    @Override
    public Cocktail getCocktail(String name) throws CocktailNotFoundException {
        return null;
    }
}
