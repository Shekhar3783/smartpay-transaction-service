package com.smartpay.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse implements Serializable {
    private UUID id;

    private String userId;

    private BigDecimal amount;

    private String merchantName;

    private OffsetDateTime createdAt;
}
