package com.myapps.store.ecommerce.repository;

import com.myapps.store.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
