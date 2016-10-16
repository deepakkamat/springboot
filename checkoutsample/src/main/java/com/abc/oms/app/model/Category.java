package com.abc.oms.app.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Category")
public class Category {
	
	@Id
	@Column(name = "categoryId",  nullable = false)
	private String  categoryId;
	
	@Column(name = "categoryName", unique = true, nullable = false)
	private String  categoryName;
	
	@Column(name = "taxPecentage", nullable = false)
	private BigDecimal taxPecentage; 
	
	public BigDecimal getTaxPecentage() {
		return taxPecentage;
	}
	public void setTaxPecentage(BigDecimal taxPecentage) {
		this.taxPecentage = taxPecentage;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
