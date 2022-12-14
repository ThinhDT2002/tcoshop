package com.tcoshop.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Orders")
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "Username")
	private User user;

	private Date createDate;
	private String status;

	@NotBlank(message = "Vui lòng nhập địa chỉ")
	@Size(max = 100, message = "Địa chỉ tối đa 100 kí tự")
	private String address;
	
	private String description;
	
	@NotBlank(message = "Vui lòng nhập số điện thoại")
	@Size(max = 10, message = "Số điện thoại tối đa 10 số")
	private String phoneNumber;
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;
	private Boolean isPaid;

	public Order() {
		super();
	}

	public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Order(Integer id, User user, Date createDate, String status,
            @NotBlank(message = "Vui lòng nhập địa chỉ") @Size(max = 100, message = "Địa chỉ tối đa 100 kí tự") String address,
            String description,
            @NotBlank(message = "Vui lòng nhập số điện thoại") @Size(max = 10, message = "Số điện thoại tối đa 10 số") String phoneNumber,
            List<OrderDetail> orderDetails, Boolean isPaid) {
        super();
        this.id = id;
        this.user = user;
        this.createDate = createDate;
        this.status = status;
        this.address = address;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.orderDetails = orderDetails;
        this.isPaid = isPaid;
    }

    public String getDescription() {
		return description;
	}

    public void setDescription(String description) {
		this.description = description;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

//    public Set<OrderDetail> getOrderDetails() {
//        return orderDetails;
//    }
//
//    public void setOrderDetails(Set<OrderDetail> orderDetails) {
//        this.orderDetails = orderDetails;
//    }

	@JsonIgnore
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
