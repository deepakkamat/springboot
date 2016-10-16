package com.abc.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.abc.oms.app.model.Cart;

@Repository
@RepositoryRestResource
public interface CartRepository extends JpaRepository<Cart, String> {

	public Cart findByCartId(@Param("cartId") String cartId);

}