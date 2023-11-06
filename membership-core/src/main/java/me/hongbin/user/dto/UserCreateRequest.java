package me.hongbin.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(@NotNull String name) {
}
