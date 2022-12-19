package com.tcoshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.Transaction;
import com.tcoshop.service.TransactionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/transaction")
public class TransactionAPI {
	@Autowired
	TransactionService transactionService;
	
	@GetMapping()
	public List<Transaction> getAll(){
		return transactionService.findAll();
	}
	
	@GetMapping("{username}")
	public List<Transaction> findByUsername(@PathVariable("username") String username){
		return transactionService.findByUsername(username);
	}
}
