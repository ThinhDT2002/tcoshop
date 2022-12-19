package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.Transaction;

public interface TransactionService {
    Transaction create(Transaction transaction);
    
    List<Transaction> findAll();
    
    List<Transaction> findByUsername(String username);
}
