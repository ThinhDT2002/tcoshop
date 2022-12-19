package com.tcoshop.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Transactions")
public class Transaction implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
    private String bankCode;
    private String cardType;
    private String transactionInfo;
    private Date payDate;
    private String payTime;
    private String payStatus;
    private String transactionStatus;
    private String transactionNo;
    private String bankTranNo;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_Id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Order order;
    public Transaction() {
        super();
    }
    public Transaction(Integer id, Double amount, String bankCode, String cardType, String transactionInfo,
            Date payDate, String payStatus, String transactionStatus, String transactionNo, Order order,
            String bankTranNo, String payTime) {
        super();
        this.id = id;
        this.amount = amount;
        this.bankCode = bankCode;
        this.cardType = cardType;
        this.transactionInfo = transactionInfo;
        this.payDate = payDate;
        this.payStatus = payStatus;
        this.transactionStatus = transactionStatus;
        this.transactionNo = transactionNo;
        this.order = order;
        this.bankTranNo = bankTranNo;
        this.payTime = payTime;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getTransactionInfo() {
        return transactionInfo;
    }
    public void setTransactionInfo(String transactionInfo) {
        this.transactionInfo = transactionInfo;
    }
    public Date getPayDate() {
        return payDate;
    }
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
    public String getPayStatus() {
        return payStatus;
    }
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
    public String getTransactionStatus() {
        return transactionStatus;
    }
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
    public String getTransactionNo() {
        return transactionNo;
    }
    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }
    
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public String getBankTranNo() {
        return bankTranNo;
    }
    public void setBankTranNo(String bankTranNo) {
        this.bankTranNo = bankTranNo;
    }
    public String getPayTime() {
        return payTime;
    }
    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
    
    
}
