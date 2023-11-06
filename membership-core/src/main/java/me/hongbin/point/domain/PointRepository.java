package me.hongbin.point.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import me.hongbin.partner.domain.Category;

public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("select p from Point p where p.userId = :userId and p.category = :category")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Point> findByWithLock(Long userId, Category category);
}
