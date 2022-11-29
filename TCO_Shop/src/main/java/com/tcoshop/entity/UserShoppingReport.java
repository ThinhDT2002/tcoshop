package com.tcoshop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "findUserShoppingReport", 
query = "select Users.Username as 'Username', sum(Orders_Detail.Quantity) as 'Product_Buy', sum(Orders_Detail.Price) as 'Total_Cash'"
        + " from Users join\r\n"
        + "Orders on Users.Username = Orders.Username join\r\n"
        + "Orders_Detail on Orders.Id = Orders_Detail.Order_Id\r\n"
        + "group by Users.Username", resultClass = UserShoppingReport.class)
public class UserShoppingReport {
    @Id
    private String username;
    private int productBuy;
    private double totalCash;
    public UserShoppingReport() {
        super();
    }
    public UserShoppingReport(String username, int productBuy, double totalCash) {
        super();
        this.username = username;
        this.productBuy = productBuy;
        this.totalCash = totalCash;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getProductBuy() {
        return productBuy;
    }
    public void setProductBuy(int productBuy) {
        this.productBuy = productBuy;
    }
    public double getTotalCash() {
        return totalCash;
    }
    public void setTotalCash(double totalCash) {
        this.totalCash = totalCash;
    }
    
    
}
