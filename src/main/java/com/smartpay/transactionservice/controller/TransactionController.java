package com.smartpay.transactionservice.controller;

import com.smartpay.transactionservice.dto.TransactionPageResponse;
import com.smartpay.transactionservice.dto.TransactionRequest;
import com.smartpay.transactionservice.dto.TransactionResponse;
import com.smartpay.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@Valid @RequestBody TransactionRequest request){
        return transactionService.createTransaction(request);
    }

    @GetMapping("/{id}")
    public TransactionResponse getTransaction(@PathVariable UUID id){
        return transactionService.getTransaction(id);
    }



    @GetMapping
    public TransactionPageResponse getTransactions(

            @RequestParam(required = false)
            String userId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "createdAt")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String direction) {

        return transactionService.getTransactions(userId, page, size, sortBy, direction);
    }
}
