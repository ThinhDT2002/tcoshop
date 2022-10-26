package com.tcoshop.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Roles")
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String name;
	@OneToMany(mappedBy = "role")
	private List<Authority> authorities;
	public Role() {
		super();
	}
	public Role(String id, String name, List<Authority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.authorities = authorities;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public List<Authority> getRoles() {
		return authorities;
	}
	public void setRoles(List<Authority> authorities) {
		this.authorities = authorities;
	}
	
}
