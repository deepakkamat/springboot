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

import com.abc.oms.app.model.CartItem;
import com.abc.oms.app.model.Product;
import com.abc.oms.dao.CartItemDao;

public class CartItemDaoTest {

	@InjectMocks
	private CartItemDao cartDaoTest;

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

	private List<CartItem> cartItems;
	private CartItem cartItem;
	private Product product;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		product = new Product();
		product.setProductDescription("Test");
		product.setProductId("1001");
		product.setProductName("Test");
		product.setProductPrice(new BigDecimal(10));

		cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantiy(2);

		cartItems = new ArrayList<CartItem>();
		cartItems.add(cartItem);

	}

	@Test
	public void testSaveCartItem() {

		Mockito.when(factory.getSessionFactory()).thenReturn(sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);
		Mockito.when(session.save(cartItem)).thenReturn(cartItem);

		CartItem postCartItem = cartDaoTest.saveCartItem(cartItem);

		Assert.assertEquals(postCartItem.getProduct().getProductName(), "Test");

	}

	@Test
	public void testListCartItems() {

		Mockito.when(factory.getSessionFactory()).thenReturn(sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);
		Mockito.when(session.createQuery(any(String.class))).thenReturn(query);
		Mockito.when(query.list()).thenReturn(cartItems);

		List<CartItem> postCartItems = cartDaoTest.listCartItems();

		assertThat(postCartItems).hasSize(1);

	}

	@Test
	public void testDeleteCartItems() {

		Mockito.when(factory.getSessionFactory()).thenReturn(sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);
		Mockito.when(session.createQuery(any(String.class))).thenReturn(query);
		Mockito.when(query.executeUpdate()).thenReturn(1);

		boolean deleted = cartDaoTest.deleteCartItems("10001");

		Assert.assertTrue(deleted);

	}

	@Test
	public void testfindByCartId() {

		Mockito.when(factory.getSessionFactory()).thenReturn(sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);
		Mockito.when(session.createQuery(any(String.class))).thenReturn(query);
		Mockito.when(query.list()).thenReturn(cartItems);

		List<CartItem> postCartItems = cartDaoTest.findByCartId("1001");

		assertThat(postCartItems).hasSize(1);

	}

	@Test
	public void findByCartIdAndProductId() {

		Mockito.when(factory.getSessionFactory()).thenReturn(sessionFactory);
		Mockito.when(sessionFactory.openSession()).thenReturn(session);
		Mockito.when(session.createCriteria(CartItem.class)).thenReturn(criteria);
		Mockito.when(criteria.createCriteria(any(String.class))).thenReturn(criteria);

		Mockito.when(criteria.list()).thenReturn(cartItems);

		List<CartItem> postCartItems = cartDaoTest.findByCartIdAndProductId("1001", "10002");

		assertThat(postCartItems).hasSize(1);

	}
}