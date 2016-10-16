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

import com.abc.oms.app.model.Cart;
import com.abc.oms.app.model.CartItem;
import com.abc.oms.app.model.Product;
import com.abc.oms.dao.CartItemDao;
import com.abc.oms.repository.CartRepository;
import com.abc.oms.repository.ProductRepository;
import com.abc.oms.service.CartServiceImpl;
import com.abc.oms.transfer.OrderCartItem;

public class CartServiceImplTest {

	@InjectMocks
	private CartServiceImpl cartServiceImpl;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private CartRepository cartRepository;

	@Mock
	private CartItemDao cartItemDao;

	private List<CartItem> cartItems;
	private CartItem cartItem;
	private Product product;
	private Cart cart;

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

		cart = new Cart();
		cart.setCartId("10001");
		cart.setCustomerName("Demo");
	}

	@Test
	public void testGetCartItem() {

		Mockito.when(cartItemDao.findByCartId(any(String.class))).thenReturn(cartItems);
		List<CartItem> postcartitem = cartServiceImpl.getCartItems("100001");
		assertThat(postcartitem).hasSize(1);

	}

	@Test
	public void testSave() {
		OrderCartItem orderCartItem = new OrderCartItem();
		orderCartItem.setCartId("100001");
		orderCartItem.setProductId("1001");
		orderCartItem.setQuantity(2);
		Mockito.when(productRepository.findByProductId(any(String.class))).thenReturn(product);
		Mockito.when(cartRepository.save(any(Cart.class))).thenReturn(cart);
		Mockito.when(cartItemDao.findByCartId(any(String.class))).thenReturn(cartItems);
		Mockito.when(cartItemDao.saveCartItem(any(CartItem.class))).thenReturn(cartItem);

		CartItem postCartItem = cartServiceImpl.save(orderCartItem);
		Assert.assertEquals(postCartItem.getProduct().getProductName(), "Test");

	}

	@Test
	public void testGetCart() {
		Mockito.when(cartRepository.findByCartId(any(String.class))).thenReturn(cart);
		Cart postCart = cartServiceImpl.getCart("100001");
		Assert.assertEquals(postCart.getCustomerName(), "Demo");
	}

	@Test
	public void testDeleteCartItems() {

		Mockito.when(cartItemDao.deleteCartItems(any(String.class))).thenReturn(true);
		boolean result = cartServiceImpl.deleteCartItems(cart);

		Assert.assertTrue(result);

	}

	@Test
	public void testFindByCartIdAndProductId() {

		Mockito.when(cartItemDao.findByCartIdAndProductId(any(String.class), any(String.class))).thenReturn(cartItems);
		List<CartItem> postCartItems = cartServiceImpl.findByCartIdAndProductId("10001", "1002");

		assertThat(postCartItems).hasSize(1);

	}
}