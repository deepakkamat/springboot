package com.abc.oms.service;

import java.util.List;

import com.abc.oms.app.model.Product;

/**
 * Interface for Product service
 */
public interface ProductService {
	/**
	 * Method list the Product
	 * 
	 * @return
	 */
	List<Product> listProducts();

	/**
	 * Method to find the Product by product id
	 * 
	 * @return
	 */
	Product findbyProductId(String productId);

}