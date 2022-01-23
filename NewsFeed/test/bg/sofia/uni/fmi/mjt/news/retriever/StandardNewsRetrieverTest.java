package bg.sofia.uni.fmi.mjt.news.retriever;

import bg.sofia.uni.fmi.mjt.news.parser.ResponseParser;
import bg.sofia.uni.fmi.mjt.news.response.NewsEntity;
import bg.sofia.uni.fmi.mjt.news.response.OKResponse;
import bg.sofia.uni.fmi.mjt.news.uribuilder.CountryCode;
import bg.sofia.uni.fmi.mjt.news.uribuilder.UriBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import javax.management.RuntimeErrorException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StandardNewsRetrieverTest {
    private static UriBuilder correctUri = UriBuilder.newBuilder().keywords("trump");
    private static UriBuilder errorUri = UriBuilder.newBuilder().country(CountryCode.DE);
    private static Collection<NewsEntity> defaultResponse;

    @BeforeAll
    static void setUp() throws IOException, InterruptedException, URISyntaxException {
        HttpResponse<String> res = HttpClient.newBuilder().build()
                .send(HttpRequest.newBuilder(correctUri.build()).build(),
                HttpResponse.BodyHandlers.ofString());
        defaultResponse = ResponseParser.createNew(res).parseTo(OKResponse.class).get().getArticles();
    }
    @BeforeAll
    static void clearDemoFile() {
        try (PrintWriter writer = new PrintWriter("file.txt")) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateDefault() {
        assertNotNull(StandardNewsRetriever.createDefault(), "Error while creating default retriever");
    }

    @Test
    void testCreate() {
        assertThrows(RuntimeException.class, () -> StandardNewsRetriever.create(errorUri).then(),
                "expected to throw error when invalid uri is used");
        assertThrows(RuntimeException.class, () -> StandardNewsRetriever.create(null).then(),
                "expected to throw error when null is used");

    }

    @Test
    void testWhere() {
        assertNotNull(StandardNewsRetriever.create(errorUri).where());
    }

    @Test
    void testThen() {
        assertDoesNotThrow(() -> StandardNewsRetriever.create(correctUri).then());
    }

    @Test
    void testRetrieveTopN() throws IOException, URISyntaxException, InterruptedException {
        final int n = 20;
        assertThrows(IllegalArgumentException.class, () ->
                StandardNewsRetriever.create(correctUri).then().retrieveTopN(-1),
                "Expected to be thrown exception when n is negative");
        assertEquals(List.copyOf(defaultResponse).get(0).getAuthor(),
                List.copyOf(StandardNewsRetriever.create(correctUri).then().retrieveTopN(n)).get(0).getAuthor());
    }

    @Test
    void testRetrieveTopNFile() throws IOException, URISyntaxException, InterruptedException {
        final int n = 20;
        StandardNewsRetriever.create(correctUri).then().retrieveTopN(n, "file.txt");
        File file = new File("file.txt");
        assertTrue(file.length() != 0);
    }

    @AfterAll
    static void cleanup() {
        try (PrintWriter writer = new PrintWriter("file.txt")) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}