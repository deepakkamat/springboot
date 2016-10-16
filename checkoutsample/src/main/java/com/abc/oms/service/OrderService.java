package com.abc.oms.service;

import java.util.List;

import com.abc.oms.app.model.CartItem;
import com.abc.oms.app.model.OrderItem;
import com.abc.oms.app.model.PurchaseOrder;

/**
 * Interface to Order Service
 *
 */
public interface OrderService {

	/**
	 * Method to Save Order
	 * 
	 * @param cartItems
	 * @return Purchase Order
	 */
	PurchaseOrder save(List<CartItem> cartItems);

	/**
	 * Method to get the list of Order Items
	 * 
	 * @param cartId
	 * @return
	 */
	List<OrderItem> getOrderItems(String cartId);

	/**
	 * Method to get Order
	 * 
	 * @param orderNo
	 * @return
	 */
	PurchaseOrder getOrder(String orderNo);

}