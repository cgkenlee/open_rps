package io.github.cgkenlee.open_rps.game.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.github.cgkenlee.open_rps.game.GameMode.EXTENDED;
import static io.github.cgkenlee.open_rps.game.GameMode.STANDARD;
import static io.github.cgkenlee.open_rps.game.GameState.*;
import static io.github.cgkenlee.open_rps.game.Hand.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link GameOutcomeUtil} unit test
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-28
 */
@Tag("CITest")
public class GameOutcomeUtilTest {
    @Test
    @DisplayName("#resolve - standard game mode cases")
    void resolve_standard() {
        // Player hand of Rock
        assertEquals(TIED, GameOutcomeUtil.resolve(STANDARD, ROCK, ROCK));
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(STANDARD, ROCK, PAPER));
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(STANDARD, ROCK, SCISSORS));

        // Player hand of Paper
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(STANDARD, PAPER, ROCK));
        assertEquals(TIED, GameOutcomeUtil.resolve(STANDARD, PAPER, PAPER));
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(STANDARD, PAPER, SCISSORS));

        // Player hand of Scissors
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(STANDARD, SCISSORS, ROCK));
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(STANDARD, SCISSORS, PAPER));
        assertEquals(TIED, GameOutcomeUtil.resolve(STANDARD, SCISSORS, SCISSORS));
    }

    @Test
    @DisplayName("#resolve - extended game mode cases")
    void resolve_extended() {
        /*
        Scissors cuts Paper covers Rock crushes Lizard poisons Spock smashes Scissors
        decapitates Lizard eats Paper disproves Spock vaporizes Rock crushes Scissors
         */

        // Player hand of Rock
        assertEquals(TIED, GameOutcomeUtil.resolve(EXTENDED, ROCK, ROCK));
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(EXTENDED, ROCK, PAPER));
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(EXTENDED, ROCK, SCISSORS));
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(EXTENDED, ROCK, LIZARD));
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(EXTENDED, ROCK, SPOCK));

        // Player hand of Paper
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(EXTENDED, PAPER, ROCK));
        assertEquals(TIED, GameOutcomeUtil.resolve(EXTENDED, PAPER, PAPER));
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(EXTENDED, PAPER, SCISSORS));
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(EXTENDED, PAPER, LIZARD));
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(EXTENDED, PAPER, SPOCK));

        // Player hand of Scissors
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(EXTENDED, SCISSORS, ROCK));
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(EXTENDED, SCISSORS, PAPER));
        assertEquals(TIED, GameOutcomeUtil.resolve(EXTENDED, SCISSORS, SCISSORS));
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(EXTENDED, SCISSORS, LIZARD));
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(EXTENDED, SCISSORS, SPOCK));

        // Player hand of Lizard
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(EXTENDED, LIZARD, ROCK));
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(EXTENDED, LIZARD, PAPER));
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(EXTENDED, LIZARD, SCISSORS));
        assertEquals(TIED, GameOutcomeUtil.resolve(EXTENDED, LIZARD, LIZARD));
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(EXTENDED, LIZARD, SPOCK));

        // Player hand of Spock
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(EXTENDED, SPOCK, ROCK));
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(EXTENDED, SPOCK, PAPER));
        assertEquals(PLAYER_WON, GameOutcomeUtil.resolve(EXTENDED, SPOCK, SCISSORS));
        assertEquals(OPPONENT_WON, GameOutcomeUtil.resolve(EXTENDED, SPOCK, LIZARD));
        assertEquals(TIED, GameOutcomeUtil.resolve(EXTENDED, SPOCK, SPOCK));
    }
}
