package me.hongbin.point.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public record PointReadRequest(
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @NotNull LocalDateTime startDateTime,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @NotNull LocalDateTime endDateTime,
    @NotNull(message = "바코드는 필수입니다.") String barcode
) {

    @Override
    public LocalDateTime startDateTime() {
        return startDateTime;
    }

    @Override
    public LocalDateTime endDateTime() {
        return endDateTime;
    }
}
