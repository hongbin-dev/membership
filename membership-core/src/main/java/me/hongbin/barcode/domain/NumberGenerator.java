package me.hongbin.barcode.domain;

import org.springframework.stereotype.Component;

@Component
public interface NumberGenerator {
    Long generate();
}
