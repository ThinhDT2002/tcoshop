package com.tcoshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.Transaction;
import com.tcoshop.repository.TransactionRepository;
import com.tcoshop.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	TransactionRepository transactionRepository;

	@Override
	public Transaction create(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}

	@Override
	public List<Transaction> findByUsername(String username) {
		return transactionRepository.findByUser(username);
	}

}
