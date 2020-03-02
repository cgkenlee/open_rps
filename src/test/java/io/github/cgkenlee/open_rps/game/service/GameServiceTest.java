package io.github.cgkenlee.open_rps.game.service;

import io.github.cgkenlee.open_rps.game.GameMode;
import io.github.cgkenlee.open_rps.game.GameState;
import io.github.cgkenlee.open_rps.game.Hand;
import io.github.cgkenlee.open_rps.game.model.Game;
import io.github.cgkenlee.open_rps.game.repository.GameRepository;
import io.github.cgkenlee.open_rps.game.util.HandUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static io.github.cgkenlee.open_rps.game.Hand.LIZARD;
import static io.github.cgkenlee.open_rps.game.Hand.SPOCK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * {@link GameService} unit test
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-29
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@Tag("CITest")
public class GameServiceTest {
    private final GameService gameService;

    @Autowired
    public GameServiceTest(GameService gameService) {
        this.gameService = gameService;
    }

    @MockBean
    private GameRepository gameRepository;

    @BeforeEach
    void setup() {
        Mockito.when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.doNothing().when(gameRepository).deleteById(any(UUID.class));
    }

    @AfterEach
    void cleanup() {
        Mockito.reset(gameRepository);
    }

    @Test
    @DisplayName("#createGame")
    void createGame() {
        // Case 1: STANDARD
        Game standardGame = gameService.createGame(GameMode.STANDARD);

        assertNotNull(standardGame.getId());
        assertEquals(GameMode.STANDARD, standardGame.getMode());
        assertNotNull(standardGame.getOpponentHand());
        assertNull(standardGame.getPlayerHand());
        assertEquals(GameState.NOT_PLAYED, standardGame.getState());

        // Case 2: EXTENDED
        Game extendedGame = gameService.createGame(GameMode.EXTENDED);

        assertNotNull(extendedGame.getId());
        assertEquals(GameMode.EXTENDED, extendedGame.getMode());
        assertNotNull(extendedGame.getOpponentHand());
        assertNull(extendedGame.getPlayerHand());
        assertEquals(GameState.NOT_PLAYED, extendedGame.getState());
    }

    @Test
    @DisplayName("#getGame")
    void getGame() {
        Game game = new Game();
        Mockito.when(gameRepository.findById(any(UUID.class))).thenReturn(Optional.of(game));

        assertDoesNotThrow(() -> gameService.getGame(game.getId()));
    }

    @Test
    @DisplayName("#getGame - GameNotFoundException")
    void getGame_GameNotFoundException() {
        Mockito.when(gameRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.getGame(UUID.randomUUID()));
    }

    @Test
    @DisplayName("#updateGame")
    void updateGame() {
        Hand playerHand = HandUtil.generateRandomHand();

        // Case 1: Play a hand
        Mockito.when(gameRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Game()));

        Game game = new Game();
        game.setPlayerHand(playerHand);

        Game updatedGame = gameService.updateGame(game.getId(), game);

        assertEquals(playerHand, updatedGame.getPlayerHand());
        assertNotEquals(GameState.NOT_PLAYED, updatedGame.getState());

        // Case 2: Change game mode only
        Mockito.when(gameRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Game()));

        Game game2 = new Game(GameMode.EXTENDED);

        Game updatedGame2 = gameService.updateGame(game2.getId(), game2);

        assertEquals(GameMode.EXTENDED, updatedGame2.getMode());
        assertNull(updatedGame2.getPlayerHand());
        assertEquals(GameState.NOT_PLAYED, updatedGame2.getState());

        // Case 3: Change game mode only from EXTENDED to STANDARD with an EXTENDED opponent hand

        Game extendedGameWithExtendedOpponentHand = new Game(GameMode.EXTENDED);
        while (HandUtil.isHandValidForMode(extendedGameWithExtendedOpponentHand.getOpponentHand(), GameMode.STANDARD)) {
            extendedGameWithExtendedOpponentHand.setOpponentHand(HandUtil.generateRandomHand(GameMode.EXTENDED));
        }

        Mockito.when(gameRepository.findById(any(UUID.class))).thenReturn(Optional.of(extendedGameWithExtendedOpponentHand));

        Game game3 = new Game(GameMode.STANDARD);

        Game updatedGame3 = gameService.updateGame(game3.getId(), game3);

        assertEquals(GameMode.STANDARD, updatedGame3.getMode());
        assertTrue(HandUtil.isHandValidForMode(updatedGame3.getOpponentHand(), GameMode.STANDARD));
        assertNull(updatedGame3.getPlayerHand());
        assertEquals(GameState.NOT_PLAYED, updatedGame3.getState());
    }

    @Test
    @DisplayName("#updateGame - IllegalArgumentException: Play an invalid hand for the game mode")
    void updateGame_IllegalArgumentException() {
        Mockito.when(gameRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Game(GameMode.STANDARD)));

        Game game = new Game(GameMode.STANDARD);
        game.setPlayerHand(LIZARD);

        assertThrows(IllegalArgumentException.class, () -> gameService.updateGame(UUID.randomUUID(), game));

        game.setPlayerHand(SPOCK);

        assertThrows(IllegalArgumentException.class, () -> gameService.updateGame(UUID.randomUUID(), game));
    }

    @Test
    @DisplayName("#markGameAsCheated")
    void markGameAsCheated() {
        Mockito.reset(gameRepository);

        Game game = new Game();

        Mockito.when(gameRepository.save(game)).thenReturn(game);
        Mockito.when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));

        Game cheatedGame = gameService.markGameAsCheated(game.getId());

        assertTrue(cheatedGame.isCheated());
    }

    @Test
    @DisplayName("#deleteGame")
    void deleteGame() {
        assertDoesNotThrow(() -> gameService.deleteGame(UUID.randomUUID()));

        Mockito.verify(gameRepository).deleteById(any(UUID.class));
    }
}
