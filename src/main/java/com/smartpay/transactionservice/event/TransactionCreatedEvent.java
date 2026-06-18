package com.smartpay.transactionservice.event;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionCreatedEvent {

    private String transactionId;

    private String userId;

    private BigDecimal amount;

    private String merchantName;

    public TransactionCreatedEvent() {
    }

    public TransactionCreatedEvent(String transactionId, String userId, BigDecimal amount, String merchantName) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.merchantName = merchantName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
