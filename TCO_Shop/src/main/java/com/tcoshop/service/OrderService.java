package com.tcoshop.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.tcoshop.entity.Order;

public interface OrderService {
	Order create(JsonNode orderData);
	
	Order update(Order order);

	Order findById(Integer id);

	List<Order> findByUsername(String username);

	List<Order> findAll();
	
	void delete(Integer id);
	
	Order create(Order order);
	
	Order findByTransacationId(Integer transactionId);
}
