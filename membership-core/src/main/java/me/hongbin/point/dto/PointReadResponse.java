package me.hongbin.point.dto;

import java.util.List;

import me.hongbin.generic.type.PageResponse;

public record PointReadResponse(
    PageResponse page,
    List<PointReadResponseItem> items
) {
}