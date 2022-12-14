package com.tcoshop.controller.client;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tcoshop.entity.Order;
import com.tcoshop.entity.OrderDetail;
import com.tcoshop.entity.Product;
import com.tcoshop.entity.User;
import com.tcoshop.service.OrderDetailService;
import com.tcoshop.service.OrderService;
import com.tcoshop.service.ProductService;
import com.tcoshop.service.UserService;

@Controller
public class CartController {
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    ProductService productService;

    @RequestMapping("/cart")
    public String cart() {
        return "tco-client/cart/cart";
    }

    @RequestMapping("/vnpay_payment")
    public String vnpayPayment(HttpServletRequest req, HttpServletResponse resp,
            Authentication authentication,
            @RequestParam("phone") String phoneNumber,
            @RequestParam("address") String address,
            @RequestParam("your-comment") Optional<String> description,
            @RequestParam("pId") String[] productId,
            @RequestParam("pQuantity") String[] productQuantity) throws IOException {

        Order order = new Order();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        order.setUser(user);
        order.setPhoneNumber(phoneNumber);
        order.setAddress(address);
        if (description.isPresent()) {
            order.setDescription(description.get());
        }
        order.setShippingCost(0.0);
        order.setCreateDate(new Date());
        order.setIsPaid(1);
        Date dt = DateUtils.addDays(new Date(), 7);
        order.setExpectedDate(dt);
        order.setStatus("ChoXacNhan");
        Order createOrder = orderService.create(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (int i = 0; i < productId.length; i++) {
            OrderDetail orderDetail = new OrderDetail();
            Integer id = Integer.parseInt(productId[i]);
            Product product = productService.findById(id);
            orderDetail.setProduct(product);
            Double price = product.getPrice() * (100 - product.getDiscount()) / 100;
            orderDetail.setPrice(price);
            Integer quantity = Integer.parseInt(productQuantity[i]);
            orderDetail.setOrder(createOrder);
            orderDetail.setQuantity(quantity);
            orderDetails.add(orderDetail);
        }
        orderDetailService.saveAll(orderDetails);

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Thanh toán vnpay test";
        String orderType = "250000";
        Integer orderId = createOrder.getId();
        String oId = orderId + "";
        String vnp_TxnRef = oId;
        String vnp_IpAddr = Config.getIpAddress(req);
        String vnp_TmnCode = Config.vnp_TmnCode;
        List<OrderDetail> orderDetailsInDB = orderDetailService.findByOrderId(orderId);
        double price = 0.0;
        for (OrderDetail orderDetail : orderDetailsInDB) {
            price += (orderDetail.getPrice() * orderDetail.getQuantity());
        }
        price += order.getShippingCost();
        int amount = (int) price * 100;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = req.getParameter("bankcode");
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String email = user.getEmail();
        vnp_Params.put("vnp_Bill_Email", email);
        String fullName = "";
        if (user.getFullname() == null) {
            fullName = user.getUsername();
        } else {
            fullName = user.getFullname();
        }
        if (fullName != null && !fullName.isEmpty()) {
            int idx = fullName.indexOf(' ');
            if (idx != -1) {
                String firstName = fullName.substring(0, idx);
                String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
                vnp_Params.put("vnp_Bill_FirstName", firstName);
                vnp_Params.put("vnp_Bill_LastName", lastName);
            }
        }
        String addressVNPAY = createOrder.getAddress();
        vnp_Params.put("vnp_Bill_Address", addressVNPAY);
        // Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(job));
        return "redirect:" + paymentUrl;
    }

}
