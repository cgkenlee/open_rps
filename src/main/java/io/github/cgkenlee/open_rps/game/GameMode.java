package io.github.cgkenlee.open_rps.game;

/**
 * Open Rock Paper Scissors game mode enum
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-28
 */
public enum GameMode {
    /**
     * Standard game mode with hands: {@link Hand#ROCK}, {@link Hand#SCISSORS}, {@link Hand#PAPER}
     */
    STANDARD (3),
    /**
     * Extended game mode with hands: {@link Hand#ROCK}, {@link Hand#SCISSORS}, {@link Hand#PAPER}, {@link Hand#LIZARD}, {@link Hand#SPOCK}
     */
    EXTENDED (5);

    private final int numberOfHands;

    GameMode(int numberOfHands) {
        this.numberOfHands = numberOfHands;
    }

    public int getNumberOfHands() {
        return numberOfHands;
    }
}
