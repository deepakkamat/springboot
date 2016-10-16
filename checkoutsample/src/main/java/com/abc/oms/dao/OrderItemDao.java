package com.abc.oms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.oms.app.model.OrderItem;

@Service
public class OrderItemDao {
	@Autowired
	HibernateEntityManagerFactory factory;

	private Session getSession() {
		return factory.getSessionFactory().openSession();

	}

	public OrderItem saveOrderItem(OrderItem orderItem) {
		Session session = getSession();
		try {
			orderItem = (OrderItem) session.save(orderItem);
			session.flush();
		} finally {
			session.close();

		}
		return orderItem;

	}

	@SuppressWarnings("unchecked")
	public List<OrderItem> findByOrderNo(String orderNo) {
		Session session = getSession();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		try {
			Query query = session.createQuery("from OrderItem item where item.purchaseOrder.orderNo = :orderNo");
			query.setParameter("orderNo", orderNo);
			orderItems = query.list();
		} finally {
			session.close();
		}

		return orderItems;
	}
}