package com.abc.oms.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.abc.oms.app.model.OrderItem;
import com.abc.oms.app.model.Product;
import com.abc.oms.dao.OrderItemDao;

public class OrderItemDaoTest {
	@InjectMocks
	private OrderItemDao orderDaoTest;

	@Mock
	private HibernateEntityManagerFactory factory;

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	private Query query;

	@Mock
	private Criteria criteria;
	private OrderItem orderItem;
	private List<OrderItem> orderItems;
	private Product product;


	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		product = new Product();
		product.setProductDescription("Test");
		product.setProductId("1001");
		product.setProductName("Test");
		product.setProductPrice(new BigDecimal(10));

		orderItem = new OrderItem();
		orderItem.setProduct(product);
		orderItem.setQuantiy(2);
		orderItems = new ArrayList<OrderItem>();
		orderItems.add(orderItem);

	}

	@Test
	public void testSaveOrderItem() {

		Mockito.when(factory.getSessionFactory()).thenReturn(sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);
		Mockito.when(session.save(orderItem)).thenReturn(orderItem);

		OrderItem postOrderItem = orderDaoTest.saveOrderItem(orderItem);

		Assert.assertEquals(postOrderItem.getProduct().getProductName(), "Test");

	}

	@Test
	public void testfindByCartId() {

		Mockito.when(factory.getSessionFactory()).thenReturn(sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);
		Mockito.when(session.createQuery(any(String.class))).thenReturn(query);
		Mockito.when(query.list()).thenReturn(orderItems);

		List<OrderItem> postOrderItem = orderDaoTest.findByOrderNo("1001");

		assertThat(postOrderItem).hasSize(1);

	}

}