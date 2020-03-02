package io.github.cgkenlee.open_rps.game.controller.advice;

import io.github.cgkenlee.open_rps.dto.ErrorDto;
import io.github.cgkenlee.open_rps.game.service.GameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Open Rock Paper Scissors exception handler
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-28
 */
@ControllerAdvice
public class GameExceptionHandler {
    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleGameNotFoundException(GameNotFoundException e) {
        return new ErrorDto(
                HttpStatus.NOT_FOUND,
                e.getMessage());
    }
}
