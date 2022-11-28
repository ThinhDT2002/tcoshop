package com.tcoshop.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "findTurnoverDetailReport",
query = "select Products.Id as 'Id', MONTH(Orders.Create_Date) as 'Month', Products.Name as 'Product_Name',\r\n"
        + "sum(Orders_Detail.Quantity) as 'Quantity',\r\n"
        + "sum(Orders_Detail.Price) as 'Turnover' from Orders \r\n"
        + "join Orders_Detail on Orders.Id = Orders_Detail.Order_Id\r\n"
        + "join Products on Orders_Detail.Product_Id = Products.Id\r\n"
        + "where YEAR(Orders.Create_Date) = :year and Orders.Status = 'DaGiaoHang'\r\n"
        + "and MONTH(Orders.Create_Date) = :month \r\n"
        + "group by MONTH(Orders.Create_Date), Products.Name, Products.Id",
        resultClass = TurnoverDetailReport.class)
public class TurnoverDetailReport implements Serializable{
    private Integer month;
    @Id
    private Integer id;
    private String productName;
    private Integer quantity;
    private Double turnover;
    public TurnoverDetailReport() {
        super();
    }
    public TurnoverDetailReport(Integer month, Integer id, String productName, Integer quantity, Double turnover) {
        super();
        this.month = month;
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.turnover = turnover;
    }
    public Integer getMonth() {
        return month;
    }
    public void setMonth(Integer month) {
        this.month = month;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Double getTurnover() {
        return turnover;
    }
    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }
    
    
}
