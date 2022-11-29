package com.tcoshop.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Categories")
public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@NotBlank(message = "Vui lòng nhập CategoryId")
	@Size(max = 10, message = "Tối đa 10 kí tự")
	private String id;
	
	@NotBlank(message = "Vui lòng nhập tên danh mục")
	@Size(max = 40, message = "Tối đa 40 kí tự")
	private String name;
	private String icon;
	@OneToMany(mappedBy = "category")
	private List<Subcategory> subcategories;
	@OneToMany(mappedBy = "category")
	private List<Product> products;
	public Category() {
		super();
	}
	public Category(String id, String name, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
	}
	
	public Category(String id, String name, String icon, List<Subcategory> subcategories, List<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.subcategories = subcategories;
		this.products = products;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@JsonIgnore
	public List<Subcategory> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(List<Subcategory> subcategories) {
		this.subcategories = subcategories;
	}
	@JsonIgnore
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}	
	
}
