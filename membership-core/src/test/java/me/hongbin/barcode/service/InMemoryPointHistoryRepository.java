package me.hongbin.barcode.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import me.hongbin.point.domain.PointHistory;
import me.hongbin.point.domain.PointHistoryRepository;
import me.hongbin.point.dto.PointReadResponseItem;

public class InMemoryPointHistoryRepository extends FakeRepository<PointHistory, Long> implements
    PointHistoryRepository {

    @Override
    public Page<PointReadResponseItem> findBy(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime,
        Pageable pageable) {
        throw new UnsupportedOperationException();
    }
}
