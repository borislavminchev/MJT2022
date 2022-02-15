package bg.sofia.uni.fmi.mjt.analyzer.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ErrorResponseTest {

    @Test
    void testConstructing() {
        ErrorResponse response = new ErrorResponse("error");
        assertNotNull(response, "Object was not constructed correctly");
    }

    @Test
    void testGetError() {
        ErrorResponse errorResponse = new ErrorResponse("error");
        assertEquals("error", errorResponse.getError(), "Getter method is not working correctly");
    }
}