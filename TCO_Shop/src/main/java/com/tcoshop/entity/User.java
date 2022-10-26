package com.tcoshop.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Users")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String username;
	private String password;
	private String email;
	private String fullname;
	private Boolean status;
	private String activateCode;
	private String forgotPasswordCode;
	private String avatar;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Authority> authorities;
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	public User() {
		super();
	}
	public User(String username, String password, String email, String fullname, Boolean status, String activateCode,
			String forgotPasswordCode, String avatar, List<Authority> authorities, List<Order> orders) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullname = fullname;
		this.status = status;
		this.activateCode = activateCode;
		this.forgotPasswordCode = forgotPasswordCode;
		this.avatar = avatar;
		this.authorities = authorities;
		this.orders = orders;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getActivateCode() {
		return activateCode;
	}
	public void setActivateCode(String activateCode) {
		this.activateCode = activateCode;
	}
	public String getForgotPasswordCode() {
		return forgotPasswordCode;
	}
	public void setForgotPasswordCode(String forgotPasswordCode) {
		this.forgotPasswordCode = forgotPasswordCode;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	@JsonIgnore
	public List<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	@JsonIgnore
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
