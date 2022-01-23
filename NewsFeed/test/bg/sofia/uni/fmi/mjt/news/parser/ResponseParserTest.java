package bg.sofia.uni.fmi.mjt.news.parser;

import bg.sofia.uni.fmi.mjt.news.response.ErrorResponse;
import bg.sofia.uni.fmi.mjt.news.response.OKResponse;
import bg.sofia.uni.fmi.mjt.news.response.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class ResponseParserTest {

    private static final URI DEFAULT_URI = URI.create("https://newsapi.org/v2/top-headlines?q=trump&" +
            "apiKey=8d1153d6a324436e8690e4ed521d3716");

    private static final URI ERROR_URI = URI.create("https://newsapi.org/v2/top-headlines");
    private static HttpResponse<String> response;
    private static HttpResponse<String> errorResponse;


    @BeforeAll
    static void setUp() throws IOException, InterruptedException {
        HttpClient cl = HttpClient.newBuilder().build();
        response = cl.send(HttpRequest.newBuilder(DEFAULT_URI).build(), HttpResponse.BodyHandlers.ofString());
        errorResponse = cl.send(HttpRequest.newBuilder(ERROR_URI).build(), HttpResponse.BodyHandlers.ofString());
    }


    @Test
    public void testOkToOk() {
        OKResponse okResponse = ResponseParser.createNew(response).parseTo(OKResponse.class).orElse(null);

        assertNotNull(okResponse, "Parse error, expected Optional object to be NOT empty");
        assertEquals(Status.OK, okResponse.getStatus(), "Parse error, expected Status code to be OK");
        assertNotNull(okResponse.getArticles(),
                "Parse error, expected articles to be defined as list (currently is null)");
    }

    @Test
    public void testErrorToError() {
        ErrorResponse err = ResponseParser.createNew(errorResponse).parseTo(ErrorResponse.class).orElse(null);

        assertNotNull(err, "Parse error, expected Optional object to be NOT empty");
        assertEquals(Status.ERROR, err.getStatus(), "Parse error, expected Status code to be ERROR");
        assertEquals("apiKeyMissing", err.getCode(), "Parse error, expected error code apiKeyMissing");
    }

    @Test
    public void testThrowNull() {
        assertThrows(RuntimeException.class, () -> ResponseParser.createNew(null),
                "Expected to throw runtime error");
    }

    @Test
    public void testErrorToOk() {
        OKResponse resp = ResponseParser.createNew(errorResponse).parseTo(OKResponse.class).orElse(null);

        assertNull(resp, "Expected empty Optional to be returned");
    }

    @Test
    public void testOkToError() {
        ErrorResponse resp = ResponseParser.createNew(response).parseTo(ErrorResponse.class).orElse(null);

        assertNull(resp, "Expected empty Optional to be returned");
    }
}