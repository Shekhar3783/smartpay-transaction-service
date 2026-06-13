package com.smartpay.transactionservice.service;

import com.smartpay.transactionservice.dto.TransactionPageResponse;
import com.smartpay.transactionservice.dto.TransactionRequest;
import com.smartpay.transactionservice.dto.TransactionResponse;

import java.util.UUID;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);

    TransactionResponse getTransaction(UUID id);

    TransactionPageResponse getAllTransactions(int page,int size);
}
