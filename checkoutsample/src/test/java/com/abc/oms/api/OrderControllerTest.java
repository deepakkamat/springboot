package com.abc.oms.api;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.abc.oms.api.OrderController;
import com.abc.oms.app.model.Cart;
import com.abc.oms.app.model.CartItem;
import com.abc.oms.app.model.Product;
import com.abc.oms.app.model.PurchaseOrder;
import com.abc.oms.service.CartServiceImpl;
import com.abc.oms.service.OrderServiceImpl;
import com.abc.oms.service.ProductServiceImpl;
import com.abc.oms.transfer.OrderCartItem;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)

public class OrderControllerTest {

	@Mock
	private ProductServiceImpl productService;

	@Mock
	private CartServiceImpl cartService;

	@Mock
	private OrderServiceImpl orderService;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private OrderController orderControllerTest;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(orderControllerTest).build();

	}

	@Test
	public void testListProducts() throws Exception {
		this.mockMvc
				.perform(get("/oms/listProducts").header("Accept-Language", "en_us")
						.accept(MediaType.parseMediaType("application/json")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));

	}

	@Test
	public void testaddToCartWithNoProduct() throws Exception {
		OrderCartItem orderCartItem = new OrderCartItem();
		ObjectMapper mapper = new ObjectMapper();
		String jsonStringRequest = mapper.writeValueAsString(orderCartItem);

		this.mockMvc
				.perform(post("/oms/addToCart").header("Accept-Language", "en_us")
						.contentType(MediaType.APPLICATION_JSON).content(jsonStringRequest))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testaddToCartWithValidProduct() throws Exception {

		OrderCartItem orderCartItem = new OrderCartItem();

		Product product = new Product();

		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantiy(2);

		List<CartItem> cartItems = new ArrayList<CartItem>();
		cartItems.add(cartItem);

		ObjectMapper mapper = new ObjectMapper();
		String jsonStringRequest = mapper.writeValueAsString(orderCartItem);
		Mockito.when(productService.findbyProductId(any(String.class))).thenReturn(product);
		Mockito.when(cartService.findByCartIdAndProductId(any(String.class), any(String.class))).thenReturn(null);
		Mockito.when(cartService.save(any(OrderCartItem.class))).thenReturn(cartItem);

		this.mockMvc.perform(post("/oms/addToCart").header("Accept-Language", "en_us")
				.contentType(MediaType.APPLICATION_JSON).content(jsonStringRequest)).andExpect(status().isOk());

	}

	@Test
	public void testaddToCartWithProductAlreadyExistIntheCart() throws Exception {
		OrderCartItem orderCartItem = new OrderCartItem();

		Product product = new Product();

		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantiy(2);

		List<CartItem> cartItems = new ArrayList<CartItem>();
		cartItems.add(cartItem);

		ObjectMapper mapper = new ObjectMapper();
		String jsonStringRequest = mapper.writeValueAsString(orderCartItem);
		Mockito.when(productService.findbyProductId(any(String.class))).thenReturn(product);
		Mockito.when(cartService.findByCartIdAndProductId(any(String.class), any(String.class))).thenReturn(cartItems);
		Mockito.when(cartService.save(any(OrderCartItem.class))).thenReturn(cartItem);

		this.mockMvc
				.perform(post("/oms/addToCart").header("Accept-Language", "en_us")
						.contentType(MediaType.APPLICATION_JSON).content(jsonStringRequest))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testCheckoutInvalidCart() throws Exception {

		Mockito.when(cartService.getCart(any(String.class))).thenReturn(null);

		this.mockMvc.perform(get("/oms/checkout").header("Accept-Language", "en_us").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testCheckout() throws Exception {
		Product product = new Product();
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantiy(2);

		List<CartItem> cartItems = new ArrayList<CartItem>();
		cartItems.add(cartItem);

		PurchaseOrder order = new PurchaseOrder();

		Mockito.when(cartService.getCart(any(String.class))).thenReturn(new Cart());
		Mockito.when(cartService.getCartItems(any(String.class))).thenReturn(cartItems);
		Mockito.when(orderService.save(cartItems)).thenReturn(order);

		this.mockMvc
				.perform(get("/oms/checkout/123").header("Accept-Language", "en_us").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void testCheckoutNoCartItmes() throws Exception {

		Mockito.when(cartService.getCart(any(String.class))).thenReturn(new Cart());
		Mockito.when(cartService.getCartItems(any(String.class))).thenReturn(null);

		this.mockMvc
				.perform(get("/oms/checkout/123").header("Accept-Language", "en_us").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

}