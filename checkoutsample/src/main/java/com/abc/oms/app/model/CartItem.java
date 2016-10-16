package com.abc.oms.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;


@Entity
@Table(name = "CartItem")
public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "productId", insertable = false, updatable = false)
	private Product product;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Id
	@ManyToOne
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
	@JoinColumn(name = "cartId", insertable = false, updatable = false)
	private Cart cart;

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantiy(Integer quantity) {
		this.quantity = quantity;
	}

}
