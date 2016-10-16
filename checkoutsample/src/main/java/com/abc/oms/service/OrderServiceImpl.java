package com.abc.oms.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.oms.app.model.CartItem;
import com.abc.oms.app.model.OrderItem;
import com.abc.oms.app.model.PurchaseOrder;
import com.abc.oms.dao.OrderItemDao;
import com.abc.oms.repository.OrderRepository;

/**
 * 
 * OrderServiceImpl provides methods for all Cart related operation
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemDao orderItemDao;

	/**
	 * Method to Save Order
	 * 
	 * @param cartItems
	 * @return Purchase Order
	 */
	@Override
	public PurchaseOrder save(List<CartItem> cartItems) {
		PurchaseOrder order = new PurchaseOrder();
		order.setOrderDate(new Date());
		order.setCustomerName("Demo");
		order = orderRepository.save(order);
		BigDecimal totalTax = new BigDecimal(0);
		BigDecimal totalAmount = new BigDecimal(0);

		for (CartItem cartItem : cartItems) {

			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantiy(cartItem.getQuantity());
			BigDecimal productCost = cartItem.getProduct().getProductPrice()
					.multiply(new BigDecimal(cartItem.getQuantity()));
			orderItem.setItemAmount(productCost);
			totalAmount = totalAmount.add(productCost);
			BigDecimal productTax = productCost
					.multiply(cartItem.getProduct().getCategory().getTaxPecentage().divide(new BigDecimal(100)));
			orderItem.setItemTax(productTax);
			totalTax = totalTax.add(productTax);
			orderItem.setPurchaseOrder(order);
			orderItemDao.saveOrderItem(orderItem);
		}
		order.setTotalAmount(totalAmount);
		order.setTotalTax(totalTax);
		order = orderRepository.save(order);

		return order;
	}

	/**
	 * Method to get the list of Order Items
	 * 
	 * @param cartId
	 * @return
	 */
	@Override
	public List<OrderItem> getOrderItems(String orderNo) {
		return orderItemDao.findByOrderNo(orderNo);

	}

	/**
	 * Method to get Order
	 * 
	 * @param orderNo
	 * @return
	 */
	@Override
	public PurchaseOrder getOrder(String orderNo) {
		return orderRepository.findOne(orderNo);
	}

}