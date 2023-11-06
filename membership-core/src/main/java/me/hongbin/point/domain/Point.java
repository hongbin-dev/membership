package me.hongbin.point.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import me.hongbin.generic.exception.PointException;
import me.hongbin.generic.type.BaseTimeEntity;
import me.hongbin.partner.domain.Category;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "category"})})
public class Point extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Long userId;

    protected Point() {
    }

    public Point(Long amount, Category category, Long userId) {
        this.amount = amount;
        this.category = category;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Long getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public Long getUserId() {
        return userId;
    }

    public void subtract(Long amount) {
        if (amount <= 0) {
            throw new PointException("[%d] 사용할 포인트를 1원이상 입력해주세요".formatted(amount));
        }

        if (!canUsable(amount)) {
            throw new PointException("포인트 사용량을 초과했습니다. total=%d, request=%d".formatted(this.amount, amount));
        }
        this.amount -= amount;
    }

    private boolean canUsable(Long amount) {
        return this.amount >= amount;
    }

    public void plus(Long amount) {
        if (amount <= 0) {
            throw new PointException("[%d] 적립할 포인트를 1원이상 입력해주세요".formatted(amount));
        }
        this.amount += amount;
    }
}
