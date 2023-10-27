package com.myapps.store.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    private int quantity;
    private double totalPrice;

    // Relationship with cart
    @ManyToOne
    private Cart cart;

    // Relationship with product
    @ManyToOne
    private Product product;

}
