package io.github.cgkenlee.open_rps.game.controller;

import io.github.cgkenlee.open_rps.game.GameMode;
import io.github.cgkenlee.open_rps.game.dto.GameDto;
import io.github.cgkenlee.open_rps.game.model.Game;
import io.github.cgkenlee.open_rps.game.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Open Rock Paper Scissors game controller
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-26
 */
@Controller
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;
    private final ModelMapper modelMapper;

    public GameController(GameService gameService,
                          ModelMapper modelMapper) {
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.POST, path = "")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto createGame(@RequestParam(defaultValue = "STANDARD") GameMode mode) {
        return modelMapper.map(
                gameService.createGame(mode),
                GameDto.class);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @ResponseBody
    public GameDto getGame(@PathVariable UUID id) {
        return modelMapper.map(
                gameService.getGame(id),
                GameDto.class);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    @ResponseBody
    public GameDto updateGame(@PathVariable UUID id, @RequestBody GameDto gameDto) {
        return modelMapper.map(
                gameService.updateGame(
                        id,
                        modelMapper.map(gameDto, Game.class)),
                GameDto.class);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}/cheat")
    @ResponseBody
    public GameDto cheat(@PathVariable UUID id) {
        return modelMapper.map(
                gameService.markGameAsCheated(id),
                GameDto.class);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable UUID id) {
        gameService.deleteGame(id);
    }
}
