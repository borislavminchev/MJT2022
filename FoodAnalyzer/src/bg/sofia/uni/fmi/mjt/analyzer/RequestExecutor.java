package bg.sofia.uni.fmi.mjt.analyzer;

import bg.sofia.uni.fmi.mjt.analyzer.api.FoodInfoReceiver;
import bg.sofia.uni.fmi.mjt.analyzer.api.Response;
import bg.sofia.uni.fmi.mjt.analyzer.barcode.BarcodeReader;

import java.util.ArrayList;
import java.util.List;

public class RequestExecutor {
    private FoodInfoReceiver infoReceiver;

    public RequestExecutor(FoodInfoReceiver infoReceiver) {
        this.infoReceiver = infoReceiver;
    }

    public Response execute(String command) {
        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("Command was null or empty");
        }
        String[] arguments = command.split("\\s+");
        switch (arguments[0]) {
            case "get-food" -> {
                List<String> args = new ArrayList<>(List.of(arguments));
                args.remove(0);
                return getFood(args);
            }
            case "get-food-report" -> {
                List<String> args = new ArrayList<>(List.of(arguments));
                args.remove(0);
                return getFoodReport(args);
            }
            case "get-food-by-barcode" -> {
                List<String> args = new ArrayList<>(List.of(arguments));
                args.remove(0);
                return getFoodByBarcode(args);
            }
        }

        throw new RuntimeException("Command was not recognized:" + command);
    }

    private Response getFood(List<String> args) {
        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException("Args cannot be null or empty");
        }
        String q = String.join("%20", args);
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

        String arg1 = args.get(0);
        if (args.size() == 1) {
            if (arg1.startsWith("--img=")) {
                return getGtinUpcByImg(arg1);
            } else if (arg1.startsWith("--code=")) {
                return getGtinUpcByCode(arg1);
            }
        } else {
            String arg2 = args.get(1);
            if (arg1.startsWith("--img=") && arg2.startsWith("--code=")) {
                return getGtinUpcByImg(arg1);
            } else if (arg2.startsWith("--img=") && arg1.startsWith("--code=")) {
                return getGtinUpcByCode(arg1);
            }
        }
        throw new RuntimeException("Some of get-food-by-barcode arguments were invalid: " + args);
    }

    private Response getGtinUpcByCode(String code) {
        String rest = code.substring(code.indexOf("--code=") + "--code=".length());
        return this.infoReceiver.getFoodByBarcode(rest);
    }

    private Response getGtinUpcByImg(String img) {
        String rest = img.substring(img.indexOf("--img=") + "--img=".length());
        String gtinUpc = BarcodeReader.read(rest);
        return this.infoReceiver.getFoodByBarcode(gtinUpc);
    }
}
