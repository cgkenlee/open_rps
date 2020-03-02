package io.github.cgkenlee.open_rps.game.util;

import io.github.cgkenlee.open_rps.game.GameMode;
import io.github.cgkenlee.open_rps.game.Hand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link HandUtil} unit test
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-29
 */
@Tag("CITest")
public class HandUtilTest {
    private final Set<Hand> standardHandSet = new HashSet<>(Arrays.asList(Hand.ROCK, Hand.PAPER, Hand.SCISSORS));
    private final Set<Hand> extendedHandSet = new HashSet<>(Arrays.asList(Hand.ROCK, Hand.PAPER, Hand.SCISSORS, Hand.LIZARD, Hand.SPOCK));
    private final Set<Hand> standardInvalidHandSet = extendedHandSet.stream()
            .filter(hand -> !standardHandSet.contains(hand))
            .collect(Collectors.toSet());

    @Test
    @DisplayName("#generateRandomHand")
    void generateRandomHand() {
        for (int i = 0; i < 100; i++) {
            Hand hand = HandUtil.generateRandomHand(GameMode.STANDARD);
            assertTrue(standardHandSet.contains(hand));
            assertFalse(standardInvalidHandSet.contains(hand));
        }

        for (int i = 0; i < 100; i++) {
            Hand hand = HandUtil.generateRandomHand(GameMode.EXTENDED);
            assertTrue(extendedHandSet.contains(hand));
        }
    }

    @Test
    @DisplayName("#isHandValidForMode")
    void gisHandValidForMode() {
        assertTrue(HandUtil.isHandValidForMode(Hand.ROCK, GameMode.STANDARD));
        assertTrue(HandUtil.isHandValidForMode(Hand.PAPER, GameMode.STANDARD));
        assertTrue(HandUtil.isHandValidForMode(Hand.SCISSORS, GameMode.STANDARD));
        assertFalse(HandUtil.isHandValidForMode(Hand.LIZARD, GameMode.STANDARD));
        assertFalse(HandUtil.isHandValidForMode(Hand.SPOCK, GameMode.STANDARD));

        assertTrue(HandUtil.isHandValidForMode(Hand.ROCK, GameMode.EXTENDED));
        assertTrue(HandUtil.isHandValidForMode(Hand.PAPER, GameMode.EXTENDED));
        assertTrue(HandUtil.isHandValidForMode(Hand.SCISSORS, GameMode.EXTENDED));
        assertTrue(HandUtil.isHandValidForMode(Hand.LIZARD, GameMode.EXTENDED));
        assertTrue(HandUtil.isHandValidForMode(Hand.SPOCK, GameMode.EXTENDED));
    }
}
