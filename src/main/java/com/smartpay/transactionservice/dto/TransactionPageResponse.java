package com.smartpay.transactionservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionPageResponse {

    private List<TransactionResponse> transactions;

    private int currentPage;

    private int totalPages;

    private long totalElements;
}
