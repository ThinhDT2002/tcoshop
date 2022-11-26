package com.tcoshop.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "findOrderStatusReport",
query = "select count(*) as 'Order_Per_Status_Count', Status as 'Status' from Orders \r\n"
        + "where Year(Create_Date) = :year \r\n"
        + "group by Status", resultClass = OrderStatusReport.class)
public class OrderStatusReport implements Serializable{
    @Id
    private String status;
    private Integer orderPerStatusCount;
    
    public OrderStatusReport() {
        super();
    }
    public OrderStatusReport(String status, Integer orderPerStatusCount) {
        super();
        this.status = status;
        this.orderPerStatusCount = orderPerStatusCount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getOrderPerStatusCount() {
        return orderPerStatusCount;
    }
    public void setOrderPerStatusCount(Integer orderPerStatusCount) {
        this.orderPerStatusCount = orderPerStatusCount;
    }
    
    
}
