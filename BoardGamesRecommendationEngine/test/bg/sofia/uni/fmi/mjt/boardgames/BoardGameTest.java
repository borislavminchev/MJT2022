package bg.sofia.uni.fmi.mjt.boardgames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

    private static final String ARGUMENT_LIST = "id;details.maxplayers;" +
            "details.minage;details.minplayers;details.name;" +
            "details.playingtime;attributes.boardgamecategory;" +
            "attributes.boardgamemechanic;details.description";
    private static final String WRONG_ARGUMENT_LIST = "id;BONUSARGUMENT;details.maxplayers;" +
            "details.minage;details.minplayers;details.name;" +
            "details.playingtime;attributes.boardgamecategory;" +
            "attributes.boardgamemechanic;details.description";

    @Test
    public void testCreating() {
        BoardGame game = BoardGame.of("1;5;14;3;Demo Name;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", ARGUMENT_LIST);
        final int expectedId = 1;
        final String expectedName = "Demo Name";
        final String expectedDescription = "Description and other";
        final int expectedMaxPlayers = 5;
        final int expectedMinAge = 14;
        final int expectedMinPlayers = 3;
        final int expectedPlayingTime = 240;

        assertEquals(expectedId, game.id());
        assertEquals(expectedName, game.name());
        assertEquals(expectedDescription, game.description());
        assertEquals(expectedMaxPlayers, game.maxPlayers());
        assertEquals(expectedMinAge, game.minAge());
        assertEquals(expectedMinPlayers, game.minPlayers());
        assertEquals(expectedPlayingTime, game.playingTimeMins());
        assertTrue(game.categories().contains("Negotiation"));
        assertTrue(game.mechanics().contains("Dice Rolling"));
    }

    @Test
    public void testCreatingWrongArgList() {
        assertThrows(RuntimeException.class, () -> BoardGame.of("1;5;14;3;Demo Name;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", WRONG_ARGUMENT_LIST));
    }

    @Test
    public void testEqualsSameId() {
        BoardGame game1 = BoardGame.of("1;5;14;3;Demo Name;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", ARGUMENT_LIST);
        BoardGame game2 = BoardGame.of("1;5;14;3;Demo Name;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", ARGUMENT_LIST);
        BoardGame game3 = BoardGame.of("1;5;14;3;Other Game;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", ARGUMENT_LIST);

        assertTrue(game1.equals(game2));
        assertFalse(game1.equals(game3));
    }

    @Test
    public void testEqualsDifferentId() {
        BoardGame game1 = BoardGame.of("1;5;14;3;Demo Name;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", ARGUMENT_LIST);
        BoardGame game2 = BoardGame.of("4;5;14;3;Demo Name;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", ARGUMENT_LIST);
        BoardGame game3 = BoardGame.of("7;5;14;3;Other Game;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", ARGUMENT_LIST);

        assertTrue(game1.equals(game2));
        assertFalse(game1.equals(game3));
    }

    @Test
    public void testHashCode() {
        BoardGame game1 = BoardGame.of("1;5;14;3;Demo Name;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", ARGUMENT_LIST);
        BoardGame game2 = BoardGame.of("1;5;14;3;Demo Name;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", ARGUMENT_LIST);
        BoardGame differentId = BoardGame.of("2;5;14;3;Demo Name;240;Negotiation;" +
                "Auction/Bidding,Dice Rolling;Description and other", ARGUMENT_LIST);

        assertTrue(game1.hashCode() == game2.hashCode());
        assertFalse(game1.hashCode() == differentId.hashCode());
    }
}