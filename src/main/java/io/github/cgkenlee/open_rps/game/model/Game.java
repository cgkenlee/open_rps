package io.github.cgkenlee.open_rps.game.model;

import io.github.cgkenlee.open_rps.game.GameMode;
import io.github.cgkenlee.open_rps.game.GameState;
import io.github.cgkenlee.open_rps.game.Hand;
import io.github.cgkenlee.open_rps.game.util.HandUtil;
import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * Open Rock Paper Scissors Game model
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-26
 */
public class Game {
    @Id
    private final UUID id;
    private GameMode mode;
    private Hand opponentHand;
    private Hand playerHand;
    private GameState state;
    private boolean cheated;

    public Game() {
        id = UUID.randomUUID();
        mode = GameMode.STANDARD;
        opponentHand = HandUtil.generateRandomHand();
        state = GameState.NOT_PLAYED;
    }

    public Game(GameMode mode) {
        id = UUID.randomUUID();
        this.mode = mode;
        opponentHand = HandUtil.generateRandomHand(mode);
        state = GameState.NOT_PLAYED;
    }

    public UUID getId() {
        return id;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public Hand getOpponentHand() {
        return opponentHand;
    }

    public void setOpponentHand(Hand opponentHand) {
        this.opponentHand = opponentHand;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public boolean isCheated() {
        return cheated;
    }

    public void setCheated(boolean cheated) {
        this.cheated = cheated;
    }
}
