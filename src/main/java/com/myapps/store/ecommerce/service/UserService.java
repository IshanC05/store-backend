package com.myapps.store.ecommerce.service;

import com.myapps.store.ecommerce.exception.ResourceNotFoundException;
import com.myapps.store.ecommerce.model.User;
import com.myapps.store.ecommerce.payload.UserDto;
import com.myapps.store.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public UserDto createUser(UserDto newUserDto) {
        User newUser = mapper.map(newUserDto, User.class);
        User savedUser = userRepository.save(newUser);
        return mapper.map(savedUser, UserDto.class);
    }

    public UserDto getUserById(int userId) {
        User foundUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not " + "found with id:" + userId));
        return mapper.map(foundUser, UserDto.class);
    }

    public void deleteUser(int userId) {
        User userToBeDeleted = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User " + "not " + "found with id:" + userId));
        userRepository.delete(userToBeDeleted);
    }

    public List<UserDto> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}
