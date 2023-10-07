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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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
        User foundUser = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.isStock()) {
            throw new ResourceNotFoundException("Product out of Stock");
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        double totalPrice = product.getProductPrice() * quantity;
        cartItem.setTotalPrice(totalPrice);

        Cart cart = foundUser.getCart();

        if (cart == null) cart = new Cart();
        cartItem.setCart(cart);
        Set<CartItem> allCartItems = cart.getCartItem();

        AtomicBoolean flag = new AtomicBoolean(false);

        Set<CartItem> newProduct = allCartItems.stream().map((i) -> {
            if (i.getProduct().getProductId() == product.getProductId()) {
                i.setQuantity(quantity);
                i.setTotalPrice(totalPrice);
                flag.set(true);
            }
            return i;

        }).collect(Collectors.toSet());

        if (flag.get()) {
            allCartItems.clear();
            allCartItems.addAll(newProduct);

        } else {
            cartItem.setCart(cart);
            allCartItems.add(cartItem);
        }

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
