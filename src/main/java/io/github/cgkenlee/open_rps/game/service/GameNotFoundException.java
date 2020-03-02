package io.github.cgkenlee.open_rps.game.service;

/**
 * Game not found exception
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-28
 */
public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
        super(message);
    }
}
