package io.github.cgkenlee.open_rps.game.service.impl;

import io.github.cgkenlee.open_rps.game.GameMode;
import io.github.cgkenlee.open_rps.game.model.Game;
import io.github.cgkenlee.open_rps.game.service.GameNotFoundException;
import io.github.cgkenlee.open_rps.game.repository.GameRepository;
import io.github.cgkenlee.open_rps.game.service.GameService;
import io.github.cgkenlee.open_rps.game.util.GameOutcomeUtil;
import io.github.cgkenlee.open_rps.game.util.HandUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Open Rock Paper Scissors game service implementation
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-26
 */
@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game createGame(GameMode mode) {
        Game newGame = new Game(mode);

        gameRepository.save(newGame);

        return newGame;
    }

    @Override
    public Game getGame(UUID id) {
        Optional<Game> gameOptional = gameRepository.findById(id);

        if (gameOptional.isPresent()) {
            return gameOptional.get();
        } else {
            throw new GameNotFoundException("Game not found");
        }
    }

    @Override
    public Game updateGame(UUID id, Game game) {
        Game existingGame = getGame(id);

        if (game.getMode() != null && existingGame.getMode() != game.getMode()) {
            existingGame.setMode(game.getMode());
            if (!HandUtil.isHandValidForMode(existingGame.getOpponentHand(), existingGame.getMode())) {
                existingGame.setOpponentHand(HandUtil.generateRandomHand(existingGame.getMode()));
            }
        }

        if (game.getPlayerHand() != null) {
            existingGame.setPlayerHand(game.getPlayerHand());
            existingGame.setState(GameOutcomeUtil.resolve(
                    existingGame.getMode(),
                    existingGame.getPlayerHand(),
                    existingGame.getOpponentHand()));
        }

        return gameRepository.save(existingGame);
    }

    @Override
    public Game markGameAsCheated(UUID id) {
        Game game = getGame(id);
        game.setCheated(true);

        gameRepository.save(game);

        return game;
    }

    @Override
    public void deleteGame(UUID id) {
        gameRepository.deleteById(id);
    }
}
