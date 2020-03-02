package io.github.cgkenlee.open_rps.game.util;

import io.github.cgkenlee.open_rps.game.GameState;
import org.junit.jupiter.api.*;

import static io.github.cgkenlee.open_rps.game.GameState.*;
import static io.github.cgkenlee.open_rps.game.Hand.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link GameOutcomeResolver} unit test
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-28
 */
@Tag("CITest")
public class GameOutcomeResolverTest {
    private static final GameOutcomeResolver TEST_GAME_OUTCOME_RESOLVER = new GameOutcomeResolver(new GameState[][]{
            // standard hand outcomes
            {TIED, OPPONENT_WON, PLAYER_WON}, // ROCK vs ROCK, PAPER, SCISSORS
            {PLAYER_WON, TIED, OPPONENT_WON}, // PAPER vs ROCK, PAPER, SCISSORS
            {OPPONENT_WON, PLAYER_WON, TIED}, // SCISSORS vs ROCK, PAPER, SCISSORS
            // test hand outcomes
            {NOT_PLAYED} // LIZARD vs ROCK = NOT_PLAYED
    });

    @Test
    @DisplayName("#resolve - success cases")
    void resolve_success() {
        assertEquals(TIED, TEST_GAME_OUTCOME_RESOLVER.resolve(ROCK, ROCK));
        assertEquals(OPPONENT_WON, TEST_GAME_OUTCOME_RESOLVER.resolve(ROCK, PAPER));
        assertEquals(PLAYER_WON, TEST_GAME_OUTCOME_RESOLVER.resolve(ROCK, SCISSORS));

        assertEquals(NOT_PLAYED, TEST_GAME_OUTCOME_RESOLVER.resolve(LIZARD, ROCK));
    }

    @Test
    @DisplayName("#resolve - exception cases: Should throw IllegalArgumentException if given hands cause ArrayIndexOutOfBoundsException")
    void resolve_exception() {
        assertThrows(IllegalArgumentException.class, () -> TEST_GAME_OUTCOME_RESOLVER.resolve(ROCK, LIZARD));
        assertThrows(IllegalArgumentException.class, () -> TEST_GAME_OUTCOME_RESOLVER.resolve(SPOCK, ROCK));
    }
}
