package com.tcoshop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "findTopProduct", query = "select top 3 Products.Id as 'Id',\r\n"
        + "count(Products.Id) as 'Sold'\r\n"
        + "from Orders_Detail\r\n"
        + "join Products on Products.Id = Orders_Detail.Order_Id\r\n"
        + "group by Products.Id order by COUNT(Product_Id) desc", resultClass = TopProduct.class)
public class TopProduct {
    @Id
    private int id;
    private int sold;
    public TopProduct() {
        super();
    }
    public TopProduct(int id, int sold) {
        super();
        this.id = id;
        this.sold = sold;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSold() {
        return sold;
    }
    public void setSold(int sold) {
        this.sold = sold;
    }
    
}
