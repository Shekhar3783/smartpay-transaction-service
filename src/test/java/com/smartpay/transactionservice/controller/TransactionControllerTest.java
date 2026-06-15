package com.smartpay.transactionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartpay.transactionservice.dto.TransactionResponse;
import com.smartpay.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    //Simulates HTTP requests No Tomcat server starts.
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldReturnTransactionById() throws Exception {

        UUID id = UUID.randomUUID();

        TransactionResponse response =
                TransactionResponse.builder()
                        .id(id)
                        .userId("USER001")
                        .amount(BigDecimal.valueOf(1000))
                        .merchantName("Amazon")
                        .createdAt(OffsetDateTime.now())
                        .build();


         // Mock service response.
        when(transactionService
                .getTransaction(id))
                .thenReturn(response);


        // Simulate:GET /api/transactions/{id}

        mockMvc.perform(get("/api/transactions/{id}" ,id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("USER001"))
                .andExpect(jsonPath("$.merchantName").value("Amazon"));
    }
}
