package bg.sofia.uni.fmi.mjt.cocktail.server;

import bg.sofia.uni.fmi.mjt.cocktail.server.storage.CocktailStorage;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestExecutor {
    private CocktailStorage storage;

    public RequestExecutor(CocktailStorage storage) {
        this.storage = storage;
    }

//        create <cocktail_name> [<ingredient_name>=<ingredient_amount> ...]
//        get all
//        get by-name <cocktail_name>
//        get by-ingredient <ingredient_name>
//        disconnect
    public ResponseObject execute(String command) {
        if (command == null || command.isEmpty()) {
            return new ResponseObject("Error", "Command was null or empty");
        }
        String[] arguments = command.split("\\s+");
        if (arguments[0].equals("create")) {
            List<String> args = new ArrayList<>(List.of(arguments));
            args.remove(0);
            return createCocktail(args);
        } else if (arguments[0].equals("get")) {
            if (arguments[1].equals("all")) {
                List<String> args = new ArrayList<>(List.of(arguments));
                args.remove(0);
                args.remove(0);
                return executeGetAll(args);
            } else if (arguments[1].equals("by-name")) {
                List<String> args = new ArrayList<>(List.of(arguments));
                args.remove(0);
                args.remove(0);
                return executeGetByName(args);
            }
            else if (arguments[1].equals("by-ingredient")) {
                List<String> args = new ArrayList<>(List.of(arguments));
                args.remove(0);
                args.remove(0);
                return executeGetByIngredient(args);
            }
        }

        return new ResponseObject("Error", "Invalid command");
    }

    private ResponseObject executeGetByIngredient(List<String> args) {
        if (args.size() != 1) {
            return new ResponseObject("Error", "Too many arguments were given");
        }
        return new ResponseObject("OK", List.copyOf(storage.getCocktailsWithIngredient(args.get(0))));
    }

    private ResponseObject executeGetByName(List<String> args) {
        if (args.size() != 1) {
            return new ResponseObject("Error", "Too many arguments were given");
        }
        try {
            return new ResponseObject("OK", List.of(storage.getCocktail(args.get(0))));
        } catch (CocktailNotFoundException e) {
            return new ResponseObject("Error", e.getMessage());
        }
    }

    private ResponseObject executeGetAll(List<String> args) {
        if (args.size() > 0) {
            return new ResponseObject("Error", "Too many arguments were given");
        }
        return new ResponseObject("OK", List.copyOf(storage.getCocktails()));
    }

    private ResponseObject createCocktail(List<String> args) {
        if (args.size() < 2) {
            return new ResponseObject("Error", "Few arguments were given");
        }
        String name = args.get(0);
        args.remove(0);
        Set<Ingredient> ingredients = args.stream()
                .map(i -> getIngredient(i))
                .collect(Collectors.toSet());
        try {
            storage.createCocktail(new Cocktail(name, ingredients));
        } catch (CocktailAlreadyExistsException e) {
            return new ResponseObject("Error", e.getMessage());
        }

        return new ResponseObject("Created");
    }

    private Ingredient getIngredient(String str) {
        String[] args = str.split("=");
        return new Ingredient(args[0], args[1]);
    }

}
