package io.github.cgkenlee.open_rps.game.dto;

import io.github.cgkenlee.open_rps.game.GameState;
import io.github.cgkenlee.open_rps.game.controller.GameController;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * {@link GameDto} processor
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-29
 */
@Service
public class GameDtoProcessor implements RepresentationModelProcessor<GameDto> {
    @Override
    public GameDto process(GameDto gameDto) {
        gameDto.add(linkTo(methodOn(GameController.class).getGame(gameDto.getId())).withSelfRel());

        if (!gameDto.isCheated() && gameDto.getState() == GameState.NOT_PLAYED) {
            gameDto.add(linkTo(methodOn(GameController.class).cheat(gameDto.getId())).withRel("cheat"));
        }

        return gameDto;
    }
}
