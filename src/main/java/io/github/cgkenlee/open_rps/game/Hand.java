package io.github.cgkenlee.open_rps.game;

/**
 * Open Rock Paper Scissors game hand enum
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-26
 */
public enum Hand {
    /**
     * Rock hand
     * <p>
     * Modes: {@link GameMode#STANDARD}, {@link GameMode#EXTENDED}
     */
    ROCK,
    /**
     * Paper hand
     * <p>
     * Modes: {@link GameMode#STANDARD}, {@link GameMode#EXTENDED}
     */
    PAPER,
    /**
     * Scissors hand
     * <p>
     *  Modes: {@link GameMode#STANDARD}, {@link GameMode#EXTENDED}
     */
    SCISSORS,
    /**
     * Lizard hand
     * <p>
     * Modes: {@link GameMode#EXTENDED}
     */
    LIZARD,
    /**
     * Spock hand
     * <p>
     * Modes: {@link GameMode#EXTENDED}
     */
    SPOCK
}
