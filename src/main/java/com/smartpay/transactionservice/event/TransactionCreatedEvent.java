package com.smartpay.transactionservice.event;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
public class TransactionCreatedEvent {

    private String transactionId;

    private String userId;

    private BigDecimal amount;

    private String merchantName;

    private OffsetDateTime transactionTime;

    public TransactionCreatedEvent() {
    }

    public TransactionCreatedEvent(String transactionId, String userId, BigDecimal amount, String merchantName, OffsetDateTime transactionTime) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.merchantName = merchantName;
        this.transactionTime = transactionTime;
    }

}
