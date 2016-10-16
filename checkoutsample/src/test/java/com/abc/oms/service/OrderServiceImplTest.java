package com.abc.oms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.abc.oms.app.model.CartItem;
import com.abc.oms.app.model.Category;
import com.abc.oms.app.model.OrderItem;
import com.abc.oms.app.model.Product;
import com.abc.oms.app.model.PurchaseOrder;
import com.abc.oms.dao.OrderItemDao;
import com.abc.oms.repository.OrderRepository;
import com.abc.oms.service.OrderServiceImpl;

public class OrderServiceImplTest {

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderItemDao orderItemDao;

	private Product product;
	private OrderItem orderItem;
	private List<OrderItem> orderItems;
	private PurchaseOrder order;
	private CartItem cartItem;
	private List<CartItem> cartItems;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		Category categoryA = new Category();
		categoryA.setTaxPecentage(new BigDecimal(10));

		product = new Product();
		product.setProductDescription("Test");
		product.setProductId("1001");
		product.setProductName("Test");
		product.setProductPrice(new BigDecimal(10));
		product.setCategory(categoryA);

		order = new PurchaseOrder();
		order.setCustomerName("Demo");

		orderItem = new OrderItem();
		orderItem.setProduct(product);
		orderItem.setQuantiy(2);

		orderItems = new ArrayList<OrderItem>();
		orderItems.add(orderItem);

		cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantiy(2);

		cartItems = new ArrayList<CartItem>();
		cartItems.add(cartItem);

	}

	@Test
	public void testGetOrderItem() {

		Mockito.when(orderItemDao.findByOrderNo(any(String.class))).thenReturn(orderItems);
		List<OrderItem> postOrderItems = orderServiceImpl.getOrderItems("100001");
		assertThat(postOrderItems).hasSize(1);

	}

	@Test
	public void testSave() {

		Mockito.when(orderRepository.save(any(PurchaseOrder.class))).thenReturn(order);
		PurchaseOrder postPurchaseOrder = orderServiceImpl.save(cartItems);
		Assert.assertEquals("Demo", postPurchaseOrder.getCustomerName());

	}

	@Test
	public void testGetOrder() {

		Mockito.when(orderRepository.findOne(any(String.class))).thenReturn(order);
		PurchaseOrder postOrder = orderServiceImpl.getOrder("100001");

		Assert.assertEquals("Demo", postOrder.getCustomerName());
	}

}