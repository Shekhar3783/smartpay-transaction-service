package com.smartpay.transactionservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jshell.Snippet;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;


@Table(name="transactions")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

    @Id
    private UUID id;

    private String userId;

    private BigDecimal amount;

    private String merchantName;

    @CreationTimestamp
    private OffsetDateTime createdAt;



}
