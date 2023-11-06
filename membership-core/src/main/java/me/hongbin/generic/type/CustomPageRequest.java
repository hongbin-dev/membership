package me.hongbin.generic.type;

import java.util.Objects;

import org.springframework.data.domain.PageRequest;

public class CustomPageRequest {
    private Integer page;
    private Integer size;

    public CustomPageRequest() {
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public PageRequest toPage() {
        return PageRequest.of(
            Objects.requireNonNullElse(page, 0),
            Objects.requireNonNullElse(size, 20)
        );
    }
}
