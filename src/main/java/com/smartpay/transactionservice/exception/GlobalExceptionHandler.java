package com.smartpay.transactionservice.exception;

import com.smartpay.transactionservice.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTransactionNotFound(TransactionNotFoundException exception){

        log.error("TransactionNotFoundException occurred : {}",
                exception.getMessage());

       return ErrorResponse.builder()
               .timestamp(OffsetDateTime.now())
               .status(HttpStatus.NOT_FOUND.value())
               .message(exception.getMessage())
               .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handelValidationException(MethodArgumentNotValidException exception){

        log.error("Validation Exception");

        Map<String,String> errors=new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error->
                        errors.put(error.getField(),
                                error.getDefaultMessage()));

        return ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation Failed")
                .errors(errors)
                .build();

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(
            Exception ex) {

        log.error("Unexpected exception occurred", ex);

        return ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal server error")
                .build();

    }
}
