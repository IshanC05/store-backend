package com.myapps.store.ecommerce.controller;

import com.myapps.store.ecommerce.exception.ResourceNotFoundException;
import com.myapps.store.ecommerce.payload.JwtRequest;
import com.myapps.store.ecommerce.payload.JwtResponse;
import com.myapps.store.ecommerce.payload.UserDto;
import com.myapps.store.ecommerce.security.JwtHelper;
import com.myapps.store.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manger;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto newUserDto) {
        Date date = new Date();
        newUserDto.setDate(date);
        String password = newUserDto.getPassword();
        newUserDto.setPassword(passwordEncoder.encode(password));
        UserDto createdUser = userService.createUser(newUserDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest request) {
        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = helper.generateToken(userDetails);
        JwtResponse response = new JwtResponse();
        response.setToken(token);
        response.setUser(mapper.map(userDetails, UserDto.class));
        return new ResponseEntity<JwtResponse>(response, HttpStatus.ACCEPTED);
    }

    private void doAuthenticate(String Username, String password) {
        try {
            manger.authenticate(new UsernamePasswordAuthenticationToken(Username, password));
        } catch (BadCredentialsException e) {
            throw new ResourceNotFoundException("Invalid username or password");

        } catch (DisabledException e) {
            throw new ResourceNotFoundException("User is not active");
        }
    }
}
