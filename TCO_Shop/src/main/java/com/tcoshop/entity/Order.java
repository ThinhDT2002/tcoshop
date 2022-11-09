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
	private Boolean status;

	@NotBlank(message = "Vui lòng nhập tên sản phẩm")
	@Size(max = 50, message = "Tên sản phẩm tối đa 50 kí tự")
	private String address;
	
	private String description;
	
	@NotBlank(message = "Vui lòng nhập tên sản phẩm")
	@Size(max = 50, message = "Tên sản phẩm tối đa 50 kí tự")
	private String phoneNumber;

	@JsonIgnore
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;

	public Order() {
		super();
	}

	public Order(Integer id, User user, Date createDate, Boolean status, String address, String phoneNumber, String description,
			List<OrderDetail> orderDetails) {
		super();
		this.id = id;
		this.user = user;
		this.createDate = createDate;
		this.status = status;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.orderDetails = orderDetails;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
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

	@JsonIgnore
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
