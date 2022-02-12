package bg.sofia.uni.fmi.mjt.analyzer;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import bg.sofia.uni.fmi.mjt.analyzer.api.Response;
import com.google.gson.*;
import com.google.zxing.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.logging.*;

public class Main {

    private static final String API_KEY = "AHaQ3LpwZIf4RWzqSYZ7AWuD726Ad69et0TPWHsJ";

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        //https://api.nal.usda.gov/fdc/v1/food/2022017?
        // format=abridged
        // nutrients=203&nutrients=204&nutrients=205&nutrients=208&nutrients=291
        // &api_key=AHaQ3LpwZIf4RWzqSYZ7AWuD726Ad69et0TPWHsJ

        //2022017

        //HttpClient client = HttpClient.newBuilder().build();
        //URI uri = new URI("https", "api.nal.usda.gov", "/fdc/v1/foods/search",
               //"api_key=" + API_KEY + "&query=Cheddar", null);
//        URI uri = new URI("https", "api.nal.usda.gov", "/fdc/v1/food/2022017",
//                "api_key=" + API_KEY + "&format=abridged&nutrients=203&nutrients=204" +
//                        "&nutrients=205&nutrients=208&nutrients=291", null);

//        HttpResponse<String> response = client.send(HttpRequest.newBuilder(uri).build(), HttpResponse.BodyHandlers.ofString());
//
//        Gson gson = new Gson();
//        Response r = gson.fromJson(response.body(), Response.class);
//        List<Food> foods = r.getFoods();
//        foods.stream().map(i -> i.toString()).forEach(System.out::println);

        //System.out.println(BarcodeReader.read("barcode.gif"));
        Logger logger = Logger.getLogger("Log");
        FileHandler handler = new FileHandler("file.log");
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler );
        logger.info("Test");
        logger.log(Level.SEVERE, "Exception was thrown", new RuntimeException("Some error occurred"));

    }
}
