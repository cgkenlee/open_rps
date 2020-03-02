package io.github.cgkenlee.open_rps.game.util;

import io.github.cgkenlee.open_rps.game.GameMode;
import io.github.cgkenlee.open_rps.game.GameState;
import io.github.cgkenlee.open_rps.game.Hand;

import static io.github.cgkenlee.open_rps.game.GameState.*;

/**
 * Open Rock Paper Scissors game outcome util
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-28
 */
public class GameOutcomeUtil {
    private static final GameOutcomeResolver STANDARD_GAME_OUTCOME_RESOLVER = new GameOutcomeResolver(new GameState[][]{
            {TIED, OPPONENT_WON, PLAYER_WON}, // ROCK vs ROCK, PAPER, SCISSORS
            {PLAYER_WON, TIED, OPPONENT_WON}, // PAPER vs ROCK, PAPER, SCISSORS
            {OPPONENT_WON, PLAYER_WON, TIED} // SCISSORS vs ROCK, PAPER, SCISSORS
    });

    private static final GameOutcomeResolver EXTENDED_GAME_OUTCOME_RESOLVER = new GameOutcomeResolver(new GameState[][]{
            {TIED, OPPONENT_WON, PLAYER_WON, PLAYER_WON, OPPONENT_WON}, // ROCK vs ROCK, PAPER, SCISSORS, LIZARD, SPOCK
            {PLAYER_WON, TIED, OPPONENT_WON, OPPONENT_WON, PLAYER_WON}, // PAPER vs ROCK, PAPER, SCISSORS, LIZARD, SPOCK
            {OPPONENT_WON, PLAYER_WON, TIED, PLAYER_WON, OPPONENT_WON}, // SCISSORS vs ROCK, PAPER, SCISSORS, LIZARD, SPOCK
            {OPPONENT_WON, PLAYER_WON, OPPONENT_WON, TIED, PLAYER_WON}, // LIZARD vs ROCK, PAPER, SCISSORS, LIZARD, SPOCK
            {PLAYER_WON, OPPONENT_WON, PLAYER_WON, OPPONENT_WON, TIED} // SPOCK vs ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    });

    public static GameState resolve(GameMode mode, Hand playerHand, Hand opponentHand) {
        switch (mode) {
            case STANDARD:
                return STANDARD_GAME_OUTCOME_RESOLVER.resolve(playerHand, opponentHand);
            case EXTENDED:
                return EXTENDED_GAME_OUTCOME_RESOLVER.resolve(playerHand, opponentHand);
            default:
                throw new IllegalArgumentException("Invalid game mode enum");
        }
    }
}
