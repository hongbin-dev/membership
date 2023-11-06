package me.hongbin.partner.dto;

import jakarta.validation.constraints.NotNull;
import me.hongbin.partner.domain.Category;

public record PartnerCreateRequest(@NotNull String name, @NotNull Category category) {
}
