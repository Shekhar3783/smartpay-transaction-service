package com.smartpay.transactionservice.repository;

import com.smartpay.transactionservice.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    //Spring automatically generates SELECT * from transactions where user_id=?
    Page<Transaction> findByUserId(String userId, Pageable pageable);
}
