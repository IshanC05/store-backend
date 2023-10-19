package com.myapps.store.ecommerce.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {

    @Email(message = "Email address is not valid")
    @NotNull(message = "Email cannot be null")
    @Size(min = 5, message = "Invalid Email address")
    private String username;

    @NotEmpty
    @Size(min = 5, message = "Password must be at-least 5 chars long")
    private String password;
}
