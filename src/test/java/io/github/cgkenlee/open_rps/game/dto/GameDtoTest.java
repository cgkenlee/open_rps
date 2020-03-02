package io.github.cgkenlee.open_rps.game.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.github.cgkenlee.open_rps.game.GameMode.EXTENDED;
import static io.github.cgkenlee.open_rps.game.GameState.*;
import static io.github.cgkenlee.open_rps.game.Hand.LIZARD;
import static io.github.cgkenlee.open_rps.game.Hand.SPOCK;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link GameDto} unit test
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-28
 */
@Tag("CITest")
public class GameDtoTest {
    @Test
    @DisplayName("#getOpponentHandString")
    void getOpponentHandString() {
        GameDto gameDto = new GameDto(null, EXTENDED, SPOCK, null, NOT_PLAYED, false);

        assertEquals("SECRET", gameDto.getOpponentHandString());

        gameDto.setCheated(true);

        assertEquals("SPOCK", gameDto.getOpponentHandString());

        gameDto.setCheated(false);
        gameDto.setState(PLAYER_WON);

        assertEquals("SPOCK", gameDto.getOpponentHandString());
    }

    @Test
    @DisplayName("#getPlayerHandString")
    void getPlayerHandString() {
        GameDto gameDto = new GameDto(null, EXTENDED, SPOCK, null, NOT_PLAYED, false);

        assertEquals("NOT_PLAYED", gameDto.getPlayerHandString());

        gameDto.setPlayerHand(LIZARD);

        assertEquals("LIZARD", gameDto.getPlayerHandString());
    }
}
