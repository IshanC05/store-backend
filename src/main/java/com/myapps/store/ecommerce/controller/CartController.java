package com.myapps.store.ecommerce.controller;

import com.myapps.store.ecommerce.payload.CartDto;
import com.myapps.store.ecommerce.payload.ItemRequest;
import com.myapps.store.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping(path = "/")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CartDto> addToCart(@RequestBody ItemRequest itemRequest, Principal principal) {
//        System.out.println(itemRequest);
        CartDto cartDto = cartService.addItemToCart(itemRequest, principal.getName());
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @GetMapping(path = "/user")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CartDto> getCart(Principal principal) {
        CartDto cart = cartService.retrieveCart(principal.getName());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping(path = "/{cartId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CartDto> getCartById(@PathVariable int cartId) {
        CartDto cartById = cartService.retrieveCartById(cartId);
        return new ResponseEntity<>(cartById, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{productId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CartDto> deleteItemFromCart(@PathVariable int productId, Principal principal) {
        CartDto updatedCart = cartService.removeCartItem(principal.getName(), productId);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }
}
