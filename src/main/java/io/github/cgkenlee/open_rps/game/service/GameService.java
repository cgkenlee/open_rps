package io.github.cgkenlee.open_rps.game.service;

import io.github.cgkenlee.open_rps.game.GameMode;
import io.github.cgkenlee.open_rps.game.model.Game;

import java.util.UUID;

/**
 * Open Rock Paper Scissors game service
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-26
 */
public interface GameService {
    /**
     * Create a new game with the given game mode
     *
     * @param mode GameMode for the new game
     * @return Game
     */
    Game createGame(GameMode mode);

    /**
     * Get a game
     *
     * @param id UUID of the game
     * @return Game
     */
    Game getGame(UUID id);

    /**
     * Update a game
     *
     * @param id UUID of the game
     * @param game Game model
     * @return Game
     */
    Game updateGame(UUID id, Game game);

    //TODO: prevent cheating of a played game, to be consistent with links
    /**
     * Mark the game as cheated to reveal opponent's hand
     *
     * @param id UUID of the game
     * @return Game
     */
    Game markGameAsCheated(UUID id);

    /**
     * Delete a game
     *
     * @param id UUID of the game
     */
    void deleteGame(UUID id);
}
