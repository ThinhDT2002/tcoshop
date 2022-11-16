package com.tcoshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderManagementController {
    @RequestMapping("/tco-admin/order")
    public String getOrder() {
        return "tco-admin/order/order-list.html";
    }
}