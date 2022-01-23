package bg.sofia.uni.fmi.mjt.news.response;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


class OKResponseTest {
    private static OKResponse response;
    private static NewsEntity entity;

    @BeforeAll
    static void setUp() throws IOException {
        entity = new NewsEntity(new Source("test", "Test"),
                "Author", "Title", "Description",
                new URL("https://www.google.com/"), new URL("https://www.google.com/"),
                "published at", "content");
        response = new OKResponse(1, List.of(entity));
    }

    @Test
    void testCorrectSetResponseOK() {
        final int expectedTotalResults = 1;
        final Status expectedStatus = Status.OK;

        assertEquals(expectedTotalResults, response.getTotalResults(), "Total results was not set correctly");
        assertEquals(expectedStatus, response.getStatus(), "Status was not set correctly, expected status OK");
        assertIterableEquals(List.of(entity), response.getArticles(), "Articles were not set correctly");
    }

    @Test
    void testCorrectSetInfoOK() {
        final String expectedSourceId = "test";
        final String expectedSourceName = "Test";
        final String expectedAuthor = "Author";
        final String expectedTitle = "Title";
        final String expectedDescription = "Description";
        final String expectedUrl = "https://www.google.com/";
        final String expectedUrlToImg = "https://www.google.com/";
        final String expectedPublishedAt = "published at";
        final String expectedContent = "content";

        assertEquals(expectedSourceId, entity.getSource().getId());
        assertEquals(expectedSourceName, entity.getSource().getName());
        assertEquals(expectedAuthor, entity.getAuthor());
        assertEquals(expectedTitle, entity.getTitle());
        assertEquals(expectedDescription, entity.getDescription());
        assertEquals(expectedUrl, entity.getUrl().toString());
        assertEquals(expectedUrlToImg, entity.getUrlToImage().toString());
        assertEquals(expectedPublishedAt, entity.getPublishedAt());
        assertEquals(expectedContent, entity.getContent());
    }
}