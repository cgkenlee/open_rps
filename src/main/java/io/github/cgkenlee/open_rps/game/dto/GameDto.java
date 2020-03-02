package io.github.cgkenlee.open_rps.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.cgkenlee.open_rps.game.GameMode;
import io.github.cgkenlee.open_rps.game.GameState;
import io.github.cgkenlee.open_rps.game.Hand;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

/**
 * Open Rock Paper Scissors Game DTO
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-26
 */
public class GameDto extends RepresentationModel<GameDto> {
    private UUID id;
    private GameMode mode;
    private Hand opponentHand;
    private Hand playerHand;
    private GameState state;
    private boolean cheated;

    public GameDto() {
    }

    public GameDto(UUID id, GameMode mode, Hand opponentHand, Hand playerHand, GameState state, boolean cheated) {
        this.id = id;
        this.mode = mode;
        this.opponentHand = opponentHand;
        this.playerHand = playerHand;
        this.state = state;
        this.cheated = cheated;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    /**
     * Custom serializer for opponentHand field
     *
     * @return actual hand if cheated or game was already played, "SECRET" otherwise
     */
    @JsonProperty("opponentHand")
    public String getOpponentHandString() {
        if (cheated || state != GameState.NOT_PLAYED) {
            return opponentHand.name();
        } else {
            return "SECRET";
        }
    }

    public void setOpponentHand(Hand opponentHand) {
        this.opponentHand = opponentHand;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    /**
     * Custom serializer for playerHand field
     *
     * @return actual hand if game was already played, "NOT_PLAYED" otherwise
     */
    @JsonProperty("playerHand")
    public String getPlayerHandString() {
        return playerHand == null ? "NOT_PLAYED" : playerHand.name();
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
