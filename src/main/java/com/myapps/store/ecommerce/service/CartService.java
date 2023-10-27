package com.myapps.store.ecommerce.service;

import com.myapps.store.ecommerce.exception.ResourceNotFoundException;
import com.myapps.store.ecommerce.model.Cart;
import com.myapps.store.ecommerce.model.CartItem;
import com.myapps.store.ecommerce.model.Product;
import com.myapps.store.ecommerce.model.User;
import com.myapps.store.ecommerce.payload.CartDto;
import com.myapps.store.ecommerce.payload.ItemRequest;
import com.myapps.store.ecommerce.repository.CartRepository;
import com.myapps.store.ecommerce.repository.ProductRepository;
import com.myapps.store.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    public CartDto addItemToCart(ItemRequest item, String username) {
        int productId = item.getProductId();
        int quantity = item.getQuantity();

        // Retrieve the user
        User foundUser = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Retrieve the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Check product availability
        if (!product.isStock()) {
            throw new ResourceNotFoundException("Product out of Stock");
        }

        // Retrieve the user's existing cart, or create a new one if it doesn't exist
        Cart cart = foundUser.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setUser(foundUser); // Set the user for the cart
        }

        // Check if the product is already in the cart
        CartItem existingCartItem = null;

        for (CartItem cartItem : cart.getCartItem()) {
            if (cartItem.getProduct().getProductId() == productId) {
                existingCartItem = cartItem;
                break;
            }
        }

        if (existingCartItem != null) {
            // Update the existing cart item
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setTotalPrice(product.getProductPrice() * existingCartItem.getQuantity());
        } else {
            // Create a new cart item
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(product.getProductPrice() * quantity);
            cartItem.setCart(cart); // Set the cart for the cart item
            cart.getCartItem().add(cartItem);
        }

        // Save the updated cart
        Cart savedCart = cartRepository.save(cart);

        return mapper.map(savedCart, CartDto.class);
    }


    public CartDto retrieveCart(String email) {
        User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart foundCart = cartRepository.findByUser(foundUser).orElseThrow(() -> new ResourceNotFoundException("Cart " + "not" + " found"));
        return mapper.map(foundCart, CartDto.class);
    }

    public CartDto retrieveCartById(int cartId) {

//        User foundUser = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart foundCart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart " + "not" + " " + "found"));
        return mapper.map(foundCart, CartDto.class);
    }

    public CartDto removeCartItem(String username, int productId) {
        User foundUser = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart foundCart = foundUser.getCart();
        Set<CartItem> items = foundCart.getCartItem();

        boolean removed = items.removeIf(item -> item.getProduct().getProductId() == productId);

        Cart updatedCart = cartRepository.save(foundCart);

        return mapper.map(updatedCart, CartDto.class);
    }
}
