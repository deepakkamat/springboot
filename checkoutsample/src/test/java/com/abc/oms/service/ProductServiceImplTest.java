package com.abc.oms.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.abc.oms.app.model.Product;
import com.abc.oms.repository.ProductRepository;
import com.abc.oms.service.ProductServiceImpl;

public class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testListProduct() {
		Product product1 = new Product();
		product1.setProductDescription("Test");
		product1.setProductId("1001");
		product1.setProductName("Test");
		product1.setProductPrice(new BigDecimal(10));

		Product product2 = new Product();
		product2.setProductDescription("Test");
		product2.setProductId("1001");
		product2.setProductName("Test");
		product2.setProductPrice(new BigDecimal(10));

		List<Product> products = new ArrayList<Product>();
		products.add(product1);
		products.add(product2);

		Mockito.when(productRepository.findAll()).thenReturn(products);
		List<Product> postProducts = productServiceImpl.listProducts();
		assertThat(postProducts).hasSize(2);

	}

}