package io.github.cgkenlee.open_rps.game;

/**
 * Open Rock Paper Scissors game state enum
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-26
 */
public enum GameState {
    /**
     * Game is not yet played
     */
    NOT_PLAYED,
    /**
     * Game is played and player won
     */
    PLAYER_WON,
    /**
     * Game is played and player and opponent tied
     */
    TIED,
    /**
     * Game is played and opponent won
     */
    OPPONENT_WON
}
