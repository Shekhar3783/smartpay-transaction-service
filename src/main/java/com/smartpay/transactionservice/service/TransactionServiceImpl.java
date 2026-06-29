package com.smartpay.transactionservice.service;

import com.smartpay.transactionservice.dto.TransactionPageResponse;
import com.smartpay.transactionservice.dto.TransactionRequest;
import com.smartpay.transactionservice.dto.TransactionResponse;
import com.smartpay.transactionservice.entity.Transaction;
import com.smartpay.transactionservice.event.TransactionCreatedEvent;
import com.smartpay.transactionservice.exception.TransactionNotFoundException;
import com.smartpay.transactionservice.producer.TransactionEventProducer;
import com.smartpay.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    private final TransactionEventProducer transactionEventProducer;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionEventProducer transactionEventProducer) {
        this.transactionRepository = transactionRepository;
        this.transactionEventProducer = transactionEventProducer;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {

        log.info("Received transaction creation request for user {} amount {}",
                request.getUserId(),
                request.getAmount());


        Transaction transaction=Transaction.builder()
                .id(UUID.randomUUID())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .merchantName(request.getMerchantName())
                .build();
        log.info("Saving transaction into database");

        Transaction savedTransaction=transactionRepository.save(transaction);
        log.info("Transaction saved successfully with id {}",
                savedTransaction.getId());


        TransactionCreatedEvent event=new TransactionCreatedEvent(savedTransaction.
                getId().toString(), savedTransaction.getUserId(), savedTransaction.getAmount(),savedTransaction.getMerchantName());

        transactionEventProducer.publishTransactionCreatedEvent(event);

       log.info("record sent to kafka topic");

        return TransactionResponse.builder()
                .id(savedTransaction.getId())
                .userId(savedTransaction.getUserId())
                .amount(savedTransaction.getAmount())
                .merchantName(savedTransaction.getMerchantName())
                .createdAt(savedTransaction.getCreatedAt())
                .build();


    }

    @Override
    @Cacheable(value = "transactions", key = "#id")
    public TransactionResponse getTransaction(UUID id) {

        log.info("Fetching transaction with id {}", id);

        Transaction transaction=transactionRepository.findById(id)
                .orElseThrow(()->
                        new TransactionNotFoundException("Transaction not found with id: "+id));

        log.info("Transaction found successfully {}", id);

        return TransactionResponse.builder()
                .id(transaction.getId())
                .userId(transaction.getUserId())
                .amount(transaction.getAmount())
                .merchantName(transaction.getMerchantName())
                .createdAt(transaction.getCreatedAt())
                .build();

    }

 

    @Override
    public TransactionPageResponse getTransactions(String userId, int page, int size, String sortBy, String direction) {
        log.info(
                "Fetching transactions for user {} page {} size {} sortBy {} direction {}",
                userId,
                page,
                size,
                sortBy,
                direction
        );


        Sort sort
                =direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();

        /*
         * Creates:
         *
         * page=0
         * size=5
         * sort=createdAt desc
         */
        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        //If userID present execute filtered query

        Page<Transaction> transactionPage;

        if (userId != null && !userId.isBlank()) {

            transactionPage =
                    transactionRepository.findByUserId(
                            userId,
                            pageable);

        } else {
            /*
             * Fetch all users
             */
            transactionPage =
                    transactionRepository.findAll(
                            pageable);
        }
        /*
         * Convert Entity -> DTO
         */
        List<TransactionResponse> responses =
                transactionPage.getContent()
                        .stream()
                        .map(transaction ->
                                TransactionResponse.builder()
                                        .id(transaction.getId())
                                        .userId(transaction.getUserId())
                                        .amount(transaction.getAmount())
                                        .merchantName(transaction.getMerchantName())
                                        .createdAt(transaction.getCreatedAt())
                                        .build())
                        .toList();

        return TransactionPageResponse.builder()
                .transactions(responses)
                .currentPage(
                        transactionPage.getNumber())
                .totalPages(
                        transactionPage.getTotalPages())
                .totalElements(
                        transactionPage.getTotalElements())
                .build();
        }

}
