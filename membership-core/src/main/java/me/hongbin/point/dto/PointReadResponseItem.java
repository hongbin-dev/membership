package me.hongbin.point.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import me.hongbin.partner.domain.Category;
import me.hongbin.point.domain.TransactionType;

public class PointReadResponseItem {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime approvedAt;
    private TransactionType type;
    private Category category;
    private String partnerName;
    private Long amount;

    public PointReadResponseItem() {
    }

    public PointReadResponseItem(LocalDateTime approvedAt, TransactionType type, Category category, String partnerName,
        Long amount) {
        this.approvedAt = approvedAt;
        this.type = type;
        this.category = category;
        this.partnerName = partnerName;
        this.amount = amount;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public TransactionType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
