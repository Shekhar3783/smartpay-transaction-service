package com.smartpay.transactionservice.service;

import com.smartpay.transactionservice.dto.TransactionPageResponse;
import com.smartpay.transactionservice.dto.TransactionRequest;
import com.smartpay.transactionservice.dto.TransactionResponse;
import com.smartpay.transactionservice.entity.Transaction;
import com.smartpay.transactionservice.exception.TransactionNotFoundException;
import com.smartpay.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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

        return TransactionResponse.builder()
                .id(savedTransaction.getId())
                .userId(savedTransaction.getUserId())
                .amount(savedTransaction.getAmount())
                .merchantName(savedTransaction.getMerchantName())
                .createdAt(savedTransaction.getCreatedAt())
                .build();


    }

    @Override
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
    public TransactionPageResponse getAllTransactions(int page, int size) {

        /*
         * Create Pageable object
         *
         * page = 0
         * size = 5
         */
        Pageable pageable= PageRequest.of(page, size);

        log.info("fetching transactions page {} size {} ",page,size);

        /*
         * Hibernate executes:
         * select *  from transactions limit size offset page*size
         */

        Page<Transaction> transactionPage=transactionRepository.findAll(pageable);

        //Convert Entity -> Dto Response*/

        List<TransactionResponse>responses=
                transactionPage.getContent()
                        .stream()
                        .map(transaction->
                                TransactionResponse.builder()
                                .id(transaction.getId())
                                        .userId(transaction.getUserId())
                                        .amount(transaction.getAmount())
                                        .merchantName(transaction.getMerchantName())
                                        .createdAt(transaction.getCreatedAt())
                                        .build())
                        .toList();

        log.info("Found {} transactions ",+responses.size());

        return TransactionPageResponse.builder()
                .transactions(responses)
                .currentPage(transactionPage.getNumber())
                .totalPages(transactionPage.getTotalPages())
                .totalElements(transactionPage.getTotalElements())
                .build();
    }
}
