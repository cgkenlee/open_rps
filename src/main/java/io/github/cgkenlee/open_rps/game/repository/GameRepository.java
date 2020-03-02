package io.github.cgkenlee.open_rps.game.repository;

import io.github.cgkenlee.open_rps.game.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * {@link Game} repository
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-26
 */
public interface GameRepository extends MongoRepository<Game, UUID> {
}
