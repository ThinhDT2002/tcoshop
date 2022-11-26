package com.tcoshop.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "findSaleReport", query = "select MONTH(Orders.Create_Date) as 'Month',count(distinct Orders.Id) as 'Order_Count', SUM(Orders_Detail.Quantity) as 'Product_Count' \r\n"
        + "from Orders join Orders_Detail\r\n"
        + "on Orders.Id = Orders_Detail.Order_Id\r\n"
        + "where Year(Orders.Create_Date) = :year \r\n"
        + "group by YEAR(Orders.Create_Date), MONTH(Create_Date)",
        resultClass = SaleReport.class)
public class SaleReport implements Serializable{
    @Id
    private Integer month;
    private Integer orderCount;
    private Integer productCount;
    
    public SaleReport() {
        super();
    }
    public SaleReport(Integer month, Integer orderCount, Integer productCount) {
        super();
        this.month = month;
        this.orderCount = orderCount;
        this.productCount = productCount;
    }
    public Integer getMonth() {
        return month;
    }
    public void setMonth(Integer month) {
        this.month = month;
    }
    public Integer getOrderCount() {
        return orderCount;
    }
    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }
    public Integer getProductCount() {
        return productCount;
    }
    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }
    
    
}
