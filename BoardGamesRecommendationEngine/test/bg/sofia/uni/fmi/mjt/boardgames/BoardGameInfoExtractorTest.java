package bg.sofia.uni.fmi.mjt.boardgames;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameInfoExtractorTest {

    private static final String ARGUMENT_LIST = "id;details.maxplayers;" +
            "details.minage;details.minplayers;details.name;" +
            "details.playingtime;attributes.boardgamecategory;" +
            "attributes.boardgamemechanic;details.description";

    private static final String ERROR_ARGUMENT_LIST = "id;BONUSARG;details.maxplayers;" +
            "details.minage;details.minplayers;details.name;" +
            "details.playingtime;attributes.boardgamecategory;" +
            "attributes.boardgamemechanic;details.description";

    private static final String DEMO_GAME_STRING = "1;5;14;3;Demo Name;240;Negotiation;" +
            "Auction/Bidding,Dice Rolling;Description and other";


    private final BoardGameInfoExtractor extractor = new BoardGameInfoExtractor(List.of(ARGUMENT_LIST.split(";")));

    @Test
    public void testConstructing() {
        assertDoesNotThrow(() -> new BoardGameInfoExtractor(List.of(ARGUMENT_LIST.split(";"))));
        assertThrows(RuntimeException.class, () ->
                new BoardGameInfoExtractor(List.of(ERROR_ARGUMENT_LIST.split(";"))));
    }

    @Test
    void testExtractId() {
        final int expected = 1;
        assertEquals(expected, extractor.extractId(List.of(DEMO_GAME_STRING.split(";"))));
    }

    @Test
    void testExtractName() {
        final String expected = "Demo Name";
        assertEquals(expected, extractor.extractName(List.of(DEMO_GAME_STRING.split(";"))));
    }

    @Test
    void testExtractDescription() {
        final String expected = "Description and other";
        assertEquals(expected, extractor.extractDescription(List.of(DEMO_GAME_STRING.split(";"))));
    }

    @Test
    void testExtractMaxPlayers() {
        final int expected = 5;
        assertEquals(expected, extractor.extractMaxPlayers(List.of(DEMO_GAME_STRING.split(";"))));
    }

    @Test
    void testExtractMinAge() {
        final int expected = 14;
        assertEquals(expected, extractor.extractMinAge(List.of(DEMO_GAME_STRING.split(";"))));
    }

    @Test
    void testExtractMinPlayers() {
        final int expected = 3;
        assertEquals(expected, extractor.extractMinPlayers(List.of(DEMO_GAME_STRING.split(";"))));
    }

    @Test
    void testExtractPlayingTime() {
        final int expected = 240;
        assertEquals(expected, extractor.extractPlayingTime(List.of(DEMO_GAME_STRING.split(";"))));
    }

    @Test
    void testExtractCategories() {
        assertTrue(extractor.extractCategories(List.of(DEMO_GAME_STRING.split(";"))).contains("Negotiation"));
    }

    @Test
    void testExtractMechanics() {
        assertTrue(extractor.extractMechanics(List.of(DEMO_GAME_STRING.split(";"))).contains("Dice Rolling"));
    }
}