package com.tcoshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tcoshop.entity.Transaction;
import com.tcoshop.service.TransactionService;

@Controller
public class TransactionManagementController {
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping("/tco-admin/transaction")
	public String getTransaction(Model model) {
		List<Transaction> list = transactionService.findAll();
		model.addAttribute("trans",list);
		return "tco-admin/transaction/transaction-list.html";
	}
}
