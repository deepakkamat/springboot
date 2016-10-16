package com.abc.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.abc.oms.app.model.Category;

@Repository
@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category, String> {

}