package com.tcoshop.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Users")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Size(min = 6, max = 30, message = "Vui lòng nhập tài khoản từ 6 - 30 kí tự")
	private String username;
	@Size(min = 6, max = 30, message = "Vui lòng nhập mật khẩu từ 6 - 30 kí tự")
	private String password;
	private String email;
	private String fullname;
	private String address;
	private String phone;
	private String introduce;
	private Date createDate;
	@NotNull(message = "Vui lòng chọn trạng thái")
	private Boolean status;
	private String activateCode;
	private String forgotPasswordCode;
	private String avatar;
	@NotNull(message = "Vui lòng chọn vai trò")
	@ManyToOne
	@JoinColumn(name = "Role_Id")
	private Role role;
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	@OneToMany(mappedBy = "user")
	private List<Review> reviews;
	@OneToMany(mappedBy = "user")
	private List<Favorite> favorites;
	public User() {
		super();
	}
	
	

    public User(@Size(min = 6, max = 30, message = "Vui lòng nhập tài khoản từ 6 - 30 kí tự") String username,
            @Size(min = 6, max = 30, message = "Vui lòng nhập mật khẩu từ 6 - 30 kí tự") String password,
            String email, String fullname, String address, String phone, String introduce, Date createDate,
            @NotNull(message = "Vui lòng chọn trạng thái") Boolean status, String activateCode,
            String forgotPasswordCode, String avatar, @NotNull(message = "Vui lòng chọn vai trò") Role role,
            List<Order> orders, List<Review> reviews, List<Favorite> favorites) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
        this.introduce = introduce;
        this.createDate = createDate;
        this.status = status;
        this.activateCode = activateCode;
        this.forgotPasswordCode = forgotPasswordCode;
        this.avatar = avatar;
        this.role = role;
        this.orders = orders;
        this.reviews = reviews;
        this.favorites = favorites;
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
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	@JsonIgnore
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonIgnore
    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }
	
	
	
}
