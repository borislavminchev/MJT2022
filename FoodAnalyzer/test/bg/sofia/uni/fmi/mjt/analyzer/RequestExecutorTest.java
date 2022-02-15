package bg.sofia.uni.fmi.mjt.analyzer;

import bg.sofia.uni.fmi.mjt.analyzer.api.FoodInfoReceiver;
import bg.sofia.uni.fmi.mjt.analyzer.api.Response;
import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import bg.sofia.uni.fmi.mjt.analyzer.entity.Nutrient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestExecutorTest {

    @Mock
    private FoodInfoReceiver infoReceiver = Mockito.mock(FoodInfoReceiver.class);
    private RequestExecutor executor = new RequestExecutor(infoReceiver);

    private static List<Nutrient> nutrients = List.of(
            new Nutrient("Protein", 1, "G"),
            new Nutrient("Total lipid (fat)", 2, "G"),
            new Nutrient("Carbohydrate, by difference", 1, "G"),
            new Nutrient("Energy", 2, "KCAL"),
            new Nutrient("Fiber, total dietary", 0, "G")
    );
    private static Food food = new Food(1, "Other default description", "725272730706", nutrients);
    private static Response response = new Response(List.of(food));

    @Test
    void testExecuteError() {
        assertThrows(RuntimeException.class, () -> executor.execute(""),
                "Expected to throw RuntimeException whe method is called with invalid arg");
        assertThrows(RuntimeException.class, () -> executor.execute("some-command"),
                "Expected to throw RuntimeException whe method is called with invalid arg");
    }

    @Test
    void testGetFood() {
        Mockito.when(infoReceiver.getFood("default")).thenReturn(response);
        Response actual = executor.execute("get-food default");

        assertEquals(food, actual.getFoods().get(0),
                "Command get-food was not executed correctly");
    }

    @Test
    void testGetFoodError() {
        assertThrows(RuntimeException.class, () -> executor.execute("get-food"),
                "Expected to throw RuntimeException when no args");
    }


    @Test
    void testGetFoodReport() {
        Mockito.when(infoReceiver.getFoodReport(1)).thenReturn(response);
        Response actual = executor.execute("get-food-report 1");

        assertEquals(food, actual.getFoods().get(0),
                "Command get-food-report was not executed correctly");
    }

    @Test
    void testGetFoodReportError() {
        Mockito.when(infoReceiver.getFoodReport(1)).thenReturn(response);
        assertThrows(RuntimeException.class, () -> executor.execute("get-food-report"),
                "Expected to throw RuntimeException when no args");
        assertThrows(RuntimeException.class, () -> executor.execute("get-food-report 1 123"),
                "Expected to throw RuntimeException when are used too much args");
    }

    @Test
    void testGetFoodByBarcode() {
        Mockito.when(infoReceiver.getFoodByBarcode("725272730706")).thenReturn(response);

        assertEquals(response, executor.execute("get-food-by-barcode --code=725272730706"),
                "Command get-food-by-barcode was not executed correctly when using only --code");
        assertEquals(response, executor.execute("get-food-by-barcode --img=barcode.gif"),
                "Command get-food-by-barcode was not executed correctly when using only --img");
        assertEquals(response, executor.execute("get-food-by-barcode --code=725272730706 --img=barcode.gif"),
                "Command get-food-by-barcode was not executed correctly when using first --code then --img");
        assertEquals(response, executor.execute("get-food-by-barcode --img=barcode.gif --code=725272730706"),
                "Command get-food-by-barcode was not executed correctly when using first --img then --code");
    }

    @Test
    void testGetFoodByBarcodeError() {
        assertThrows(RuntimeException.class, () -> executor.execute("get-food-by-barcode"),
                "Expected to throw RuntimeException when no args");
        assertThrows(RuntimeException.class, () -> executor.execute("get-food-by-barcode too much args"),
                "Expected to throw RuntimeException when are used too much args");
    }
}