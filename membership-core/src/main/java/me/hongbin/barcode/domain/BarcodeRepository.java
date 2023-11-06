package me.hongbin.barcode.domain;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface BarcodeRepository {
    Optional<String> findByUserId(Long userId);

    void save(Long userId, String barcode);

    Optional<Long> findByBarcode(String barcode);

    default boolean existBarcode(String barcode) {
        return findByBarcode(barcode).isPresent();
    }
}
