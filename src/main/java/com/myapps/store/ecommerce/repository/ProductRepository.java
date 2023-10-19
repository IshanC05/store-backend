package com.myapps.store.ecommerce.repository;

import com.myapps.store.ecommerce.model.Category;
import com.myapps.store.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByCategory(Category category, Pageable pageable);
}
