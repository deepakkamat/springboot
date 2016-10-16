package com.abc.oms.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.oms.app.model.CartItem;

@Service
public class CartItemDao {
	@Autowired
	HibernateEntityManagerFactory factory;

	private Session getSession() {
		return factory.getSessionFactory().openSession();

	}

	public CartItem saveCartItem(CartItem cartItem) {
		Session session = getSession();
		try {
			cartItem = (CartItem) session.save(cartItem);
			session.flush();
		} finally {
			session.close();

		}
		return cartItem;

	}

	@SuppressWarnings("unchecked")
	public List<CartItem> listCartItems() {

		Session session = getSession();
		List<CartItem> cartItems = new ArrayList<CartItem>();
		try {
			Query query = session.createQuery("from CartItem");
			cartItems = query.list();
		} finally {
			session.close();
		}
		return cartItems;
	}

	public boolean deleteCartItems(String cartId) {
		boolean deleted = false;
		Session session = getSession();
		try {
			Query query = session.createQuery("delete from CartItem item where item.cart.cartId = :cartId");
			query.setParameter("cartId", cartId);
			query.executeUpdate();
			deleted = true;
		} finally {
			session.close();
		}
		return deleted;
	}

	@SuppressWarnings("unchecked")
	public List<CartItem> findByCartId(String cartId) {
		Session session = getSession();
		List<CartItem> cartItems = new ArrayList<CartItem>();

		try {
			Query query = session.createQuery("from CartItem item where item.cart.cartId = :cartId");
			query.setParameter("cartId", cartId);
			cartItems = query.list();
		} finally {
			session.close();
		}

		return cartItems;
	}

	@SuppressWarnings("unchecked")
	public List<CartItem> findByCartIdAndProductId(String cartId, String productId) {
		Session session = getSession();
		List<CartItem> cartItems = new ArrayList<CartItem>();
		try {
			Criteria crit = session.createCriteria(CartItem.class);
			Criteria productCriteria = crit.createCriteria("product");
			productCriteria.add(Restrictions.eq("productId", productId));
			Criteria cartCriteria = crit.createCriteria("cart");
			cartCriteria.add(Restrictions.eq("cartId", cartId));

			cartItems = crit.list();

		} finally {
			session.close();
		}

		return cartItems;
	}
}