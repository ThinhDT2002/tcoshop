package com.tcoshop.controller.client;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tcoshop.entity.Order;
import com.tcoshop.entity.OrderDetail;
import com.tcoshop.entity.Product;
import com.tcoshop.entity.Transaction;
import com.tcoshop.entity.User;
import com.tcoshop.service.OrderDetailService;
import com.tcoshop.service.OrderService;
import com.tcoshop.service.ProductService;
import com.tcoshop.service.TransactionService;
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
    @Autowired
    TransactionService transactionService;

    @RequestMapping("/cart")
    public String cart() {
        return "tco-client/cart/cart";
    }

    @RequestMapping("/vnpay_payment")
    public String vnpayPayment(HttpServletRequest req, HttpServletResponse resp,
            Authentication authentication,
            @RequestParam("phone") Optional<String> phoneNumber,
            @RequestParam("address") Optional<String> address,
            @RequestParam("your-comment") Optional<String> description,
            @RequestParam("pId") String[] productId,
            @RequestParam("pQuantity") String[] productQuantity,
            @RequestParam("orderShippingCost") Optional<String> orderShippingCost,
            HttpSession session,
            Model model) throws IOException {
        if (!phoneNumber.isPresent() || !address.isPresent()) {
            model.addAttribute("errorCheckoutMessage", "Hãy nhập địa chỉ và số điện thoại!");
            return "tco-client/cart/checkout";
        }
        Order order = new Order();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        order.setUser(user);
        order.setPhoneNumber(phoneNumber.get());
        order.setAddress(address.get());
        if (description.isPresent()) {
            order.setDescription(description.get());
        }
        if (orderShippingCost.isPresent()) {
            double shippingCost = Double.parseDouble(orderShippingCost.get());
            order.setShippingCost(shippingCost);
        } else {
            order.setShippingCost(0.0);
        }
        Date createDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String createTime = simpleDateFormat.format(calendar.getTime());
        order.setCreateDate(createDate);
        order.setOrderTimeDetail(createTime);
        order.setIsPaid(1);
        Date dt = DateUtils.addDays(new Date(), 7);
        order.setExpectedDate(dt);
        order.setStatus("ChoXacNhan");
        Order createOrder = orderService.create(order);
        session.setAttribute("orderIdPay", createOrder.getId());
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
        String vnp_OrderInfo = "Thanh toan hoa don vnpay";
        String orderType = "250000";
        Integer orderId = createOrder.getId();
        String oId = orderId + "";
        // String vnp_TxnRef = oId;
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(req);
        String vnp_TmnCode = Config.vnp_TmnCode;
        List<OrderDetail> orderDetailsInDB = orderDetailService.findByOrderId(orderId);
        double price = 0.0;
        for (OrderDetail orderDetail : orderDetailsInDB) {
            price += (orderDetail.getPrice() * (orderDetail.getQuantity() + 0.0));
        }
        price += order.getShippingCost();
        int amount = (int) (price * 100.0);
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

    @RequestMapping("/afterCheckout")
    public String afterCheckout(
            @RequestParam("vnp_Amount") String amount,
            @RequestParam("vnp_BankCode") String bankCode,
            @RequestParam("vnp_BankTranNo") Optional<String> bankTranNo,
            @RequestParam("vnp_CardType") String cardType,
            @RequestParam("vnp_OrderInfo") String orderInfo,
            @RequestParam("vnp_ResponseCode") String responCode,
            @RequestParam("vnp_TransactionNo") String transactionNo,
            @RequestParam("vnp_TransactionStatus") String transactionStatus,
            HttpSession session,
            Authentication authentication,
            Model model) {
        try {
            Date payDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            String payTime = simpleDateFormat.format(calendar.getTime());
            Integer orderId = (Integer) session.getAttribute("orderIdPay");
            Order order = orderService.findById(orderId);
            Transaction transaction = new Transaction();
            Double transactionAmount = Double.parseDouble(amount) / 100.0;
            transaction.setAmount(transactionAmount);
            transaction.setBankCode(bankCode);
            if (bankTranNo.isPresent()) {
                transaction.setBankTranNo(bankTranNo.get());
            } else {
                transaction.setBankTranNo("Cancel payment");
            }
            transaction.setCardType(cardType);
            transaction.setTransactionInfo(orderInfo);
            transaction.setPayDate(payDate);
            transaction.setPayTime(payTime);
            switch (responCode) {
                case "00": {
                    transaction.setTransactionStatus("Giao dịch thành công");
                    break;
                }
                case "07": {
                    transaction.setTransactionStatus(
                            "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường).");
                    break;
                }
                case "09": {
                    transaction.setTransactionStatus(
                            "Thẻ/Tài khoản của quý khách chưa đăng ký dịch vụ InternetBanking tại ngân hàng.");
                    break;
                }
                case "10": {
                    transaction.setTransactionStatus("Xác thực thông tin thẻ/tài khoản không đúng quá 3 lần");
                    break;
                }
                case "11": {
                    transaction.setTransactionStatus(
                            "Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch.");
                    break;
                }
                case "12": {
                    transaction.setTransactionStatus("Thẻ/Tài khoản của khách hàng bị khóa.");
                    break;
                }
                case "13": {
                    transaction.setTransactionStatus("Mã OTP không hợp lệ. Vui lòng thực hiện lại giao dịch");
                    break;
                }
                case "24": {
                    transaction.setTransactionStatus("Giao dịch đã bị huỷ");
                    break;
                }
                case "51": {
                    transaction.setTransactionStatus("Tài khoản của quý khách không đủ số dư để thực hiện giao dịch.");
                    break;
                }
                case "65": {
                    transaction
                            .setTransactionStatus("Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày.");
                    break;
                }
                case "75": {
                    transaction.setTransactionStatus("Ngân hàng thanh toán đang bảo trì.");
                    break;
                }
                case "79": {
                    transaction.setTransactionStatus(
                            "Quý khách nhập sai mật khẩu thanh toán quá số lần quy định. Vui lòng thử lại");
                    break;
                }
                case "99": {
                    transaction.setTransactionStatus("Lỗi không xác định");
                    break;
                }
                default: {
                    transaction.setTransactionStatus("Lỗi không xác định");
                    break;
                }
            }
            ;
            if (responCode.equals("00")) {
                transaction.setPayStatus("Giao dịch thành công");
                transaction.setOrder(order);
            } else {
                transaction.setPayStatus("Giao dịch bị huỷ");
                transaction.setOrder(null);
            }
            transaction.setTransactionNo(transactionNo);
            Transaction returnTransaction = transactionService.create(transaction);
            model.addAttribute("transaction", returnTransaction);        
            if (returnTransaction.getPayStatus().equals("Giao dịch thành công")) { 
            	Order returnOrder = orderService.findByTransacationId(returnTransaction.getId());
                returnOrder.setIsPaid(2);
                orderService.update(returnOrder);
                model.addAttribute("order", returnOrder);
            } else {
            	orderService.delete(order.getId());
            	System.out.println("Delete thanh cong");
            }
            return "tco-client/cart/after-checkout";
        } catch (Exception e) {
            e.printStackTrace();
            return "tco-client/other/error-page";
        }
    }

    @RequestMapping("/testCheckoutResult")
    public String test() {
        return "tco-client/cart/after-checkout";
    }
}
