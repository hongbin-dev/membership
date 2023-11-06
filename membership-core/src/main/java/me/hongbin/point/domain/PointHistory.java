package me.hongbin.point.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import me.hongbin.generic.type.BaseTimeEntity;

@Entity
public class PointHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Long amount;

    private Long partnerId;

    private Long pointId;

    protected PointHistory() {

    }

    public PointHistory(Long partnerId, Long amount,
        TransactionType transactionType, Long pointId) {
        this.partnerId = partnerId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.pointId = pointId;
    }

    public Long getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public Long getPointId() {
        return pointId;
    }
}
