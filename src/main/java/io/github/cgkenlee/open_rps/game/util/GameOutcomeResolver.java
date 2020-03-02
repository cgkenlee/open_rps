package io.github.cgkenlee.open_rps.game.util;

import io.github.cgkenlee.open_rps.game.GameState;
import io.github.cgkenlee.open_rps.game.Hand;

/**
 * Open Rock Paper Scissors game outcome resolver using a lookup matrix
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-28
 */
public class GameOutcomeResolver {
    private final GameState[][] lookupMatrix;

    /**
     * Constructor for HandOutcomeResolver
     *
     * @param lookupMatrix 2d array of GameState of [playerHand ordinal][opponentHand ordinal]
     */
    public GameOutcomeResolver(GameState[][] lookupMatrix) {
        this.lookupMatrix = lookupMatrix;
    }

    /**
     * Resolve the outcome of the two hands
     *
     * @param playerHand Hand enum for the player
     * @param opponentHand Hand enum for the opponent
     * @return GameState
     */
    public GameState resolve(Hand playerHand, Hand opponentHand) {
        try {
            return lookupMatrix[playerHand.ordinal()][opponentHand.ordinal()];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid hand enum for the game mode provided");
        }
    }
}
