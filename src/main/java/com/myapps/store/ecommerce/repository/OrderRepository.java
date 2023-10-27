package com.myapps.store.ecommerce.repository;

import com.myapps.store.ecommerce.model.Order;
import com.myapps.store.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findAllByUser(User user, Pageable pageable);
}
