package com.myapps.store.ecommerce.service;

import com.myapps.store.ecommerce.exception.ResourceNotFoundException;
import com.myapps.store.ecommerce.exception.UserAlreadyPresentException;
import com.myapps.store.ecommerce.model.Cart;
import com.myapps.store.ecommerce.model.User;
import com.myapps.store.ecommerce.payload.UserDto;
import com.myapps.store.ecommerce.repository.CartRepository;
import com.myapps.store.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    public UserDto createUser(UserDto newUserDto) {
        User newUser = mapper.map(newUserDto, User.class);
        // Check email id is unique
        String email = newUser.getEmail();
        Optional<User> isUserAvailable = userRepository.findByEmail(email);
        if (isUserAvailable.isPresent()) throw new UserAlreadyPresentException("User with same email already exists.");
        String role = "ROLE_USER";
        newUser.setRole(role);
        User savedUser = userRepository.save(newUser);
        Cart cart = new Cart();
        cart.setUser(savedUser);
        Cart savedCart = cartRepository.save(cart);
        return mapper.map(savedUser, UserDto.class);
    }

    public UserDto getUserById(int userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not " +
                "found"));
        return mapper.map(foundUser, UserDto.class);
    }

    public void deleteUser(int userId) {
        User userToBeDeleted = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not " +
                "found"));
        userRepository.delete(userToBeDeleted);
    }

    public List<UserDto> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}
