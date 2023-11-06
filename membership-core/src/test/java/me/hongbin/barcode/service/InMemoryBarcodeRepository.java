package me.hongbin.barcode.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import me.hongbin.barcode.domain.BarcodeRepository;

public class InMemoryBarcodeRepository implements BarcodeRepository {

    private final Map<Long, String> values = new HashMap<>();

    @Override
    public Optional<String> findByUserId(Long userId) {
        return Optional.ofNullable(values.get(userId));
    }

    @Override
    public void save(Long userId, String barcode) {
        values.put(userId, barcode);
    }

    @Override
    public Optional<Long> findByBarcode(String barcode) {
        return values.entrySet()
            .stream()
            .filter(it -> Objects.equals(it.getValue(), barcode))
            .map(Map.Entry::getKey)
            .findAny();
    }
}
