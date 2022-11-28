package com.tcoshop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "findUserShoppingReport", 
query = "select Users.Username as 'Username', sum(Orders_Detail.Quantity) as 'Product_Buy' from Users join\r\n"
        + "Orders on Users.Username = Orders.Username join\r\n"
        + "Orders_Detail on Orders.Id = Orders_Detail.Order_Id\r\n"
        + "group by Users.Username", resultClass = UserShoppingReport.class)
public class UserShoppingReport {
    @Id
    private String username;
    private int productBuy;
    public UserShoppingReport() {
        super();
    }
    public UserShoppingReport(String username, int productBuy) {
        super();
        this.username = username;
        this.productBuy = productBuy;
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
    
}
