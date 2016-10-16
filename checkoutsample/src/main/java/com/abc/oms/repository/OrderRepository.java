package com.abc.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.abc.oms.app.model.PurchaseOrder;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<PurchaseOrder, String> {

}