package com.abc.oms.app.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "Product")
public class Product {

	@Id
	@Column(name = "productId",  nullable = false)
	private String productId;
	
	@Column(name = "productName", unique = true, nullable = false)
	private String productName;
	
	@Column(name = "productPrice",  nullable = false)
	private BigDecimal productPrice;
	
	@Column(name = "productDescription",  nullable = false)
	private String productDescription;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId")
	private Category category;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
