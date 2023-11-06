package me.hongbin.barcode.dto;

import jakarta.validation.constraints.NotNull;

public record BarcodeCreateRequest(@NotNull(message = "유저ID는 필수입니다.") Long userId) {
}
