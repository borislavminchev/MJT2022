package bg.sofia.uni.fmi.mjt.news.uribuilder;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UriBuilderTest {
    @Test
    void testNewBuilder() {
        assertDoesNotThrow(() -> UriBuilder.newBuilder(), "Expected not to throw anything" +
                " when method newBuilder() is called");
        assertThrows(RuntimeException.class, () -> UriBuilder.newBuilder().build(), "Expected to throw error when" +
               " we try to create empty builder");
    }

    @Test
    void testCountry() {
        final String expected1 = "country=us";
        final String expected2 = "country=bg";
        assertThrows(RuntimeException.class, () -> UriBuilder.newBuilder().country(null),
                "Expected to throw exception whe we try to set country with null");
        assertTrue(UriBuilder.newBuilder().country(CountryCode.US).getQuery().contains(expected1),
                "Country property was not set correctly. Expected to contain country=us");
        assertTrue(UriBuilder.newBuilder().country(CountryCode.BG).getQuery().contains(expected2),
                "Country property was not set correctly. Expected to contain country=bg");
    }

    @Test
    void testCategory() {
        final String expected1 = "category=business";
        final String expected2 = "category=health";
        assertThrows(RuntimeException.class, () -> UriBuilder.newBuilder().category(null),
                "Expected to throw exception whe we try to set category with null");
        assertTrue(UriBuilder.newBuilder().category(NewsCategory.BUSINESS).getQuery().contains(expected1),
                "Category property was not set correctly. Expected to contain category=business");
        assertTrue(UriBuilder.newBuilder().category(NewsCategory.HEALTH).getQuery().contains(expected2),
                "Category property was not set correctly. Expected to contain category=health");
    }

    @Test
    void testKeywordsSingle() {
        final String expected1 = "q=virus";
        assertThrows(RuntimeException.class, () -> UriBuilder.newBuilder().keywords(null),
                "Expected to throw exception whe we try to set keywords with null");
        assertTrue(UriBuilder.newBuilder().keywords("virus").getQuery().contains(expected1),
                "Keywords property was not set correctly. Expected to contain q=virus");
    }
    @Test
    void testKeywordsTwo() {
        final String expected1 = "q=virus+health";
        assertThrows(RuntimeException.class, () -> UriBuilder.newBuilder().keywords(null, null),
                "Expected to throw exception whe we try to set keywords with null");
        assertTrue(UriBuilder.newBuilder().keywords("virus", "health").getQuery().contains(expected1),
                "Keywords property was not set correctly. Expected to contain q=virus+health");
        assertTrue(UriBuilder.newBuilder().keywords("virus", null, "health").getQuery().contains(expected1),
                "Keywords property was not set correctly. Expected to contain q=virus+health");
    }


    @Test
    void testBuildNoKeywords() {
        assertThrows(RuntimeException.class, () -> UriBuilder.newBuilder().build(), "Expected to throw error when" +
                " we try to create empty builder");
        assertThrows(RuntimeException.class, () -> UriBuilder.newBuilder().country(CountryCode.DE).build(),
                "Expected to throw error when we try to create builder without set keywords field");
    }

    @Test
    void testBuildWith() {
        assertDoesNotThrow(() -> UriBuilder.newBuilder().keywords("virus").country(CountryCode.DE).build(),
                "Expected to throw error when we try to create empty builder");
    }

    @Test
    void testRetrieve() {
        assertNotNull(UriBuilder.newBuilder().keywords("virus").retrieve());
    }


}