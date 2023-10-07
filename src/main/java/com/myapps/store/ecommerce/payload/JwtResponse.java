package com.myapps.store.ecommerce.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private String token;
    private UserDto user;
}
