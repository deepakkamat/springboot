package com.abc.oms.api;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abc.oms.api.exception.ValidationException;
import com.abc.oms.app.model.Cart;
import com.abc.oms.app.model.CartItem;
import com.abc.oms.app.model.Product;
import com.abc.oms.app.model.PurchaseOrder;
import com.abc.oms.service.CartServiceImpl;
import com.abc.oms.service.OrderServiceImpl;
import com.abc.oms.service.ProductServiceImpl;
import com.abc.oms.transfer.OrderCartItem;

/**
 * Rest Controller for managing Order
 *
 */
@RequestMapping("/oms")
@RestController
public class OrderController {

	@Autowired
	private CartServiceImpl cartService;

	@Autowired
	private ProductServiceImpl productservice;

	@Autowired
	private OrderServiceImpl orderService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Add item to Cart
	 * 
	 * @param locale
	 * @param orderCartItem
	 * @return
	 */

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public ResponseEntity<CartItem> addProducttoCart(@RequestHeader(name = "Accept-Language") Locale locale,
			@RequestBody OrderCartItem orderCartItem) throws Exception {
		if (ObjectUtils.isEmpty(productservice.findbyProductId(orderCartItem.getProductId()))) {
			throw new ValidationException(messageSource.getMessage("invalid.product", null, locale));

		}
		List<CartItem> cartItems = cartService.findByCartIdAndProductId(orderCartItem.getCartId(),
				orderCartItem.getProductId());
		if (!CollectionUtils.isEmpty(cartItems)) {
			throw new ValidationException(messageSource.getMessage("product.in.cart", null, locale));

		}
		return new ResponseEntity<CartItem>(cartService.save(orderCartItem), HttpStatus.OK);

	}

	/**
	 * List the products
	 * 
	 * @param locale
	 * @return
	 */
	@RequestMapping("/listProducts")
	public ResponseEntity<List<Product>> listProduct(@RequestHeader(name = "Accept-Language") Locale locale) {
		List<Product> products = productservice.listProducts();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

	}

	/**
	 * checkout the cart items
	 * 
	 * @param locale
	 * @param cartId
	 * @return
	 */
	@RequestMapping("/checkout/{cartId}")
	public ResponseEntity<PurchaseOrder> checkOut(@RequestHeader(name = "Accept-Language") Locale locale,
			@PathVariable String cartId) {
		Cart cart = cartService.getCart(cartId);
		if (ObjectUtils.isEmpty(cart)) {
			throw new ValidationException(messageSource.getMessage("invalid.cart", null, locale));
		}
		List<CartItem> cartItems = cartService.getCartItems(cartId);
		if (CollectionUtils.isEmpty(cartItems)) {
			throw new ValidationException(messageSource.getMessage("cart.empty", null, locale));

		}
		PurchaseOrder order = orderService.save(cartItems);
		if (!ObjectUtils.isEmpty(order)) {
			cartService.deleteCartItems(cart);
		}
		return new ResponseEntity<PurchaseOrder>(order, HttpStatus.OK);

	}

}