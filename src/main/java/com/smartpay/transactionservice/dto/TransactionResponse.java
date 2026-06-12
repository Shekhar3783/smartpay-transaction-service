package com.smartpay.transactionservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class TransactionResponse {
    private UUID id;

    private String userId;

    private BigDecimal amount;

    private String merchantName;

    private OffsetDateTime createdAt;
}
