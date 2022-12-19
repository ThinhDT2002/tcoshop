package com.tcoshop.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.tcoshop.entity.Order;
import com.tcoshop.entity.OrderDetail;
import com.tcoshop.entity.User;
import com.tcoshop.service.OrderService;

@Controller
public class OrderController {
    private RestTemplate restTemplate = new RestTemplate();
    
	@Autowired
	OrderService orderService;
	
	@GetMapping("/checkout")
	public String checkout(Authentication authentication, Model model) {
	    String currentUserUsername = authentication.getName();
	    String url = "http://localhost:8080/api/user/" + currentUserUsername;
	    ResponseEntity<User> entity = restTemplate.getForEntity(url, User.class);
	    User currentUser = entity.getBody();
	    model.addAttribute("currentUser", currentUser);	 
		return "tco-client/cart/checkout";
	}
		
	@RequestMapping("/order/history")
	public String detail(Model model) {
		return "tco-client/order/order-history";
	}
	
	@RequestMapping("/order/transaction")
	public String transaction(Model model) {
		return "tco-client/order/order-transaction";
	}
	
	@RequestMapping("/order/track/{id}")
	public String track(Model model, @PathVariable("id") Integer id) {
		Order order = orderService.findById(id);
		model.addAttribute("order", order);
		List<OrderDetail> ordersDetail = order.getOrderDetails();
		double tongTien = 0;
		for(OrderDetail orderDetail : ordersDetail) {
			double giaSanPham = (orderDetail.getProduct().getPrice() * ((100.0 - orderDetail.getProduct().getDiscount()) / 100)) * orderDetail.getQuantity();
			tongTien += giaSanPham;
		}	
		tongTien += order.getShippingCost();
		model.addAttribute("sum", tongTien);
		return "tco-client/order/track-order";
	}
}
