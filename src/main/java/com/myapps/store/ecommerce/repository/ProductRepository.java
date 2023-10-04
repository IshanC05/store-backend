package com.myapps.store.ecommerce.repository;

import com.myapps.store.ecommerce.model.Category;
import com.myapps.store.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
}
