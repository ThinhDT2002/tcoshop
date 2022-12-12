package com.tcoshop.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Products")
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Vui lòng nhập tên sản phẩm")
	@Size(max = 50, message = "Tên sản phẩm tối đa 50 kí tự")
	private String name;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	
	@NotNull(message = "Vui lòng nhập giá sản phẩm")
	@Min(value = 0, message = "Giá phải lớn hơn 0")
	private Double price;
	
	private String description;
	
	@NotNull(message = "Vui lòng nhập số lượng sản phẩm")
	@Min(value = 0, message = "Số lượng phải lớn hơn 0")
	private Integer stock;
	
	@NotNull(message = "Vui lòng nhập giảm giá")
	@Min(value = 0, message = "Giá giảm lớn hơn 0")
	@Max(value = 100, message = "Giá giảm ít hơn 100")
	private Integer discount;
	
	
	@ManyToOne
	@JoinColumn(name = "Category_Id")
	@NotNull(message = "Vui lòng lựa chọn danh mục")
	private Category category;
	
	
	@ManyToOne
	@JoinColumn(name = "Subcategory_Id")
	@NotNull(message = "Vui lòng lựa chọn thương hiệu")
	private Subcategory subcategory;
	@OneToMany(mappedBy = "product")
	private List<ProductVariation> productVariations;
	@OneToMany(cascade = CascadeType.ALL, mappedBy =  "product", fetch=FetchType.EAGER)
	private List<Review> reviews;
	@OneToMany(mappedBy = "product")
	private List<Favorite> favorites;
	public Product() {
		super();
	}
	
	public Product(Integer id, String name, String image1, String image2, String image3, String image4, Double price,
			String description, Integer stock, Integer discount, Category category, Subcategory subcategory,
			List<ProductVariation> productVariations, List<Review> reviews, List<Favorite> favorites) {
		super();
		this.id = id;
		this.name = name;
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
		this.image4 = image4;
		this.price = price;
		this.description = description;
		this.stock = stock;
		this.discount = discount;
		this.category = category;
		this.subcategory = subcategory;
		this.productVariations = productVariations;
		this.reviews = reviews;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getImage4() {
		return image4;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Subcategory getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}
	@JsonIgnore
	public List<ProductVariation> getProductVariations() {
		return productVariations;
	}
	public void setProductVariations(List<ProductVariation> productVariations) {
		this.productVariations = productVariations;
	}
	@JsonIgnore
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    @JsonIgnore
    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }
	
	
	
}
