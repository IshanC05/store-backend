package com.myapps.store.ecommerce.repository;

import com.myapps.store.ecommerce.model.Cart;
import com.myapps.store.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    public Optional<Cart> findByUser(User user);
}
