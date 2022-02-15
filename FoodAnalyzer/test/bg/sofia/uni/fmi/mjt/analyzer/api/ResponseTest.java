package bg.sofia.uni.fmi.mjt.analyzer.api;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ResponseTest {

    @Test
    void testConstructing() {
        Response response = new Response(List.of(
                new Food(1, "Default description", "123456", null)
        ));
        assertNotNull(response, "Object was not constructed correctly");
    }

    @Test
    void testGetFoods() {
        final long id1 = 12345;
        final long id2 = 54321;
        Food food1 = new Food(id1, "Default description", "123456", null);
        Food food2 = new Food(id2, "Default description", "654321", null);
        Response response = new Response(List.of(food1, food2));
        assertIterableEquals(List.of(food1, food2), response.getFoods(), "Getter method is not working correctly");
    }
}