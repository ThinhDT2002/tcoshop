package com.tcoshop.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "findUserRegistryReport",
query = "select Month(Create_Date) as 'Month',\r\n"
        + "count(Users.Username) as 'User_Registry'\r\n"
        + "from Users\r\n"
        + "where Year(Create_Date) = :year and Users.Role_Id = 'USER'\r\n"
        + "group by MONTH(Create_Date)", resultClass = UserRegistryReport.class)
public class UserRegistryReport implements Serializable{
    @Id
    private int month;
    private int userRegistry;
    public UserRegistryReport() {
        super();
    }
    public UserRegistryReport(int month, int userRegistry) {
        super();
        this.month = month;
        this.userRegistry = userRegistry;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getUserRegistry() {
        return userRegistry;
    }
    public void setUserRegistry(int userRegistry) {
        this.userRegistry = userRegistry;
    }
    
    
}
