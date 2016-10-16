package com.abc.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.abc.oms.app.model.Product;

@Repository
@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, String> {
	public Product findByProductId(@Param("productId") String productId);

}