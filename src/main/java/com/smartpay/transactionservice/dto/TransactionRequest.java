package com.smartpay.transactionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Merchant name is required")
    private String merchantName;

    public @NotBlank(message = "User ID is required") String getUserId() {
        return userId;
    }

    public void setUserId(@NotBlank(message = "User ID is required") String userId) {
        this.userId = userId;
    }

    public @NotNull(message = "Amount is required") @Positive(message = "Amount must be greater than zero") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "Amount is required") @Positive(message = "Amount must be greater than zero") BigDecimal amount) {
        this.amount = amount;
    }

    public @NotBlank(message = "Merchant name is required") String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(@NotBlank(message = "Merchant name is required") String merchantName) {
        this.merchantName = merchantName;
    }
}
