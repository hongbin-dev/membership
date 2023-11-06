package me.hongbin.partner.dto;

import me.hongbin.partner.domain.Category;

public record PartnerCreateResponse(Long id, String name, Category category) {
}
