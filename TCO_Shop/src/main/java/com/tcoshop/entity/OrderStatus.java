package com.tcoshop.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "OrderStatus")
public class OrderStatus implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String statusName;
    public OrderStatus() {
        super();
    }
    public OrderStatus(String id, String statusName) {
        super();
        this.id = id;
        this.statusName = statusName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStatusName() {
        return statusName;
    }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
}
