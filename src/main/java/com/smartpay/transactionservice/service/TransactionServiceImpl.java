package com.smartpay.transactionservice.service;

import com.smartpay.transactionservice.dto.TransactionRequest;
import com.smartpay.transactionservice.dto.TransactionResponse;
import com.smartpay.transactionservice.entity.Transaction;
import com.smartpay.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {

        Transaction transaction=Transaction.builder()
                .id(UUID.randomUUID())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .merchantName(request.getMerchantName())
                .build();

        Transaction saved=transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .id(saved.getId())
                .userId(saved.getUserId())
                .amount(saved.getAmount())
                .merchantName(saved.getMerchantName())
                .createdAt(saved.getCreatedAt())
                .build();


    }
}
