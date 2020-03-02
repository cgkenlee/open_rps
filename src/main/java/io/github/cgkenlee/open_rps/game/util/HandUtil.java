package io.github.cgkenlee.open_rps.game.util;

import io.github.cgkenlee.open_rps.game.GameMode;
import io.github.cgkenlee.open_rps.game.Hand;

import java.util.Random;

/**
 * Hand util
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-03-02
 */
public class HandUtil {
    /**
     * Generates a random standard mode {@see GameMode.STANDARD} hand
     *
     * @return a random standard mode Hand
     */
    public static Hand generateRandomHand() {
        return generateRandomHand(GameMode.STANDARD);
    }

    /**
     * Generates a random hand based on the game mode
     *
     * @param mode Game mode to be used
     * @return a valid random Hand for the game mode
     */
    public static Hand generateRandomHand(GameMode mode) {
        Random random = new Random();

        return Hand.values()[random.nextInt(mode.getNumberOfHands())];
    }

    /**
     * Checks if the hand is valid for the game mode
     *
     * @param hand Hand to check for validity
     * @param mode Game mode to be used
     * @return boolean
     */
    public static boolean isHandValidForMode(Hand hand, GameMode mode) {
        return hand.ordinal() < mode.getNumberOfHands();
    }
}
