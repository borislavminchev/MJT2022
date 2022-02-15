package bg.sofia.uni.fmi.mjt.analyzer.api;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import bg.sofia.uni.fmi.mjt.analyzer.entity.Nutrient;
import bg.sofia.uni.fmi.mjt.analyzer.storage.DefaultFoodStorage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultFoodInfoReceiverTest {

    private static MockedStatic<RequestSender> mockedStatic;
    private static Response response;
    private static Food food1;
    private static Food food2;
    private static List<Nutrient> nutrients = List.of(
            new Nutrient("Protein", 1, "G"),
            new Nutrient("Total lipid (fat)", 2, "G"),
            new Nutrient("Carbohydrate, by difference", 1, "G"),
            new Nutrient("Energy", 2, "KCAL"),
            new Nutrient("Fiber, total dietary", 0, "G")
    );
    private final DefaultFoodInfoReceiver receiver = new DefaultFoodInfoReceiver(new DefaultFoodStorage());

    @BeforeAll
    static void setUp() {
        mockedStatic = Mockito.mockStatic(RequestSender.class);
        food1 = new Food(1, "Default description", "123456", null);
        food2 = new Food(2, "Other default description", null, nutrients);
        response = new Response(List.of(food1, food2));
    }

    @Test
    void testGetFood() {
        mockedStatic.when(() -> RequestSender.getFoodsByQuery("default"))
                .thenReturn(response);

        Response resp = receiver.getFood("default");
        assertEquals(response, resp, "Expected returned response to contain different foods than actual");
        assertEquals(2, receiver.getStorage().getFoods().size(),
                "Expected foods in storage to contain 2 foods in their id part");
        assertEquals(1, receiver.getStorage().getGtinUpcFoods().size(),
                "Expected foods in storage to contain 2 foods in their GtinUpc part");
        assertEquals(food1, receiver.getStorage().getFoods().get(food1.getFdcId()),
                "Expected from storage to contain all foods after request");
        assertEquals(food2, receiver.getStorage().getFoods().get(food2.getFdcId()),
                "Expected from storage to contain all foods after request");
        assertEquals(food1, receiver.getStorage().getGtinUpcFoods().get(food1.getGtinUpc()),
                "Expected from storage to contain all foods after request");
    }

    @Test
    void testGetFoodError() {
        mockedStatic
                .when(() -> RequestSender.getFoodsByQuery(""))
                .thenThrow(new RuntimeException("It is not permitted to search by empty string"));

        assertThrows(IllegalArgumentException.class, () -> receiver.getFood(""),
                "Expected IllegalArgumentException to be thrown when method is called with invalid args");
    }

    @Test
    void testGetFoodReportFromApi() {
        mockedStatic
                .when(() -> RequestSender.getFoodInfo(2))
                .thenReturn(food2);

        Response actual = receiver.getFoodReport(2);
        assertEquals(food2, actual.getFoods().get(0),
                "Expected returned response to contain different foods than actual");
        assertEquals(1, receiver.getStorage().getFoods().size(),
                "Expected foods in storage to contain 1 food in their id part");
        assertTrue(receiver.getStorage().getGtinUpcFoods().isEmpty(),
                "Expected foods in storage to contain 0 foods in their GtinUpc part");
        assertEquals(food2, receiver.getStorage().getFoods().get(2L),
                "Food stored in storage is different than expected");
    }

    @Test
    void testGetFoodReportFromStorage() {
        mockedStatic
                .when(() -> RequestSender.getFoodInfo(2))
                .thenThrow(new RuntimeException("Expected to search in storage first"));

        receiver.getStorage().addFood(food2);
        Response actual = receiver.getFoodReport(2);

        assertEquals(food2, actual.getFoods().get(0),
                "Expected returned response to contain different foods than actual");
    }

    @Test
    void testGetFoodReportError() {
        mockedStatic
                .when(() -> RequestSender.getFoodInfo(-1))
                .thenThrow(new RuntimeException("Expected to throw error"));

        assertThrows(IllegalArgumentException.class, () -> receiver.getFoodReport(-1),
                "Expected IllegalArgumentException to be thrown when method is called with invalid args");
    }

    @Test
    void getFoodByBarcodeInStorage() {
        receiver.getStorage().addFood(food1);
        Response actual = receiver.getFoodByBarcode(food1.getGtinUpc());
        assertEquals(food1, actual.getFoods().get(0),
                "Expected returned response to contain different foods than actual");
    }

    @Test
    void getFoodByBarcodeNotInStorage() {
        assertThrows(RuntimeException.class, () -> receiver.getFoodByBarcode(food1.getGtinUpc()),
                "Expected RuntimeException to be thrown when food with given barcode is on inn storage");
    }

    @Test
    void getFoodByBarcodeError() {
        assertThrows(RuntimeException.class, () -> receiver.getFoodByBarcode(""),
                "Expected RuntimeException to be thrown when method is called with invalid args");
    }

    @AfterAll
    static void afterAll() {
        mockedStatic.close();
    }
}