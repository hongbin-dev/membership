package me.hongbin.point.domain;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.hongbin.point.dto.PointReadResponseItem;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    @Query(
        "select new me.hongbin.point.dto.PointReadResponseItem(ph.createdAt, ph.transactionType, p.category, partner.name, ph.amount) "
            + "from Point p "
            + "left join PointHistory ph on p.id = ph.pointId "
            + "join Partner partner on partner.id = ph.partnerId "
            + "where p.userId = :userId "
            + "and p.createdAt >= :startDateTime "
            + "and p.createdAt <= :endDateTime "
            + "order by ph.id desc"
    )
    Page<PointReadResponseItem> findBy(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime,
        Pageable pageable);
}
