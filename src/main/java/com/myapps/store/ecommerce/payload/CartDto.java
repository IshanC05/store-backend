package com.myapps.store.ecommerce.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CartDto {

    private int cartId;
    private Set<CartItemDto> cartItem = new HashSet<>();
    private UserDto user;
}
