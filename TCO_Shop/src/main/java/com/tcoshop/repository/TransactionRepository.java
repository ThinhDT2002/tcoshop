package com.tcoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
    
}
