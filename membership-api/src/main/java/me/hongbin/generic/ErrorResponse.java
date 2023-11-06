package me.hongbin.generic;

import java.util.List;

public record ErrorResponse(
    String message,
    List<ErrorDto> errors
) {

    public ErrorResponse(String message) {
        this(message, null);
    }

    public record ErrorDto(
        String filed,
        Object value,
        String reason
    ) {
    }
}
