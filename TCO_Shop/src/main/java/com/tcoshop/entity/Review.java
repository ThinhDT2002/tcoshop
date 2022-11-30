package com.tcoshop.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Reviews")
public class Review implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "Username")
    private User user;
    @ManyToOne
    @JoinColumn(name = "Product_Id")
    private Product product;
    private String content;
    @Column(name = "Review_Time")
    private Date time;
    @Column(name = "Review_Time_Detail")
    private String timeDetail;
    private Boolean edited;
    public Review() {
        super();
    }
   

	public Review(Integer id, User user, Product product, String content, Date time, String timeDetail,
            Boolean edited) {
        super();
        this.id = id;
        this.user = user;
        this.product = product;
        this.content = content;
        this.time = time;
        this.timeDetail = timeDetail;
        this.edited = edited;
    }

	

    public String getTimeDetail() {
        return timeDetail;
    }


    public void setTimeDetail(String timeDetail) {
        this.timeDetail = timeDetail;
    }


    public Boolean isEdited() {
        return edited;
    }


    public void setEdited(Boolean edited) {
        this.edited = edited;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
}
