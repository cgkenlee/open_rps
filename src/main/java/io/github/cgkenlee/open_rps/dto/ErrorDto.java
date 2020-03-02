package io.github.cgkenlee.open_rps.dto;

import org.springframework.http.HttpStatus;

/**
 * General error DTO
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-29
 */
public class ErrorDto {
    private final int status;
    private final String error;
    private final String message;

    public ErrorDto(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
