package me.hongbin.barcode.service;

import java.util.Optional;

import me.hongbin.partner.domain.Category;
import me.hongbin.point.domain.Point;
import me.hongbin.point.domain.PointRepository;

public class InMemoryPointRepository extends FakeRepository<Point, Long> implements PointRepository {

    @Override
    public Optional<Point> findByWithLock(Long userId, Category category) {
        return map.values().stream()
            .filter(it -> it.getCategory().equals(category))
            .filter(it -> it.getUserId().equals(userId))
            .findAny();
    }
}
