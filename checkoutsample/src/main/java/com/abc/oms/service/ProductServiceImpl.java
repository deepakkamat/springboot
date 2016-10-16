package com.abc.oms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.oms.app.model.Product;
import com.abc.oms.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> listProducts() {
		return productRepository.findAll();

	}

	@Override
	public Product findbyProductId(String productId) {
		return productRepository.findOne(productId);
	}

}