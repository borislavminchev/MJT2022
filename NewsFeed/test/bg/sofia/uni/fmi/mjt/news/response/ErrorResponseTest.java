package bg.sofia.uni.fmi.mjt.news.response;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseTest {
    private static ErrorResponse response;

    @BeforeAll
    static void setUp() throws IOException {
        response = new ErrorResponse("apiKeyMissing", "Your API key is missing. Append this to the" +
                " URL with the apiKey param, or use the x-api-key HTTP header.");
    }

    @Test
    void testCorrectSetResponseError() {
        final Status expectedStatus = Status.ERROR;
        final String expectedCode = "apiKeyMissing";
        final String expectedMessage = "Your API key is missing. Append this to the" +
                " URL with the apiKey param, or use the x-api-key HTTP header.";

        assertEquals(expectedStatus, response.getStatus(), "Status was not set correctly, expected status Error");
        assertEquals(expectedCode, response.getCode(), "Error code was not set correctly");
        assertEquals(expectedMessage, response.getMessage(), "Message was not set correctly");
    }


}