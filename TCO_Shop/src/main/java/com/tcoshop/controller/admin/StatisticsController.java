package com.tcoshop.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class StatisticsController {
	@RequestMapping("/tco-admin/statistics/ordersSold")
    public String getOderSold() {    
        return "tco-admin/statistics/ordersSold.html";
    }
    
    @RequestMapping("/tco-admin/statistics/orderStatus")
    public String getOderStatus() {
        return "tco-admin/statistics/orderStatus.html";
    }

    @RequestMapping("/tco-admin/statistics/turnover")
    public String getTurnover() {
        return "tco-admin/statistics/turnover.html";
    }

    @RequestMapping("/tco-admin/statistics/usersRegistered")
    public String getUsersRegistered() {
        return "tco-admin/statistics/usersRegistered.html";
    }

    }

