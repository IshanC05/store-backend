package com.myapps.store.ecommerce.controller;

import com.myapps.store.ecommerce.payload.UserDto;
import com.myapps.store.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto newUserDto) {
        Date date = new Date();
        newUserDto.setDate(date);
        String password = newUserDto.getPassword();
        newUserDto.setPassword(passwordEncoder.encode(password));
        UserDto createdUser = userService.createUser(newUserDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping(path = "/find/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable int userId) {
        UserDto foundUserDto = userService.getUserById(userId);
        return new ResponseEntity<>(foundUserDto, HttpStatus.FOUND);
    }

    @DeleteMapping(path = "/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> allUsersDto = userService.findAllUsers();
        return new ResponseEntity<>(allUsersDto, HttpStatus.ACCEPTED);
    }
}
