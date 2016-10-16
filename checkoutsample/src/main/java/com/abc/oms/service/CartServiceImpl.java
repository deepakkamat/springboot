package com.abc.oms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.oms.app.model.Cart;
import com.abc.oms.app.model.CartItem;
import com.abc.oms.app.model.Product;
import com.abc.oms.dao.CartItemDao;
import com.abc.oms.repository.CartRepository;
import com.abc.oms.repository.ProductRepository;
import com.abc.oms.transfer.OrderCartItem;

/**
 * CartServiceImpl provides methods for all Cart related operation
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CartItemDao cartItemDao;

	/**
	 * Method to save Car Item
	 * 
	 * @param orderCartItem
	 * @return
	 */
	@Override
	public CartItem save(OrderCartItem orderCartItem) {
		Product product = productRepository.findByProductId(orderCartItem.getProductId());
		CartItem cartItem = new CartItem();
		Cart cart = cartRepository.findByCartId(orderCartItem.getCartId());
		if (cart == null) {
			cart = new Cart();
			cart.setCartId(orderCartItem.getCartId());
			cart.setCustomerName("Demo");
			cart.setCounter(1);
			cart = cartRepository.save(cart);
		}
		cartItem.setProduct(product);
		cartItem.setQuantiy(orderCartItem.getQuantity());
		cartItem.setCart(cart);

		cartItem = cartItemDao.saveCartItem(cartItem);
		
		return cartItem;
	}

	/**
	 * Method to get the list of Cart Items
	 * 
	 * @param cartId
	 * @return
	 */
	@Override
	public List<CartItem> getCartItems(String cartId) {
		return cartItemDao.findByCartId(cartId);
	}

	/**
	 * Method to get Cart
	 * 
	 * @param cartId
	 * @return
	 */
	@Override
	public Cart getCart(String cartId) {
		return cartRepository.findByCartId(cartId);
	}

	/**
	 * delete cartItems
	 * 
	 * @param cartId
	 * @return
	 */
	@Override
	public boolean deleteCartItems(Cart cart) {
		return cartItemDao.deleteCartItems(cart.getCartId());
	}

	/**
	 * Method to get CartItem by cart id and product id
	 * @param cartId
	 * @param productId
	 * @return
	 */
	@Override
	public List<CartItem> findByCartIdAndProductId(String cartId, String productId) {
		return cartItemDao.findByCartIdAndProductId(cartId, productId);
	}

}