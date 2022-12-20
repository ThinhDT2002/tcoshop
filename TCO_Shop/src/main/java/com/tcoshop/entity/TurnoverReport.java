package com.tcoshop.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "findTurnoverReport",
query = "select MONTH(Orders.Create_Date) as 'Month',sum(Orders_Detail.price) as 'Turnover' \r\n"
        + "from Orders\r\n"
        + "join Orders_Detail on Orders.Id = Orders_Detail.Order_Id \r\n"
        + "where Orders.is_Paid = 2 \r\n"
        + "and Year(Orders.Create_Date) = :year \r\n"
        + "group by YEAR(Orders.Create_Date), MONTH(Orders.Create_Date)",
        resultClass = TurnoverReport.class)
public class TurnoverReport implements Serializable{
    @Id
    private Integer month;
    private Double turnover;
    public TurnoverReport() {
        super();
    }
    public TurnoverReport(Integer month, Double turnover) {
        super();
        this.month = month;
        this.turnover = turnover;
    }
    public Integer getMonth() {
        return month;
    }
    public void setMonth(Integer month) {
        this.month = month;
    }
    public Double getTurnover() {
        return turnover;
    }
    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }    
}
