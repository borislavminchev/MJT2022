package bg.sofia.uni.fmi.mjt.analyzer.api;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestSenderTest {

    @Test
    void testGetFoodsByQueryOK() {
        Response response = RequestSender.getFoodsByQuery("Cheddar");
        assertFalse(response.getFoods().isEmpty());

        final long id = 2022017;
        final String description = "CHEDDAR";
        final String gtinUpc = "048707820026";

        Food f = response.getFoods().get(0);
        assertEquals(id, f.getFdcId(), "Returned food id is not correct");
        assertEquals(description, f.getDescription(), "Returned food description is not correct");
        assertEquals(gtinUpc, f.getGtinUpc(), "Returned food gtinUpc is not correct");
    }

    @Test
    void testGetFoodsByQueryError() {
        assertThrows(RuntimeException.class, () -> RequestSender.getFoodsByQuery(""),
                "Expected to throw RuntimeException when method is called with invalid args");
        assertThrows(RuntimeException.class, () -> RequestSender.getFoodsByQuery("Cheddar&pageNumber=-1"),
                "Expected to throw RuntimeException when method is called with strange args");
    }

    @Test
    void testGetFoodInfoOK() {
        final long id = 2022017;
        Food response = RequestSender.getFoodInfo(id);
        assertNotNull(response);

        final String description = "CHEDDAR";
        final String gtinUpc = "048707820026";

        assertEquals(id, response.getFdcId(), "Returned food id is not correct");
        assertEquals(description, response.getDescription(), "Returned food description is not correct");
        assertEquals(gtinUpc, response.getGtinUpc(), "Returned food gtinUpc is not correct");
    }

    @Test
    void testGetFoodInfoError() {
        assertThrows(RuntimeException.class, () -> RequestSender.getFoodInfo(0),
                "Expected to throw RuntimeException when method is called with invalid args");
        assertThrows(RuntimeException.class, () -> RequestSender.getFoodInfo(-1),
                "Expected to throw RuntimeException when method is called with strange args");
    }
}