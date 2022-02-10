package bg.sofia.uni.fmi.mjt.analyzer;

import bg.sofia.uni.fmi.mjt.analyzer.api.FoodInfoReceiver;
import bg.sofia.uni.fmi.mjt.analyzer.api.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestExecutor {
    private FoodInfoReceiver infoReceiver;

//    get-food <food_name> (fdcId, description, gtinUpc)
//    get-food-report <food_fdcId> (fdcId, description, gtinUpc, carbs, fats, protein, fiber, kcal)
//    get-food-by-barcode --code=<gtinUpc_code>|--img=<barcode_image_file>


    public RequestExecutor(FoodInfoReceiver infoReceiver) {
        this.infoReceiver = infoReceiver;
    }

    public Response execute(String command) {
        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("Command was null or empty");
        }
        String[] arguments = command.split("\\s+");
        if (arguments[0].equals("get-food")) {
            List<String> args = new ArrayList<>(List.of(arguments));
            args.remove(0);
            return getFood(args);
        } else if (arguments[0].equals("get-food-report")) {
            List<String> args = new ArrayList<>(List.of(arguments));
            args.remove(0);
            return getFoodReport(args);
        } else if (arguments[0].equals("get-food-by-barcode")) {
            List<String> args = new ArrayList<>(List.of(arguments));
            args.remove(0);
            return getFoodByBarcode(args);
        }

        throw new RuntimeException("Command was not recognized:" + command);
    }

    private Response getFood(List<String> args) {
        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException("Args cannot be null or empty");
        }
        String q = args.stream().collect(Collectors.joining(" "));
        return infoReceiver.getFood(q);
    }

    private Response getFoodReport(List<String> args) {
        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException("Args cannot be null or empty");
        } else if (args.size() > 1) {
            throw new IllegalArgumentException("Expected only one argument");
        }

        long id = Long.parseLong(args.get(0));

        return this.infoReceiver.getFoodReport(id);
    }

    private Response getFoodByBarcode(List<String> args) {
        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException("Args cannot be null or empty");
        } else if (args.size() > 2) {
            throw new IllegalArgumentException("Expected max 2 arguments");
        }
        return null;
    }
}
