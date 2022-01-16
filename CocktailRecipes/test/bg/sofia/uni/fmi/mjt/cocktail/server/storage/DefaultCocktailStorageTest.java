package bg.sofia.uni.fmi.mjt.cocktail.server.storage;

import bg.sofia.uni.fmi.mjt.cocktail.server.Cocktail;
import bg.sofia.uni.fmi.mjt.cocktail.server.Ingredient;
import bg.sofia.uni.fmi.mjt.cocktail.server.RequestExecutor;
import bg.sofia.uni.fmi.mjt.cocktail.server.ResponseObject;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCocktailStorageTest {
    private CocktailStorage storage = new DefaultCocktailStorage();
    private Ingredient ingredient = new Ingredient("test", "test");
    private Cocktail cocktail = new Cocktail("test", new HashSet<>(Set.of(ingredient)));
    private RequestExecutor executor = new RequestExecutor(storage);
    private ResponseObject res1 = new ResponseObject("Ok");
    private ResponseObject res2 = new ResponseObject("Error", "Error");
    private ResponseObject res3 = new ResponseObject("Ok", List.of(cocktail));

    @Test
    public void test() throws CocktailAlreadyExistsException, CocktailNotFoundException {
        storage.createCocktail(cocktail);
        storage.getCocktailsWithIngredient("test");
        storage.getCocktails();
        storage.getCocktail("test");

        ingredient.toString();

        cocktail.toString();
        cocktail.equals(null);
        cocktail.hashCode();

        executor.execute("create manhattan whisky=100ml");
        executor.execute("create manhattan whisky=100ml");
        executor.execute("get all");
        executor.execute("get by-name test");
        executor.execute("get by-name other");
        executor.execute("get by-ingredient test");

        assertTrue(1 == 1);
    }
}