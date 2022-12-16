package com.tcoshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcoshop.service.OrderService;
import com.tcoshop.entity.Order;
import com.tcoshop.entity.OrderDetail;
import com.tcoshop.entity.Product;
import com.tcoshop.repository.OrderDetailRepository;
import com.tcoshop.repository.OrderRepository;
import com.tcoshop.repository.ProductRepository;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderRepository orderDAO;
	
	@Autowired
	OrderDetailRepository orderDetailDAO;
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public Order create(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();
		
		Order order = mapper.convertValue(orderData, Order.class);
		orderDAO.save(order);
		
		TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
		};
		List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"),type)
				.stream().peek(d -> d.setOrder(order)).collect(Collectors.toList());
		for(OrderDetail orderDetail : details) {
		    int productId = orderDetail.getProduct().getId();
		    Product product = productRepository.findById(productId).get();
		    int remainStock = product.getStock() - orderDetail.getQuantity();
		    product.setStock(remainStock);
		    productRepository.save(product);
		}
		orderDetailDAO.saveAll(details);
		return order;
	}

	@Override
	public Order findById(Integer id) {
		return orderDAO.findById(id).get();
	}

	@Override
	public List<Order> findByUsername(String username) {
		return orderDAO.findByUsername(username);
	}

	@Override
	public List<Order> findAll() {
		return orderDAO.findAll();
	}

    @Override
    public Order update(Order order) {
        return orderDAO.save(order);
    }

	@Override
	public void delete(Integer id) {
		orderDAO.deleteById(id);
	}
	
	@Override
	public Order create(Order order) {
	    return orderDAO.save(order);
	}

    @Override
    public Order findByTransacationId(Integer transactionId) {
        return orderDAO.findByTransactionId(transactionId);
    }
}
