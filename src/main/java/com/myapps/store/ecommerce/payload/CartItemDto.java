package com.myapps.store.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private int cartItemId;
    private int quantity;
    private double totalPrice;
    @JsonIgnore
    private CartDto cart;
    private ProductDto product;
}
