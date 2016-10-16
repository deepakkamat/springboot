package com.abc.oms.service;

import java.util.List;

import com.abc.oms.app.model.Cart;
import com.abc.oms.app.model.CartItem;
import com.abc.oms.transfer.OrderCartItem;

/**
 * Interface for Cart service
 */
public interface CartService {
	/**
	 * Method to save Car Item
	 * 
	 * @param orderCartItem
	 * @return
	 */
	CartItem save(OrderCartItem orderCartItem);

	/**
	 * Method to get the list of Cart Items
	 * 
	 * @param cartId
	 * @return
	 */
	List<CartItem> getCartItems(String cartId);

	/**
	 * Method to get the Cart
	 * 
	 * @param cartId
	 * @return
	 */
	Cart getCart(String cartId);

	/**
	 * delete cartItems
	 * 
	 * @param cart
	 * @return
	 */
	boolean deleteCartItems(Cart cart);
	
	/**
	 * Method to get CartItem by cart id and product id
	 * @param cartId
	 * @param productId
	 * @return
	 */
	List<CartItem> findByCartIdAndProductId(String cartId, String productId);

}