package com.smartpay.transactionservice.service;

import com.smartpay.transactionservice.dto.TransactionPageResponse;
import com.smartpay.transactionservice.dto.TransactionRequest;
import com.smartpay.transactionservice.dto.TransactionResponse;
import com.smartpay.transactionservice.entity.Transaction;
import com.smartpay.transactionservice.exception.TransactionNotFoundException;
import com.smartpay.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Enables Mockito without this @Mok @InjectMocks will not work.
@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    //Fake Repository No PostgreSQL  Connection.
   @Mock
    private TransactionRepository transactionRepository;

    //Service Under Test. Mockito injects mock repository here.
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldCreateTransaction(){

        //Arrange the request
        TransactionRequest request =
                new TransactionRequest();

        request.setUserId("USER001");
        request.setAmount(
                BigDecimal.valueOf(5000));

        request.setMerchantName("Amazon");

        Transaction transaction =
                Transaction.builder()
                        .id(UUID.randomUUID())
                        .userId("USER001")
                        .amount(BigDecimal.valueOf(5000))
                        .merchantName("Amazon")
                        .createdAt(OffsetDateTime.now())
                        .build();

        //when repository.save() called return transaction
        when(transactionRepository.save(any()))
                .thenReturn(transaction);

        //Act
        TransactionResponse response=transactionService.createTransaction(request);

        //ASSERT

        assertNotNull(response);

        assertEquals("USER001",response.getUserId());
        assertEquals("Amazon",response.getMerchantName());

        verify(transactionRepository,times(1)).save(any());

    }

    @Test
    void shouldReturnTransactionById(){

        UUID id=UUID.randomUUID();

        Transaction transaction =
                Transaction.builder()
                        .id(UUID.randomUUID())
                        .userId("USER001")
                        .amount(BigDecimal.valueOf(5000))
                        .merchantName("Amazon")
                        .createdAt(
                                OffsetDateTime.now())
                        .build();

        //Fake DB Response
        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(transaction));

        TransactionResponse response =transactionService.getTransaction(id);

        assertNotNull(response);

        assertEquals(id, response.getId());

        assertEquals("USER001", response.getUserId());

        verify(transactionRepository).findById(id);
    }
    @Test
    void shouldThrowTransactionNotFoundException() {

        UUID id = UUID.randomUUID();

        //simulate record not found.
        when(transactionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                TransactionNotFoundException.class,
                () -> transactionService.getTransaction(id)
        );

        verify(transactionRepository).findById(id);
    }

    @Test
    void shouldReturnPaginatedTransactions(){
        Transaction transaction = Transaction.builder()
                        .id(UUID.randomUUID())
                        .userId("USER001")
                        .amount(BigDecimal.valueOf(500))
                        .merchantName("Zomato")
                        .createdAt(
                                OffsetDateTime.now())
                        .build();

        List<Transaction> transactions = List.of(transaction);

        /*
         * Fake page returned
         * by repository
         */
        Page<Transaction> page =
                new PageImpl<>(transactions);

        when(transactionRepository.findByUserId(
                eq("USER001"),
                any(Pageable.class)))
                .thenReturn(page);

        TransactionPageResponse response =
                transactionService
                        .getTransactions(
                                "USER001",
                                0,
                                5,
                                "createdAt",
                                "desc");

        assertNotNull(response);

        assertEquals(
                1,
                response.getTransactions().size());

        assertEquals(
                "USER001",
                response.getTransactions()
                        .get(0)
                        .getUserId());

        verify(transactionRepository)
                .findByUserId(
                        eq("USER001"),
                        any(Pageable.class));

    }
}
